package com.sistemasdistr.basico.config;

import com.sistemasdistr.basico.model.Role;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.RoleRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

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

        if (userRepository.findUserByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmailuser("admin@baskethub.local");
            admin.setNombreUsuario("Administrador");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFechaUltimoAcceso(LocalDateTime.now());
            admin.setUserRole(adminRole);
            userRepository.save(admin);
        }

        if (userRepository.findUserByUsername("manager") == null) {
            User manager = new User();
            manager.setUsername("manager");
            manager.setEmailuser("manager@baskethub.local");
            manager.setNombreUsuario("Manager");
            manager.setPassword(passwordEncoder.encode("manager"));
            manager.setFechaUltimoAcceso(LocalDateTime.now());
            manager.setUserRole(managerRole);
            userRepository.save(manager);
        }

        if (userRepository.findUserByUsername("player") == null) {
            User player = new User();
            player.setUsername("player");
            player.setEmailuser("player@baskethub.local");
            player.setNombreUsuario("Player");
            player.setPassword(passwordEncoder.encode("player"));
            player.setFechaUltimoAcceso(LocalDateTime.now());
            player.setUserRole(playerRole);
            userRepository.save(player);
        }
    }
}