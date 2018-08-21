package com.slipp.board.api.controller.board;

import com.slipp.board.api.service.board.BoardService;
import com.slipp.board.model.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = boardService.getPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Post post) {
        boardService.create(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/post", method = RequestMethod.PUT)
    public ResponseEntity modify(@RequestBody Post post) {
        boardService.modify(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable long id) {
        boardService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
