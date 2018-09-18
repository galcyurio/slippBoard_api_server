package net.slipp.controller

import net.slipp.repository.domain.Board
import net.slipp.service.BoardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * @author galcyurio
 */
@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping
    fun boards(@RequestParam("page") page: Int? = null,
               @RequestParam("size") size: Int? = null): Page<Board> {
        val pageRequest = PageRequest.of(page ?: 0, size ?: 10)
        return boardService.findAll(pageRequest)
    }

    @GetMapping("/{id}")
    fun board(@PathVariable("id") id: Long): ResponseEntity<Board> {
        val board = boardService.findById(id)
        return when (board.isPresent) {
            true -> ResponseEntity.ok(board.get())
            false -> ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun save(@RequestBody board: Board): ResponseEntity<Board> {
        val savedBoard = boardService.save(board)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedBoard.id).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody board: Board): ResponseEntity<Board> {
        val updatedBoard = boardService.save(board)
        return ResponseEntity.ok(updatedBoard)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        boardService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}