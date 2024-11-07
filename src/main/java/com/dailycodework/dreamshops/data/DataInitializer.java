package com.dailycodework.dreamshops.data;

import com.dailycodework.dreamshops.model.Role;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.RoleRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Transactional
@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

   @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN");
        createDefaultRoleIfNotExists(roles);
        seedUserIfNotExists();
        seedAdminIfNotExists();
    }

    void seedUserIfNotExists() {
       Role userRole = roleRepository.findByName("ROLE_USER");
       for (int i = 1; i < 5; i++) {
           String defaultEmail = "user" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail))
            {
              continue;
            }
           User user = new User();
           user.setFirstName("User" + i);
           user.setLastName("User" + i);
           user.setEmail(defaultEmail);
           user.setPassword(passwordEncoder.encode("password"));
           user.setRoles(Set.of(userRole));
           userRepository.save(user);
       }
    }

    void seedAdminIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail))
            {
                continue;
            }
            User user = new User();
            user.setFirstName("Administrator" + i);
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> !roleRepository.existsByName(role))
                .map(Role::new).forEach(roleRepository::save);

    }
}
