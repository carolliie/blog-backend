package dev.carolliie.BlogServer.service;

import com.github.slugify.Slugify;
import dev.carolliie.BlogServer.entity.User;
import dev.carolliie.BlogServer.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    final Slugify slg = Slugify.builder().build();

    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String result = slg.slugify(user.getUsername());
        user.setSlug(result);
        return userRepository.save(user);
    }

    public User getUserBySlug(String slug) {
        Optional<User> user = userRepository.findBySlug(slug);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public User getUserByEmail(String email) {
        UserDetails user = userRepository.findByEmail(email);

        if (user != null) {
            return (User) user;
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public User editRegisteredUser(String slug, User user) {
        Optional<User> optionalUser = userRepository.findBySlug(slug);

        if (optionalUser.isPresent()) {
            User registedUser = optionalUser.get();

            if (user.getUsername() != null) {
                registedUser.setUsername(user.getUsername());
                String newSlug = slg.slugify(user.getUsername());
                registedUser.setSlug(newSlug);
            }

            if (user.getPassword() != null) {
                registedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            if (user.getEmail() != null) {
                registedUser.setEmail(user.getEmail());
            }

            if (user.getRole() != null) {
                registedUser.setRole(user.getRole());
            }

            if (user.getProfilePicture() != null) {
                registedUser.setProfilePicture(user.getProfilePicture());
            }

            if (user.getBio() != null) {
                registedUser.setBio(user.getBio());
            }

            userRepository.save(registedUser);
            return registedUser;
        } else {
            throw new EntityNotFoundException("user not found or deleted.");
        }
    }
}
