package com.abcbank.documentmangement.controller;

        import java.util.List;
        import java.util.Optional;

        import com.abcbank.documentmangement.configuration.ClientConfiguration;
        import com.abcbank.documentmangement.exception.NoDataFoundException;
        import com.abcbank.documentmangement.model.Post;
        import com.abcbank.documentmangement.service.JSONPlaceHolderService;
        import org.springframework.beans.factory.ObjectFactory;
        import org.springframework.beans.factory.ObjectProvider;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
        import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
        import org.springframework.context.annotation.Import;
        import org.springframework.web.bind.annotation.*;

@RestController
@Import(ClientConfiguration.class)
public class DocumentCommentsController {
    ////private final AlbumClient albumClient;

    //private final PostClient postClient;

    //private final TodoClient todoClient;

    @Autowired
    JSONPlaceHolderService jsonPlaceHolderService;

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    private final ObjectProvider<HttpMessageConverterCustomizer> customizers;

    public DocumentCommentsController(ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers) {

        //this.postClient = postClient;
        this.messageConverters = messageConverters;
        this.customizers = customizers;

    }


    @GetMapping(value = "/dynamicPosts/{id}")
    public Post getAlbumByIdAndDynamicUrl(@PathVariable(value = "id") Long id) {
       /* PostClient client = Feign.builder()
                .requestInterceptor(new DynamicUrlInterceptor(() -> "https://jsonplaceholder.typicode.com/posts/"))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, customizers))
                .target(Target.EmptyTarget.create(PostClient.class));
*/
        Optional<Post> response = Optional.ofNullable(jsonPlaceHolderService.getPostById(id));

        return response.orElseThrow(()->new NoDataFoundException());


    }
    @GetMapping(value = "/posts")
    public List<Post> getAllPosts() {
       /* PostClient client = Feign.builder()
                .requestInterceptor(new DynamicUrlInterceptor(() -> "https://jsonplaceholder.typicode.com/posts/"))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, customizers))
                .target(Target.EmptyTarget.create(PostClient.class));*/

        return jsonPlaceHolderService.getPosts();
    }
    @PostMapping(value = "/post")
    public Post postComments(@RequestBody Post postData){
/*
        PostClient client = Feign.builder()
                .requestInterceptor(new DynamicUrlInterceptor(() -> "https://jsonplaceholder.typicode.com/posts/"))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, customizers))
                .target(Target.EmptyTarget.create(PostClient.class));*/

       return  jsonPlaceHolderService.postFormData(postData);

    }
}

