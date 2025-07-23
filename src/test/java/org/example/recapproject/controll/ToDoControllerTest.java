package org.example.recapproject.controll;


import org.example.recapproject.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository mockRepo;

    @Test
    void getAllToDo_shouldReturnNull_IfEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [
                                ]
                                """
                ));
    }

    @Test
    void addToDo_shouldReturnOneToDO_ifDataValid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""

                                {
                      "description" : "new task",
                      "status": "TODO"
                  }
                 """))
                .andExpect(MockMvcResultMatchers.status().isOk()) // oder isCreated(), wenn du das im Controller machst
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("new task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TODO"));
    }
}