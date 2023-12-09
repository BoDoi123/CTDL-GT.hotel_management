package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private static int nextId = 1;
    private int id;
    private String username;
    private String password;
    private Role role;

    public enum Role {
        Manager, Staff, Customer
    }

    public User(String username, String password, Role role) {
        this.id = getNextId();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private static int getNextId() {
        return nextId++;
    }
}
