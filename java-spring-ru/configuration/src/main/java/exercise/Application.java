package exercise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController


public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();
    @Autowired
    UserProperties adminsInfo = new UserProperties();

    // BEGIN
    @GetMapping("/admins")
    public List<String> admins() {
        // Получаем список email администраторов
        List<String> adminEmails = adminsInfo.getAdmins();
        if (adminEmails == null || adminEmails.isEmpty()) {
            return List.of(); // Если список админов пустой, возвращаем пустой результат
        }

        // Фильтруем пользователей по email и собираем имена
        return users.stream()
                .filter(user -> adminEmails.contains(user.getEmail())) // Фильтруем только администраторов
                .map(User::getName) // Извлекаем имена
                .sorted(String::compareToIgnoreCase) // Сортируем по алфавиту
                .collect(Collectors.toList());
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
            .filter(u -> u.getId() == id)
            .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
