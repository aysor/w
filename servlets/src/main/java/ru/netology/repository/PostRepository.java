package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    AtomicLong lastId = new AtomicLong();

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        for (Post post : posts.values()) {
            list.add(post);
        }
        return list;
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(lastId.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
