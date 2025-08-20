package ru.netology.config; // Убедитесь, что пакет указан правильно

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
@ComponentScan(basePackages = "ru.netology") // Указывает Spring искать компоненты в указанном пакете
public class AppConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository(); // Создание и возврат экземпляра PostRepository
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository()); // Использование метода postRepository для получения зависимости
    }

    @Bean
    public PostController postController() {
        return new PostController(postService()); // Использование метода postService для получения зависимости
    }
}
