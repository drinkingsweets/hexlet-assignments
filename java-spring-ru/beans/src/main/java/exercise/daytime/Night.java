package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Bean was created.");
    }
    // END
}
