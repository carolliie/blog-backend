package dev.carolliie.BlogServer.controller;

import dev.carolliie.BlogServer.entity.AuthenticationDTO;
import dev.carolliie.BlogServer.entity.RegisterDTO;
import dev.carolliie.BlogServer.entity.User;
import dev.carolliie.BlogServer.entity.UserDTO;
import dev.carolliie.BlogServer.repository.UserRepository;
import dev.carolliie.BlogServer.security.TokenResponse;
import dev.carolliie.BlogServer.security.TokenService;
import dev.carolliie.BlogServer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authManager.authenticate(usernamePassword);
        TokenResponse tokenResponse = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity logout (HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout realizado com sucesso.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getProfilePicture(), user.getSlug()));
    }

}
