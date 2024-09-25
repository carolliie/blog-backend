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
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    final Slugify slg = Slugify.builder().build();

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        final String result = slg.slugify(post.getName());
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
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

    /*public Post editPostById(Long id, PostDTO postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (postDto.getName() != null) {
                post.setName(postDto.getName());
            }
            if (postDto.getContent() != null) {
                post.setContent(postDto.getContent());
            }
            if (postDto.getSlug() != null) {
                post.setContent(postDto.getSlug());
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
    }*/

    public Post editPostBySlug(String postSlug, PostDTO postDto) {
        Optional<Post> optionalPost = postRepository.findBySlug(postSlug);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (postDto.getName() != null) {
                post.setName(postDto.getName());
                final String newSlug = slg.slugify(postDto.getName());
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
}
