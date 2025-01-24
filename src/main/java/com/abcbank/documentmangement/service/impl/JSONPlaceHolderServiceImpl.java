package com.abcbank.documentmangement.service.impl;


import java.util.List;

import com.abcbank.documentmangement.client.JSONPlaceHolderClient;
import com.abcbank.documentmangement.model.Post;
import com.abcbank.documentmangement.service.JSONPlaceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class JSONPlaceHolderServiceImpl implements JSONPlaceHolderService {

    @Autowired
    private JSONPlaceHolderClient jsonPlaceHolderClient;

    @Override
    public List<Post> getPosts() {
        return jsonPlaceHolderClient.getPosts();
    }

    @Override
    public Post getPostById(Long id) {
        return jsonPlaceHolderClient.getPostById(id);
    }


    @Override
    public Post postFormData(@RequestBody Post data) {
        return jsonPlaceHolderClient.postFormData(data);
    }
}