package com.sistemasdistr.basico.service.mapper;

import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        return userRepository.save(user);
    }

    public boolean isLastAdmin(Integer id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        if (user.getUserRole() == null || user.getUserRole().getRoleName() == null) {
            return false;
        }

        if (!"ROLE_ADMIN".equals(user.getUserRole().getRoleName())) {
            return false;
        }

        long totalAdmins = userRepository.countByUserRole_RoleName("ROLE_ADMIN");
        return totalAdmins <= 1;
    }

    public boolean deleteByIdProtected(Integer id) {
        if (isLastAdmin(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }
}