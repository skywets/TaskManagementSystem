package com.example.taskmanagementsystem;

import com.example.taskmanagementsystem.config.SecurityConfig;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.utils.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;


    @BeforeAll
    public void init() {
        userRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void testCreateUser() throws Exception {
        String createUserJson = """
                {"name":"Ozodbek", "email":"ozod@example.com","password":"A1qwerty?^%"}""";
        ResultActions result = mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserJson));

        result.andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser(username = "pupkin", roles = "ADMIN", password = "1234")
    @Order(2)
    public void testGetUserByName() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/user/byName").queryParam("name", "Ozodbek"))
                .andReturn();
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        this.mockMvc.perform(get("/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("Ozodbek"))
                .andExpect(jsonPath("$.email").value("ozod@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));

    }

    @Test
    @WithMockCustomUser(username = "pupkin", roles = "ADMIN", password = "1234")
    @Order(3)
    public void testUpdateUser() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/user/byName").queryParam("name", "Ozodbek"))
                .andReturn();
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        String updatedUserJson = """
                {"name":"Ozodbek","email":"ozodbek.email@example.com","password":"qwertu?!3344HH"}
                """;
        ResultActions result = mockMvc.perform(put("/user/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser(username = "pupkin", roles = "ADMIN", password = "1234")
    @Order(4)
    public void testDeleteUser() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/user/byName").queryParam("name", "Ozodbek"))
                .andReturn();
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        var requestBuilder = MockMvcRequestBuilders.delete("/user/{id}", user.getId());
        ResultActions result = mockMvc.perform(requestBuilder);
        result.andExpect(status().isOk());
    }


    @Test
    @WithMockCustomUser(username = "pupkin", roles = "ADMIN", password = "1234")
    @Order(5)
    public void noUserByName() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/user/byName").queryParam("name", "Ozodbek"))
                .andReturn();
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        this.mockMvc.perform(get("/user/{id}", user.getName()))
                .andExpect(status().isNotFound());
    }
}
