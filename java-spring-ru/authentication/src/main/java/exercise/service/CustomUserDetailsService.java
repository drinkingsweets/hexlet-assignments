package exercise.service;

import exercise.exception.ResourceNotFoundException;
import exercise.mapper.UserMapper;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

// BEGIN
@Service
public class CustomUserDetailsService implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Нужно добавить в репозиторий findByEmail
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Override
    public void createUser(UserDetails userData) {
        User user = new User();
        user.setEmail(userData.getUsername());
        user.setPasswordDigest(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        User found = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with email " +
                        user.getUsername() + " not found"));

        found.setEmail(user.getUsername());
        found.setPasswordDigest(passwordEncoder.encode(user.getPassword()));
        userRepository.save(found);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " +
                        username + " not found"));

        userRepository.delete(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        var currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        currentUser.setPasswordDigest(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
}
// END
