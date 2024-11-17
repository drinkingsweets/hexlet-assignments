package exercise.dto.users;

import exercise.model.User;

import java.util.List;

import lombok.AllArgsConstructor;


// BEGIN
@AllArgsConstructor
public class UsersPage {
    public List<User> users;

    public List<User> getUsers() {
        return users;
    }
}

// END
