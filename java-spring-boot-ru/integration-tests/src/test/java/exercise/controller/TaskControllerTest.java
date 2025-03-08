package exercise.controller;

import com.sun.source.tree.AssertTree;
import net.datafaker.providers.entertainment.NewGirl;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.aot.hint.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.useRepresentation;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@AutoConfigureMockMvc
@SpringBootTest
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testGetConcrete() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .supply(Select.field(Task::getDescription), () -> faker.chuckNorris().fact())
                .create();
        taskRepository.save(task);

        var request = get("/tasks/"+task.getId())
                .contentType(MediaType.APPLICATION_JSON);

        var result = mockMvc.perform(request).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Task body = om.readValue(result, Task.class);
        assertThat(task.getDescription()).isEqualTo(body.getDescription());
    }
    @Test
    public void testPost() throws Exception {
        var data = new HashMap<>();
        data.put("description", faker.chuckNorris().fact());

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        var response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        long id = om.readValue(response, Task.class).getId();

        var result = taskRepository.findById(id).get();
        assertThat(data.get("description")).isEqualTo(result.getDescription());
    }
    @Test
    public void testPut() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .create();
        taskRepository.save(task);

        var data = new HashMap<>();
        data.put("description", faker.chuckNorris().fact());

        var request = put("/tasks/"+task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getDescription()).isEqualTo(data.get("description"));
    }
    @Test
    public void testDelete() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .supply(Select.field(Task::getDescription), () -> faker.chuckNorris().fact())
                .create();
        taskRepository.save(task);

        var request = delete("/tasks/"+task.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        var result = taskRepository.findById(task.getId());
        assertThat(result.isEmpty()).isTrue();
    }
    // END
}
