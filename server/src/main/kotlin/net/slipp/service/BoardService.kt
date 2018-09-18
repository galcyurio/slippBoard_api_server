package net.slipp.service

import net.slipp.repository.BoardRepository
import net.slipp.repository.domain.Board
import net.slipp.repository.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author galcyurio
 */
@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    fun save(board: Board): Board {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        board.user = user
        return boardRepository.save(board)
    }

    fun findAll(pageable: Pageable): Page<Board> {
        return boardRepository.findAll(pageable)
    }

    fun findById(id: Long): Optional<Board> {
        return boardRepository.findById(id)
    }

    fun deleteById(id: Long) {
        boardRepository.deleteById(id)
    }
}