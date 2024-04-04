import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserList {
    private final List<User> userList;

    public UserList() {
        this.userList = new ArrayList<>();
    }

    public void add(User user) throws AlreadyHereException {
        synchronized (userList) {
            if (checkIfHere(user.getName())) {
                throw new AlreadyHereException("Try new name");
            }
            userList.add(user);
        }
    }

    public String getUserList() {
        StringBuilder userListString = new StringBuilder();
        synchronized (userList) {
            for (User user : userList) {
                userListString.append(user.getName()).append("|");
            }
        }
        if (userListString.length() > 0) {
            userListString.deleteCharAt(userListString.length() - 1);
        }
        return userListString.toString();
    }

    public void removeUser(String nickname) {
        userList.removeIf(user -> user.getName().equals(nickname));
    }

    private boolean checkIfHere(String name) {
        boolean isHere = false;
        for (User user : userList) {
            if (Objects.equals(user.getName(), name))
                isHere = true;
        }
        return isHere;
    }
}
