package com.slipp.board.api.service.board;

import com.slipp.board.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author galcyurio
 */
@RunWith(MockitoJUnitRunner.class)
public final class BoardServiceTest {

    private BoardService boardService;

    @Before
    public void setUp() throws Exception {
        boardService = new BoardService();
    }

    @Test
    public void Post_생성하고_조회하면_size는_1_이상이어야_한다() {
        boardService.create(new Post());

        List<Post> posts = boardService.getPosts();
        assertThat(posts.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void Post_생성하고_수정하고_조회하면_수정되어있어야_한다() {
        final Post expected = new Post();
        expected.setTitle("test title");
        expected.setContent("test content");

        boardService.create(expected);
        final Post actual = boardService.getPosts().stream()
            .filter(post -> post.getId() == expected.getId())
            .findFirst().get();

        assertThat(actual).isNotNull();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void Post_생성하고_삭제하고_조회하면_해당_Post는_없어야한다() {
        final Post expected = new Post();
        expected.setTitle("test title");
        expected.setContent("test content");

        boardService.create(expected);
        final Post actual = boardService.getPosts().stream()
            .filter(post -> post.getId() == expected.getId())
            .findFirst().get();
        boardService.remove(expected.getId());

        assertThat(actual).isNull();
    }
}