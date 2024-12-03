package exercise.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "users.admins")
public class UserProperties {
    private static List<String> admins;

    public static List<String> getAdmins() {
        return admins;
    }

    public static void setAdmins(List<String> admins) {
        UserProperties.admins = admins;
    }

}
