package dev.carolliie.BlogServer.service;

import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;

import java.util.List;

public interface PostService {

    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post deletePostById(Long id);

    //Post editPostById(Long id, PostDTO postDto);

    Post getPostBySlug(String slug);

    Post editPostBySlug(String postSlug, PostDTO postDto);
}
