package org.example.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private int num;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public Library() {}

    public Library(String name, String username, String password, int num, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.num = num;
        this.email = email;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
