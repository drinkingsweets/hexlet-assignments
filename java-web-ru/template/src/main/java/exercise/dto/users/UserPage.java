package exercise.dto.users;

import exercise.model.User;

// BEIN
public class UserPage {
    User user;

    public UserPage(User user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getEmail() {
        return user.getEmail();
    }
}
// END
