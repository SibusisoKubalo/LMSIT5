// Java
package org.example.Domain;
/**
 * LibraryDomain.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Library {
    @Id
    private int id;
    private String name;
    private String username;
    private String password; // Note: Passwords should be hashed in a real application
    private int num;
    private String email;

    private Library() {}

    // Builder constructor
    private Library(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setUsername(builder.username);
        setPassword(builder.password);
        setNum(builder.num);
        setEmail(builder.email);
    }

    // Getters and Setters with validation
    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be greater than 0");
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be null or empty");
        this.username = username;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password cannot be null or empty");
        this.password = password;
    }
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) throw new IllegalArgumentException("Invalid email address");
        this.email = email;
    }

    // Methods
    public void printAccountCreationMessage() {
        System.out.println("Account created for " + name);
    }
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    public void logout() {
        System.out.println("User logged out");
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", num=" + num +
                ", email='" + email + '\'' +
                '}';
    }

    // Builder pattern
    public static class Builder {
        private int id;
        private String name;
        private String username;
        private String password;
        private int num;
        private String email;

        public Builder id(int id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder num(int num) { this.num = num; return this; }
        public Builder email(String email) { this.email = email; return this; }

        public Library build() { return new Library(this); }
    }
}