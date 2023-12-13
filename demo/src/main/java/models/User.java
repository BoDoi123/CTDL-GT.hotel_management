package models;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class User {
    private static AtomicInteger nextID = new AtomicInteger(1);
    private int id;
    private String username;
    private String password;
    private Role role;

    public enum Role {
        Manager, Staff
    }

    public User() {

    }

    public User(String username, String password, Role role) {
        this.id = nextID.getAndIncrement();
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
