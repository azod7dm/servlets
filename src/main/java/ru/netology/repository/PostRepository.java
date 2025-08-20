package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  // Получение всех постов
  public List<Post> all() {
    return List.copyOf(posts.values());
  }

  // Получение поста по ID
  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  // Сохранение или обновление поста
  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = idCounter.incrementAndGet(); // Генерация нового ID
      post.setId(newId);
      posts.put(newId, post);
    } else {
      if (!posts.containsKey(post.getId())) {
        throw new NotFoundException("Post not found with id: " + post.getId());
      }
      posts.put(post.getId(), post);
    }
    return post;
  }

  // Удаление поста по ID
  public void removeById(long id) {
    if (posts.remove(id) == null) {
      throw new NotFoundException("Post not found with id: " + id);
    }
  }
}
