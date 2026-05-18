package com.dailycodework.dreamshop.data;

import com.dailycodework.dreamshop.model.Role;
import com.dailycodework.dreamshop.model.User;
import com.dailycodework.dreamshop.repository.RoleRepository;
import com.dailycodework.dreamshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Ensure roles exist first
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_CUSTOMER");

        createDefaultUsersIfNotExist();
        createDefaultAdminsIfNotExist();
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }

    private void createDefaultUsersIfNotExist() {
        Role userRole = roleRepository.findByName("ROLE_CUSTOMER").get();

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));

            HashSet<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
            System.out.println("Default customer user " + i + " created successfully.");
        }
    }

    private void createDefaultAdminsIfNotExist() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));

            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);

            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }
}

