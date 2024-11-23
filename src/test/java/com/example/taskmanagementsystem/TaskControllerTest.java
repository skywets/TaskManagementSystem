package com.example.taskmanagementsystem;

import com.example.taskmanagementsystem.config.SecurityConfig;
import com.example.taskmanagementsystem.models.entities.Role;
import com.example.taskmanagementsystem.models.entities.Task;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    public void init() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        User user = new User();
        user.setName("Ozodbek");
        user.setEmail("ozod@mail.ru");
        user.setPassword("ozod123$%^");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @BeforeEach
    public void beforeMethod() throws ServletException {
        Optional<User> optionalUser = userRepository.findByName("Ozodbek");
        optionalUser.ifPresent( user -> {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }

    @Test
    @Order(1)
    public void testCreateTask() throws Exception {
        String createTaskJson = """
                {"header":"header", "description":"description description","comment":"comment+comment",
                "status":"PENDING","priority":"LOW","authorId":null,"performerId":"null"}""";
        ResultActions result = mockMvc.perform(post("/task/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTaskJson));
        result.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void testGetTestById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/task/byAuthorName?authorName=Ozodbek")
                .queryParam("name", "Ozodbek")).andReturn();
        Task[] tasks = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Task[].class);
        String createTaskJson = """
                {"header":"header", "description":"description description","comment":"comment+comment",
                "status":"PENDING","priority":"LOW","authorId":null,"performerId":"null"}""";
        for (Task task : tasks) {
            this.mockMvc.perform(get("/task/{id}", task.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(createTaskJson))
                    .andExpect(status().isOk());
            System.out.println(task.getComment());
        }


    }

    @Test
    @Order(3)
    public void testUpdateTask() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/task/byAuthorName?authorName=Ozodbek")
                .queryParam("name", "Ozodbek")).andReturn();
        Task[] tasks = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Task[].class);
        String updateTaskJson = """
                {"header":"headerupdate", "description":"description description","comment":"comment+comment",
                "status":"PENDING","priority":"LOW","authorId":null,"performerId":"null"}""";
        for (Task task : tasks) {
            mockMvc.perform(put("/task/{id}", task.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updateTaskJson))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @Order(4)
    public void testDeleteTask() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/task/byAuthorName?authorName=Ozodbek")
                .queryParam("name", "Ozodbek")).andReturn();
        Task[] tasks = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Task[].class);
        for (Task task : tasks) {
            var requestBuilder = MockMvcRequestBuilders.delete("/task/{id}", task.getId());
            ResultActions result = mockMvc.perform(requestBuilder);
            result.andExpect(status().isOk());
        }
    }

}

