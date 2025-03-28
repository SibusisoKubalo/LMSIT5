package org.example.Domain;


public class Library {
    private int id;
    private String name;
    private String username;
    private String password;
    private int num;
    private String email;

    public Library(int id, String name, String username, String password, int num, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.num = num;
        this.email = email;
    }

    public void createAccount() {
        System.out.println("Account created for " + name);
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User logged out");
    }
}

