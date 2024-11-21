package exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import kong.unirest.Unirest;
import io.javalin.Javalin;

class SessionTest {

    private Javalin app;
    private String baseUrl;

    @BeforeEach
    public void beforeAll() {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    public void afterAll() {
        app.stop();
    }

    @Test
    void testFlashMessage() {
        var requestBody = "name=test&body=body";
        var response = Unirest
            .post(baseUrl + "/posts")
            .body(requestBody)
            .asEmpty();

        assertThat(response.getStatus()).isEqualTo(302);

        var response2 = Unirest
            .get(baseUrl + "/posts")
            .asString();

        var content = response2.getBody();
        assertThat(content).contains("Post was successfully created!");
    }
}
