package ru.netology.config; // Убедитесь, что вы используете правильный пакет

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
@ComponentScan(basePackages = "ru.netology") // Указывает на пакет, в котором Spring будет искать компоненты
public class AppConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository(); // Создается бин PostRepository
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository()); // Здесь PostRepository инжектируется в PostService
    }

    @Bean
    public PostController postController() {
        return new PostController(postService()); // Здесь PostService инжектируется в PostController
    }
}
