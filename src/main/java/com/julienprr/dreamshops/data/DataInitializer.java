package com.julienprr.dreamshops.data;

import com.julienprr.dreamshops.model.Role;
import com.julienprr.dreamshops.model.User;
import com.julienprr.dreamshops.repository.RoleRepository;
import com.julienprr.dreamshops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultsRoles = Set.of("ROLE_ADMIN", "ROLE_CUSTOMER");
        createDefaultRolesIfNotExist(defaultsRoles);
        createDefaultCustomerUsersIfNotExist();
        createDefaultAdminUsersIfNotExist();
    }

    private void createDefaultCustomerUsersIfNotExist() {
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER").get(0);
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("Customer firstname" + i);
            user.setLastname("Customer lastname" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(customerRole));
            userRepository.save(user);
            System.out.println("Default customer user created: " + i);
        }
    }

    private void createDefaultAdminUsersIfNotExist() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get(0);

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("Admin firstname" + i);
            user.setLastname("Admin lastname" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user created: " + i);
        }
    }

    private void createDefaultRolesIfNotExist(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
