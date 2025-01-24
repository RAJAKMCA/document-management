package com.abcbank.documentmangement.hystrix;

import com.abcbank.documentmangement.client.JSONPlaceHolderClient;
import com.abcbank.documentmangement.model.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return new Post();
    }

    @Override
    public Post postFormData(Post data) {
        return new Post();
    }

}