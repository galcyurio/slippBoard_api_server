package net.slipp.controller

import net.slipp.repository.domain.Board
import net.slipp.repository.BoardRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * @author galcyurio
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class BoardControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var boardRepository: BoardRepository
    private lateinit var mockMvc: MockMvc


    @Before
    fun setUp() {
        val boards = (1..10).map { Board("title $it", "content $it") }
        boardRepository.saveAll(boards)

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @After
    fun tearDown() {
        boardRepository.deleteAll()
    }

    @Test
    fun boardsTest() {
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk)
    }
}