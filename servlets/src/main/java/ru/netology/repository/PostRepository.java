package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class PostRepository {
    //List<Post> posts = new ArrayList<>();
    Map<Long, Post> posts = new HashMap<>();
    AtomicLong lastId = new AtomicLong();

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        for (Post post : posts.values()) {
            list.add(post);
        }
        return list;
    }

    public Optional<Post> getById(long id) {
        //return posts.stream().filter(p -> p.getId() == id).findAny();
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
