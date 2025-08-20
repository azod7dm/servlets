package ru.netology.servlet;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.AppConfig;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private static final Logger logger = Logger.getLogger(MainServlet.class.getName());
  private PostController controller;

  @Override
  public void init() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); // Создание контекста Spring
    controller = context.getBean(PostController.class); // Получение экземпляра PostController из контекста
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // Primitive routing
      if (method.equals("GET") && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
        final var id = extractIdFromPath(path);
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        final var id = extractIdFromPath(path);
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      logger.severe("Internal server error: " + e.getMessage());
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private long extractIdFromPath(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }
}
