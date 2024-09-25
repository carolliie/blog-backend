package dev.carolliie.BlogServer.service;

import com.github.slugify.Slugify;
import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;
import dev.carolliie.BlogServer.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    final Slugify slg = Slugify.builder().locale(Locale.US).build();

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        String result = slg.slugify(post.getName());
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
        post.setSlug(result);
        result = generateUniqueSlug(post.getName());
        post.setSlug(result);

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

    public Post getPostBySlug(String slug) {
        Optional<Post> post = postRepository.findBySlug(slug);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    public Post deletePostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            postRepository.delete(post.get());
            return post.get();
        } else {
            throw new EntityNotFoundException("Post not found or deleted.");
        }
    }

    public Post editPostBySlug(String postSlug, PostDTO postDto) {
        Optional<Post> optionalPost = postRepository.findBySlug(postSlug);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (postDto.getName() != null) {
                post.setName(postDto.getName());
                String newSlug = slg.slugify(postDto.getName());
                post.setSlug(newSlug);
                newSlug = generateUniqueSlug(postDto.getName());
                post.setSlug(newSlug);
            }
            if (postDto.getContent() != null) {
                post.setContent(postDto.getContent());
            }
            if (postDto.getImg() != null) {
                post.setImg(postDto.getImg());
            }
            if (postDto.getDate() != null) {
                post.setDate(postDto.getDate());
            }
            if (postDto.getTags() != null) {
                post.setTags(postDto.getTags());
            }

            postRepository.save(post);
            return post;
        } else {
            throw new EntityNotFoundException("Post not found or deleted.");
        }
    }

    private String generateUniqueSlug(String postSlug) {
        String slugCheck = postSlug;
        int counter = 1;

        while (postRepository.findBySlug(slugCheck).isPresent()) {
            slugCheck = postSlug + "-" + counter;
            counter++;
        }
        return slugCheck;
    }
}
