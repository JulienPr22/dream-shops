package com.julienprr.dreamshops.data;

import com.julienprr.dreamshops.model.User;
import com.julienprr.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUsersIfNotExist();
    }

    private void createDefaultUsersIfNotExist() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "User" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("User firstname" + i);
            user.setLastname("User lastname" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default user created: " + i);
        }
    }
}
