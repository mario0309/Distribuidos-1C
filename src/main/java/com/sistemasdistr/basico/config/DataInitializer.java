package com.sistemasdistr.basico.config;

import com.sistemasdistr.basico.model.Role;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.RoleRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.default.admin.username}")
    private String adminUsername;

    @Value("${app.default.admin.password}")
    private String adminPassword;

    @Value("${app.default.manager.username}")
    private String managerUsername;

    @Value("${app.default.manager.password}")
    private String managerPassword;

    @Value("${app.default.player.username}")
    private String playerUsername;

    @Value("${app.default.player.password}")
    private String playerPassword;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            adminRole.setShowOnCreate(1);
            adminRole = roleRepository.save(adminRole);
        }

        Role managerRole = roleRepository.findByRoleName("ROLE_MANAGER");
        if (managerRole == null) {
            managerRole = new Role();
            managerRole.setRoleName("ROLE_MANAGER");
            managerRole.setShowOnCreate(1);
            managerRole = roleRepository.save(managerRole);
        }

        Role playerRole = roleRepository.findByRoleName("ROLE_PLAYER");
        if (playerRole == null) {
            playerRole = new Role();
            playerRole.setRoleName("ROLE_PLAYER");
            playerRole.setShowOnCreate(1);
            playerRole = roleRepository.save(playerRole);
        }

        if (userRepository.findUserByUsername(adminUsername) == null) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmailuser("admin@baskethub.local");
            admin.setNombreUsuario("Administrador");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setFechaUltimoAcceso(LocalDateTime.now());
            admin.setUserRole(adminRole);
            userRepository.save(admin);
        }

        if (userRepository.findUserByUsername(managerUsername) == null) {
            User manager = new User();
            manager.setUsername(managerUsername);
            manager.setEmailuser("manager@baskethub.local");
            manager.setNombreUsuario("Manager");
            manager.setPassword(passwordEncoder.encode(managerPassword));
            manager.setFechaUltimoAcceso(LocalDateTime.now());
            manager.setUserRole(managerRole);
            userRepository.save(manager);
        }

        if (userRepository.findUserByUsername(playerUsername) == null) {
            User player = new User();
            player.setUsername(playerUsername);
            player.setEmailuser("player@baskethub.local");
            player.setNombreUsuario("Player");
            player.setPassword(passwordEncoder.encode(playerPassword));
            player.setFechaUltimoAcceso(LocalDateTime.now());
            player.setUserRole(playerRole);
            userRepository.save(player);
        }
    }
}