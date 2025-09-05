import React, { useEffect, useState } from "react";

function Books() {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({ title: "", subject: "", author: "" });
  const [bookId, setBookId] = useState("");
  const [foundBook, setFoundBook] = useState(null);

  useEffect(() => {
    fetchAllBooks();
  }, []);

  const fetchAllBooks = async () => {
    try {
      const response = await fetch("http://localhost:8080/books");
      const data = await response.json();
      setBooks(data);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  const handleAddBook = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/books", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newBook),
      });
      const result = await response.text();
      console.log(result);
      setNewBook({ title: "", subject: "", author: "" });
      fetchAllBooks();
    } catch (error) {
      console.error("Error adding book:", error);
    }
  };


  const handleFindBook = async () => {
    if (!bookId) return;
    try {
      const response = await fetch(`http://localhost:8080/books/${bookId}`);
      if (response.status === 404) {
        setFoundBook(null);
        alert("Book not found!");
        return;
      }
      const data = await response.json();
      setFoundBook(data);
    } catch (error) {
      console.error("Error finding book:", error);
    }
  };

  const handleDeleteBook = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/books/${id}`, {
        method: "DELETE",
      });
      const result = await response.text();
      console.log(result);
      fetchAllBooks();
    } catch (error) {
      console.error("Error deleting book:", error);
    }
  };

  return (
    <div>
      <h2>📚 Books</h2>

      <form onSubmit={handleAddBook}>
        <input
          type="text"
          placeholder="Title"
          value={newBook.title}
          onChange={(e) => setNewBook({ ...newBook, title: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Subject"
          value={newBook.subject}
          onChange={(e) => setNewBook({ ...newBook, subject: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Author"
          value={newBook.author}
          onChange={(e) => setNewBook({ ...newBook, author: e.target.value })}
          required
        />
        <button type="submit">Add Book</button>
      </form>

      <div style={{ marginTop: "20px" }}>
        <input
          type="number"
          placeholder="Book ID"
          value={bookId}
          onChange={(e) => setBookId(e.target.value)}
        />
        <button onClick={handleFindBook}>Find Book</button>
        {foundBook && (
          <div>
            <p>Found: {foundBook.title} - {foundBook.subject} - {foundBook.author}</p>
          </div>
        )}
      </div>

      <ul>
        {books.map((book) => (
          <li key={book.bookId}>
            {book.title} - {book.subject} - {book.author}
            <button onClick={() => handleDeleteBook(book.bookId)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Books;
