package dev.carolliie.BlogServer.entity;

public record UserDTO(Long id, String username, String email, UserRole role, String profilePicture, String slug) {
}
