package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.exceptions.ConflictException;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Role;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.RoleRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.request.RegisterRequest;
import com.dailycodework.dreamshops.request.UpdadteUserRequest;
import com.dailycodework.dreamshops.response.JwtResponse;
import com.dailycodework.dreamshops.security.jwt.JwtUtils;
import com.dailycodework.dreamshops.security.user.ShopUserDetails;
import com.dailycodework.dreamshops.service.email.IEmailService;
import com.dailycodework.dreamshops.service.kafka.IKafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final IEmailService emailService;
    private  IKafkaProducerService producerService;
    private final ExecutorService executorService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils, AuthenticationManager authenticationManager, RoleRepository roleRepository,
                       IEmailService emailService, IKafkaProducerService producerService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.producerService = producerService;
        this.executorService = Executors.newSingleThreadExecutor(); // Asynchronous task executor
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public JwtResponse registerUser(RegisterRequest request) {
        createUser(request);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateTokenForUser(authentication);
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

        String emailContent= "Hello " + request.getFirstName() + ",\n\n" +
                "Thank you for registering with DreamShops. Your account has been created successfully.\n\n" +
                "Best regards,\n" +
                "DreamShops Team";

//        emailService.sendEmail(request.getEmail(), "Welcome to DreamShops",
//                "Hello " + request.getFirstName() + ",\n\n" +
//                        "Thank you for registering with DreamShops. Your account has been created successfully.\n\n" +
//                        "Best regards,\n" +
//                        "DreamShops Team");
        producerService.sendMessage("emailss", userDetails.getEmail(), emailContent);
        return new JwtResponse(userDetails.getId(), jwt);
    }

    private void createUser(RegisterRequest request) {
        // Check if the email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(request.getEmail() + " already exists!");
        }

        // Find or create the role for the user
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            throw new IllegalArgumentException("Default role ROLE_USER not found.");
        }

        // Create and save the user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User updateUser(UpdadteUserRequest user, Long userId) {
        return userRepository.findById(userId)
                .map(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new NotFoundException("User not found");
                });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
