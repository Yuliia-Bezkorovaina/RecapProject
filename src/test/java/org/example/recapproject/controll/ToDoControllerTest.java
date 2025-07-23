package org.example.recapproject.controll;


import org.example.recapproject.model.STATUS;
import org.example.recapproject.model.ToDo;
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
                      "status": "OPEN"
                  }
                 """))
                .andExpect(MockMvcResultMatchers.status().isOk()) // oder isCreated(), wenn du das im Controller machst
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("new task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"));
    }

    @Test
    void getToDoById_shouldReturnToDo_ifDatavalide() throws Exception {
        //Give
        ToDo todo = new ToDo("1", "testing", STATUS.DOING);
        mockRepo.save(todo);
        todo = new ToDo("2", "party", STATUS.OPEN);
        mockRepo.save(todo);
        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                        {
                                "id" : "2",
                                "description" : "party",
                                "status" : "OPEN"
                                }
                                """
                ));
    }

    @Test
    void updateToDoById_shouldReturnToDo_ifDataValide() throws Exception {
        //Give
        ToDo oldToDo = new ToDo("1", "old testing", STATUS.OPEN);
        mockRepo.save(oldToDo);

        ToDo updateToDo = new ToDo("1", "new testing", STATUS.DOING);
        mockRepo.delete(oldToDo);
        mockRepo.save(updateToDo);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + "1")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                                {
                                                  "id" : "1",
                                          "description" : "new testing",
                                        "status" : "DOING"}
                                        """))
                // THEN (Assert)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("new testing")) // Assert description
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DOING"));
    }

    @Test
    void deleteToDoById_shouldReturnToDo_ifDataValide() throws Exception {

            // GIVEN
            ToDo toDo = new ToDo("1", "test", STATUS.DONE);
            mockRepo.save(toDo);

            // ensure it exists before delete
            mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            // WHEN: delete student
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            // THEN: student should no longer be available
            mockMvc.perform(MockMvcRequestBuilders.get("/api/student/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

}