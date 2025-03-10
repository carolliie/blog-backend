package dev.carolliie.BlogServer.controller;

import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;
import dev.carolliie.BlogServer.repository.PostRepository;
import dev.carolliie.BlogServer.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("api/posts")
@CrossOrigin(origins = "*")
public class PostController {


    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Post createdPost = postService.savePost(post, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getPostBySlug(@PathVariable String slug) {
        try {
            Post post = postService.getPostBySlug(slug);

            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slug not found");
            }

            return ResponseEntity.ok(post);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
        try {
            Post post = postService.deletePostById(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/edit/{postSlug}")
    public ResponseEntity<?> editPostBySlug(@PathVariable String postSlug, @RequestBody PostDTO postDto, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Post post = postService.editPostBySlug(postSlug, postDto, file);
            return ResponseEntity.ok("Post edited successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
