package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
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
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private Faker getFaker;


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
    public void testShow() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .supply(Select.field(Task::getTitle), () -> getFaker.lorem().sentence(5))
                .supply(Select.field(Task::getDescription), () -> getFaker.lorem().sentence(15))
                .create();

        taskRepository.save(task);

        var result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("title").isEqualTo(task.getTitle()),
                a -> a.node("description").isEqualTo(task.getDescription())
        );
    }

    @Test
    void testCreate() throws Exception{
        String title = getFaker.lorem().sentence(5);
        String description = getFaker.lorem().sentence(15);

        String taskJson = String.format("""
        {
            "title": "%s",
            "description": "%s"
        }
        """, title, description);

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThatJson(response).and(
                a -> a.node("title").isEqualTo(title),
                a -> a.node("description").isEqualTo(description),
                a -> a.node("id").isNotNull(),
                a -> a.node("createdAt").isNotNull(),
                a -> a.node("updatedAt").isNotNull()
        );

    }

    @Test
    void testUpdate() throws Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .supply(Select.field(Task::getTitle), () -> getFaker.lorem().sentence(5))
                .supply(Select.field(Task::getDescription), () -> getFaker.lorem().sentence(15))
                .create();

        taskRepository.save(task);

        String title = getFaker.lorem().sentence(5);
        String description = getFaker.lorem().sentence(15);

        var data = new HashMap<>();

        data.put("title", title);
        data.put("description", description);

        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        var result = mockMvc.perform(request);
        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
    }

    @Test
    void testDelete() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .supply(Select.field(Task::getTitle), () -> getFaker.lorem().sentence(5))
                .supply(Select.field(Task::getDescription), () -> getFaker.lorem().sentence(15))
                .create();

        taskRepository.save(task);

        var request = delete("/tasks/" + task.getId());
        var result = mockMvc.perform(request);
        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
    // END
}
