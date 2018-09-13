package net.slipp.repository

import net.slipp.repository.domain.Board
import net.slipp.repository.domain.BoardRepository
import net.slipp.repository.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author galcyurio
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class BoardRepositoryTest {

    @Autowired lateinit var boardRepository: BoardRepository

    @Test
    fun `User 생성 후 Board 생성 조회 삭제`() {
        val user = User("foo", "bar", listOf())
        val board = Board("dummy-title").also { it.user = user }

        val savedBoard = boardRepository.save(board)
        assertThat(board).isEqualTo(savedBoard)

        val foundBoard = boardRepository.getOne(board.id)
        assertThat(board).isEqualTo(foundBoard)

        boardRepository.delete(board)
    }
}