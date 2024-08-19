package dev.carolliie.BlogServer.service;

import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
         post.setLikeCount(0);
         post.setViewCount(0);
         post.setDate(new Date());

         return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

}
