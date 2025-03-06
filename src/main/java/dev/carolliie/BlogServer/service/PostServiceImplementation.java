package dev.carolliie.BlogServer.service;

import com.github.slugify.Slugify;
import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;
import dev.carolliie.BlogServer.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImplementation implements PostService {

    final Slugify slg = Slugify.builder().build();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private StorageService storageService;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    public Post savePost(Post post, MultipartFile file) throws IOException {
        String result = slg.slugify(post.getName());
        post.setDate(new Date());
        post.setSlug(result);

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename().replaceAll(" ", "_");

            storageService.uploadFile(bucketName, fileName, file.getInputStream(), file.getContentType());

            String imageUrl = minioUrl + "/" + bucketName + "/" + fileName;

            post.setImg(imageUrl);
        }

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

    public Post editPostBySlug(String postSlug, PostDTO postDto, MultipartFile file) throws IOException {
        Optional<Post> optionalPost = postRepository.findBySlug(postSlug);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (postDto.getName() != null) {
                post.setName(postDto.getName());
                String newSlug = slg.slugify(postDto.getName());
                post.setSlug(newSlug);
            }
            if (postDto.getContent() != null) {
                post.setContent(postDto.getContent());
            }
            if (postDto.getImg() != null) {
                if (file != null && !file.isEmpty()) {
                    String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename().replaceAll(" ", "_");

                    storageService.uploadFile(bucketName, fileName, file.getInputStream(), file.getContentType());

                    String imageUrl = minioUrl + "/" + bucketName + "/" + fileName;

                    post.setImg(imageUrl);
                }
            }
            if (postDto.getDate() != null) {
                post.setDate(postDto.getDate());
            }
            if (postDto.getTags() != null) {
                post.setTags(postDto.getTags());
            }
            if (postDto.getTagColor() != null) {
                post.setTagColor(postDto.getTagColor());
            }
            if (postDto.getTagTextColor() != null) {
                post.setTagTextColor(postDto.getTagTextColor());
            }

            postRepository.save(post);
            return post;
        } else {
            throw new EntityNotFoundException("Post not found or deleted.");
        }
    }
}
