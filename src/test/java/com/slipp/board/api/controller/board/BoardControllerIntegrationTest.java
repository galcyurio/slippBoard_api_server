package com.slipp.board.api.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slipp.board.api.service.board.BoardService;
import com.slipp.board.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author galcyurio
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardControllerIntegrationTest {

    private ArgumentCaptor<Post> captor;

    @Autowired MappingJackson2HttpMessageConverter converter;
    @Mock private BoardService service;
    private ObjectMapper objectMapper;

    private List<Post> posts;
    private Post post;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        final BoardController controller = new BoardController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(converter)
            .build();
        objectMapper = converter.getObjectMapper();
        captor = ArgumentCaptor.forClass(Post.class);
    }

    @Before
    public void postsStubbing() throws Exception {
        posts = IntStream.rangeClosed(0, 9)
            .mapToObj(i -> new Post() {{
                setTitle("test title " + i);
                setContent("test content " + i);
            }})
            .collect(Collectors.toList());
        when(service.getPosts()).thenReturn(posts);

        post = new Post();
        post.setTitle("dummy title");
        post.setContent("dummy content");
    }

    @Test
    public void getPosts_호출시_service_에서_반환하는_Posts_반환() throws Exception {
        mockMvc.perform(get("/board/posts"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$", hasSize(posts.size())))
            .andExpect(jsonPath("$.[5].title", is("test title 5")))
            .andExpect(jsonPath("$.[5].content", is("test content 5")));
    }

    @Test
    public void create_호출시_service에_넘어가는_Post가_동일하고_Response는_성공() throws Exception {
        mockMvc
            .perform(post("/board/post")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(post))
            )
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        verify(service).create(captor.capture());

        final Post actual = captor.getValue();
        assertThat(actual).isEqualTo(post);
    }

    @Test
    public void modify_호출시_service에_넘어가는_Post가_동일하고_Response는_성공() throws Exception {
        mockMvc
            .perform(put("/board/post")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(post)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        verify(service).modify(captor.capture());

        final Post actual = captor.getValue();
        assertThat(actual).isEqualTo(post);
    }

    @Test
    public void remove_호출시_해당_ID가_Service를_통해_호출되고_Response는_성공() throws Exception {
        long expected = 1L;

        mockMvc
            .perform(delete("/board/post/{id}", expected))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        verify(service).remove(expected);
    }
}