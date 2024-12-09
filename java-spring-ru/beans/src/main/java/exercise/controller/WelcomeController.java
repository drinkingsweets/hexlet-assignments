package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
public class WelcomeController {
    private final Daytime daytime;

    @GetMapping(path = "/welcome")
    public String welcome() {
        return String.format("It is %s now! Welcome to Spring!", daytime.getName());
    }
    public WelcomeController(Daytime daytime) {
        this.daytime = daytime;
    }
}
// END
