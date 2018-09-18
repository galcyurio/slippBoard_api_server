package net.slipp.controller

import com.nhaarman.mockitokotlin2.*
import net.slipp.repository.domain.Board
import net.slipp.service.BoardService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

/**
 * @author galcyurio
 */
@RunWith(MockitoJUnitRunner::class)
class BoardControllerTest {

    private lateinit var mockMvc: MockMvc
    @Mock private lateinit var boardService: BoardService
    @Spy @InjectMocks private lateinit var boardController: BoardController

    private val boardJson = """
            {
              "title": "dummy-title",
              "content": "dummy-content"
            }
        """.trimIndent()

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build()
    }

    @Test
    fun `GET boards`() {
        mockMvc.perform(get("/boards"))
            .andDo(print())
            .andExpect(status().isOk)

        verify(boardController).boards()
        verify(boardService).findAll(any())
    }

    @Test
    fun `GET boards (page, size)`() {
        val page = 1
        val size = 10
        mockMvc
            .perform(get("/boards")
                .param("page", page.toString())
                .param("size", size.toString()))
            .andDo(print())
            .andExpect(status().isOk)

        verify(boardController).boards(page, size)
        argumentCaptor<Pageable>().apply {
            verify(boardService).findAll(capture())

            val pageable = firstValue
            assertThat(pageable.pageNumber).isEqualTo(page)
            assertThat(pageable.pageSize).isEqualTo(size)
        }
    }

    @Test
    fun `GET board - 없으면 NOT_FOUND`() {
        val id = 1L
        whenever(boardService.findById(id)).thenReturn(Optional.ofNullable(null))

        mockMvc.perform(get("/boards/$id"))
            .andDo(print())
            .andExpect(status().isNotFound)

        verify(boardController).board(id)
        verify(boardService).findById(id)
    }

    @Test
    fun `GET board - 있으면 HTTP_OK`() {
        val id = 1L
        val board = Board("dummy-title", "dummy-content")
        whenever(boardService.findById(id)).thenReturn(Optional.of(board))

        mockMvc.perform(get("/boards/$id"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value(board.title))
            .andExpect(jsonPath("$.content").value(board.content))

        verify(boardController).board(id)
        verify(boardService).findById(id)
    }

    @Test
    fun `POST boards`() {
        val savedId = 3L
        val savedBoard: Board = mock()
        whenever(boardService.save(any())).thenReturn(savedBoard)
        whenever(savedBoard.id).thenReturn(savedId)

        val mvcResult = mockMvc
            .perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(boardJson))
            .andDo(print())
            .andExpect(status().isCreated)
            .andReturn()
        val location = mvcResult.response.getHeader("Location")
        assertThat(location).contains("/boards/$savedId")

        verify(boardController).save(any())
        verify(boardService).save(any())
    }

    @Test
    fun `PUT board`() {
        val id = 1L
        val updatedBoard: Board = mock()
        whenever(boardService.save(any())).thenReturn(updatedBoard)

        mockMvc
            .perform(put("/boards/$id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(boardJson))
            .andDo(print())
            .andExpect(status().isOk)

        verify(boardController).update(eq(id), any())
        verify(boardService).save(any())
    }

    @Test
    fun `DELETE board`() {
        val id = 1L

        mockMvc.perform(delete("/boards/$id"))
            .andDo(print())
            .andExpect(status().isOk)

        verify(boardController).deleteById(id)
        verify(boardService).deleteById(id)
    }
}