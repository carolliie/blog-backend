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

import java.util.List;

@RestController()
@RequestMapping("api/posts")
@CrossOrigin(origins = "*")
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post createdPost = postService.savePost(post);
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

    /*@GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

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

    @PatchMapping("/edit/{postId}")
    public ResponseEntity<?> editPostById(@PathVariable Long postId, @RequestBody PostDTO postDto) {
        try {
            Post post = postService.editPostById(postId, postDto);
            return ResponseEntity.ok("Post edited successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
