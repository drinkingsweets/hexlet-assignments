package exercise.dto.users;

import exercise.model.User;
import java.util.List;

public class UsersPage {
    public List<User> USERS;
    public String term;
    public String content;

    public UsersPage(List<User> USERS, String term, String content) {
        this.USERS = USERS;
        this.term = term;
        this.content = content;
    }
}
