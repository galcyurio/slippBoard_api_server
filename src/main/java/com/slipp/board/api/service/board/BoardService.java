package com.slipp.board.api.service.board;

import com.slipp.board.model.Post;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService implements InitializingBean {

    private Map<Long, Post> posts = new HashMap<>();

    private void loadPosts() {

        DateTime now = DateTime.now();

        Map<Long, Post> posts = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {

            System.out.println("id : " + i);

            Post post = new Post();
            post.setId(i);
            post.setUserId("DEFAULT");
            post.setTitle(i + " TITLE");
            post.setContent(i + " CONTENT");
            post.setCreateDtm(now);

            posts.put(post.getId(), post);
        }

        synchronized (this.posts) {
            this.posts = posts;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadPosts();
    }

    public List<Post> getPosts() {
        return new ArrayList<>(this.posts.values());
    }

    public void create(Post post) {
        Optional<Post> lastPost = Optional.ofNullable(this.posts.get(this.posts.size() * 1L));
        long id = lastPost.isPresent() ? lastPost.get().getId() + 1 : 0;
        post.setId(id);
        post.setCreateDtm(DateTime.now());

        this.posts.put(id, post);
    }

    public void modify(Post post) {
        post.setUpdateDtm(DateTime.now());
        this.posts.put(post.getId(), post);
    }

    public void remove(long id) {
        this.posts.remove(id);
    }
}
