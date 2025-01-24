package com.abcbank.documentmangement.client;


import java.util.List;

import com.abcbank.documentmangement.configuration.ClientConfiguration;
import com.abcbank.documentmangement.hystrix.JSONPlaceHolderFallback;
import com.abcbank.documentmangement.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "jplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = ClientConfiguration.class,
        fallback = JSONPlaceHolderFallback.class)
public interface JSONPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();


    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Post postFormData(@RequestBody Post data);

}
