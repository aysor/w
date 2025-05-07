package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.IPostRepository;
import ru.netology.repository.PostRepositoryImpl;
import ru.netology.service.PostService;

@Configuration
public class JavaConfig {
    @Bean
    public PostController postController() {
        return new PostController(postService());
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public IPostRepository postRepository() {
        return new PostRepositoryImpl();
    }
}
