// src/main/java/my/socpms/api/service/UserService.java
package my.socpms.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.socpms.api.model.User;
import my.socpms.api.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            new ArrayList<>()
        );
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisterTime(LocalDateTime.now());  // Use setRegisterTime instead of setCreatedAt
        user.setActive(true);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public void updateLastLogin(String email) {
        User user = findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    public void initiatePasswordReset(String email) {
        User user = findByEmail(email);
        String newPassword = generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        notificationService.sendPasswordResetNotification(user, newPassword);
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User update(Long id, User user) {
        User existingUser = findById(id);
        existingUser.setName(user.getName());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setActive(user.isActive());
        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}