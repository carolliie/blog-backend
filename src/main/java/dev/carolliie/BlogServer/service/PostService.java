package dev.carolliie.BlogServer.service;

import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post savePost(Post post, MultipartFile file) throws IOException;

    List<Post> getAllPosts();

    Post deletePostById(Long id);

    Post getPostBySlug(String slug);

    Post editPostBySlug(String postSlug, PostDTO postDto, MultipartFile file) throws IOException;
}
