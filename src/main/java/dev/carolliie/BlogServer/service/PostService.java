package dev.carolliie.BlogServer.service;

import dev.carolliie.BlogServer.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long id);
}
