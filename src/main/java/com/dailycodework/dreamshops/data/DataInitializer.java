package com.dailycodework.dreamshops.data;

import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

   @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        seedUserIfNotExists();
    }
    void seedUserIfNotExists() {
       for (int i = 1; i < 5; i++) {
           String defaultEmail = "user" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail))
            {
              continue;
            }
           User user = new User();
           user.setFirstName("User" + i);
           user.setLastName("User" + i);
           user.setEmail("user" + i + "@gmail.com");
           user.setPassword("password");
           userRepository.save(user);
       }
    }
}
