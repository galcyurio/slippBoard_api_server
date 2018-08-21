package com.slipp.board.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author galcyurio
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldControllerIntegrationTest {

    @Autowired private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void excludeSecurityHelloWorld_호출시_성공() throws Exception {
        mockMvc.perform(get("/excludeSecurity/helloWorld"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 인증되지_않은_요청일_때_includeSecurityHelloWorld_호출시_실패() throws Exception {
        mockMvc.perform(get("/includeSecurity/helloWorld").with(anonymous()))
            .andDo(print())
            .andExpect(unauthenticated());
    }

    @Test
    public void 인증된_요청일_때_includeSecurityHelloWorld_호출시_성공() throws Exception {
        mockMvc
            .perform(get("/includeSecurity/helloWorld")
                .with(user("user").authorities(new SimpleGrantedAuthority("USER"))))
            .andDo(print())
            .andExpect(status().isOk());
    }
}