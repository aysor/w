package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;

    private static final String M_GET = "GET";
    private static final String M_POST = "POST";
    private static final String M_DELETE = "DELETE";

    private static final String POSTS = "/api/posts";
    private static final String POSTS_WITH_ID = "/api/posts/\\d+";

    private static final String DELIMITER = "/";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(M_GET) && path.equals(POSTS)) {
                controller.all(resp);
                return;
            }
            if (method.equals(M_GET) && path.matches(POSTS_WITH_ID)) {
                // easy way
                final var id = getIdByPath(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(M_POST) && path.equals(POSTS)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(M_DELETE) && path.matches(POSTS_WITH_ID)) {
                // easy way
                final var id = getIdByPath(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long getIdByPath(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf(DELIMITER) + 1));
    }
}

