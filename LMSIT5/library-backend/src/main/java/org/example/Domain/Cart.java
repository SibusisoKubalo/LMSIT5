package org.example.Domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "cart_items",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> items = new ArrayList<>();

    private boolean checkedOut = false;

    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<Book> getItems() { return items; }
    public void setItems(List<Book> items) { this.items = items; }
    public boolean isCheckedOut() { return checkedOut; }
    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }

    public void addBook(Book book) {
        items.add(book);
    }

    public void removeBook(Book book) {
        items.remove(book);
    }
}

