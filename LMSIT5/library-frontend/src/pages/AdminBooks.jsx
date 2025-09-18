import { useEffect, useState } from "react";
import api from "../api";

export default function AdminBooks() {
  const [books, setBooks] = useState([]);
  const [form, setForm] = useState({ title: "", author: "", subject: "", genre: "" });

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const res = await api.get("/books");
      setBooks(res.data);
    } catch (err) {
      console.error("Failed to fetch books:", err);
    }
  };

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleAdd = async (e) => {
    e.preventDefault();
    try {
      await api.post("/books/add", form);
      setForm({ title: "", author: "", subject: "", genre: "" });
      fetchBooks();
    } catch (err) {
      console.error("Failed to add book:", err);
      alert("Failed to add book");
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/books/delete/${id}`);
      fetchBooks();
    } catch (err) {
      console.error("Failed to delete book:", err);
    }
  };

  return (
    <div>
      <h2>Admin Books</h2>
      <form onSubmit={handleAdd}>
        <input name="title" placeholder="Title" value={form.title} onChange={handleChange} required />
        <input name="author" placeholder="Author" value={form.author} onChange={handleChange} required />
        <input name="subject" placeholder="Subject" value={form.subject} onChange={handleChange} />
        <select name="genre" value={form.genre} onChange={handleChange} required>
          <option value="">Select Genre</option>
          <option value="Fiction">Fiction</option>
          <option value="Non-Fiction">Non-Fiction</option>
          <option value="Science">Science</option>
          <option value="Biography">Biography</option>
        </select>
        <button type="submit">Add Book</button>
      </form>

      <ul>
        {books.map((b) => (
          <li key={b.bookId}>
            {b.title} - {b.author} ({b.genre}) <button onClick={() => handleDelete(b.bookId)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
