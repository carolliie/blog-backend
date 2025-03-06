package dev.carolliie.BlogServer.entity;

public record RegisterDTO(String username, String email, String password, UserRole role, String bio, String profilePicture) {
}
