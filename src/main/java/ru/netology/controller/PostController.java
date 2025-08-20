package ru.netology.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Component
public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  @Autowired
  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(data)); // Сериализация списка постов
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try {
      final var post = service.getById(id);
      final var gson = new Gson();
      response.getWriter().print(gson.toJson(post)); // Сериализация поста
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
    }
  }

  public void save(Reader body, HttpServletResponse response) {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    Post post;
    try {
      post = gson.fromJson(body, Post.class);
    } catch (JsonSyntaxException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return; // Возврат не нужен, так как это последний оператор в методе, но это может быть полезно для ясности
    }
    // Здесь мы сохраняем пост через сервис
    try {
      final var savedPost = service.save(post); // Сохранение поста
      response.getWriter().print(gson.toJson(savedPost)); // Сериализация и возвращение сохранённого поста
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found, если пост с таким ID не найден для обновления
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

  public void removeById(long id, HttpServletResponse response) {
    try {
      service.removeById(id); // Попытка удалить пост
      response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
    }
  }
}
