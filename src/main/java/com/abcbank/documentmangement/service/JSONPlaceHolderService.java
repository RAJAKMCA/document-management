package com.abcbank.documentmangement.service;


import com.abcbank.documentmangement.model.Post;

import java.util.List;


public interface JSONPlaceHolderService {

    List<Post> getPosts();

    Post getPostById(Long id);

    Post postFormData(Post post);
}