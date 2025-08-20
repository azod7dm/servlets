package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all(); // Получаем все посты
  }

  public Post getById(long id) {
    // Получаем пост и используем getters, например, для логирования
    Post post = repository.getById(id)
            .orElseThrow(() -> new NotFoundException("Post not found for ID: " + id));

    // Можно вывести значение через getter
    System.out.println("Retrieved post with ID: " + post.getId() + " and content: " + post.getContent());
    return post;
  }

  public Post save(Post post) {
    // Используем setter для изменения или установки значений
    post.setContent(post.getContent() + " (Saved)"); // Пример добавления отметки к контенту
    return repository.save(post); // Сохраняем или обновляем пост
  }

  public void removeById(long id) {
    repository.removeById(id); // Удаляем пост по ID
  }
}
