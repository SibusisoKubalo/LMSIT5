import { useEffect, useState } from "react";
import api from "../api"; // Axios instance

export default function AdminBooks() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    title: "",
    author: "",
    subject: "",
    genre: "",
    totalCopies: "",
    availableCopies: "",
    location: "",
    status: "AVAILABLE"
  });
  const [errors, setErrors] = useState({});

  // Fetch all books
  const fetchBooks = async () => {
    try {
      const res = await api.get("/books");
      setBooks(res.data);
    } catch (err) {
      console.error("Failed to fetch books:", err.response?.data || err.message);
      alert("Failed to fetch books");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  // Validate input fields
  const validate = () => {
    const newErrors = {};
    if (!form.title.trim()) newErrors.title = "Title is required";
    if (!form.author.trim()) newErrors.author = "Author is required";
    if (!form.genre) newErrors.genre = "Genre is required";
    if (!form.totalCopies || isNaN(form.totalCopies) || form.totalCopies <= 0)
      newErrors.totalCopies = "Total copies must be a positive number";
    if (!form.availableCopies || isNaN(form.availableCopies) || form.availableCopies < 0)
      newErrors.availableCopies = "Available copies must be 0 or more";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form change
  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  // Add new book
  const handleAdd = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    // Prepare payload, no bookId
    const payload = {
      title: form.title,
      author: form.author,
      subject: form.subject,
      genre: form.genre,
      totalCopies: parseInt(form.totalCopies),
      availableCopies: parseInt(form.availableCopies),
      location: form.location,
      status: form.status
    };

    try {
      await api.post("/books/add", payload);
      setForm({
        title: "",
        author: "",
        subject: "",
        genre: "",
        totalCopies: "",
        availableCopies: "",
        location: "",
        status: "AVAILABLE"
      });
      fetchBooks();
    } catch (err) {
      console.error("Failed to add book:", err.response?.data || err.message);
      alert("Failed to add book: " + (err.response?.data?.error || err.message));
    }
  };

  // Delete a book
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this book?")) return;
    try {
      await api.delete(`/books/delete/${id}`);
      fetchBooks();
    } catch (err) {
      console.error("Failed to delete book:", err.response?.data || err.message);
      alert("Failed to delete book: " + (err.response?.data?.error || err.message));
    }
  };

  if (loading) return <p>Loading books...</p>;

  return (
  <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
    <h2 style={{ marginBottom: "20px" }}>📚 Admin Books</h2>

    {/* Add Book Form */}
    <form
      onSubmit={handleAdd}
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
        gap: "15px",
        marginBottom: "30px",
        backgroundColor: "#f0f4ff",
        padding: "20px",
        borderRadius: "12px",
        boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
      }}
    >
      {[
        { name: "title", placeholder: "Title" },
        { name: "author", placeholder: "Author" },
        { name: "subject", placeholder: "Subject" },
        { name: "totalCopies", placeholder: "Total Copies", type: "number" },
        { name: "availableCopies", placeholder: "Available Copies", type: "number" },
        { name: "location", placeholder: "Location" },
      ].map((field) => (
        <div key={field.name} style={{ display: "flex", flexDirection: "column" }}>
          <input
            type={field.type || "text"}
            name={field.name}
            placeholder={field.placeholder}
            value={form[field.name]}
            onChange={handleChange}
            style={{
              padding: "10px",
              borderRadius: "8px",
              border: "1px solid #082155",
              outline: "none",
            }}
          />
          {errors[field.name] && <span style={{ color: "#f44336" }}>{errors[field.name]}</span>}
        </div>
      ))}

      <select
        name="genre"
        value={form.genre}
        onChange={handleChange}
        style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
      >
        <option value="">Select Genre</option>
        <option value="Fiction">Fiction</option>
        <option value="Non-Fiction">Non-Fiction</option>
        <option value="Science">Science</option>
        <option value="Biography">Biography</option>
      </select>
      {errors.genre && <span style={{ color: "#f44336" }}>{errors.genre}</span>}

      <select
        name="status"
        value={form.status}
        onChange={handleChange}
        style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
      >
        <option value="AVAILABLE">AVAILABLE</option>
        <option value="ON_LOAN">ON_LOAN</option>
        <option value="RESERVED">RESERVED</option>
        <option value="DAMAGED">DAMAGED</option>
      </select>

      <button
        type="submit"
        style={{
          gridColumn: "1 / -1",
          padding: "12px",
          borderRadius: "8px",
          backgroundColor: "#082155",
          color: "#fff",
          fontWeight: "bold",
          cursor: "pointer",
          border: "none",
          transition: "background 0.3s",
        }}
        onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#0b2a7a")}
        onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#082155")}
      >
        ➕ Add Book
      </button>
    </form>

    {/* Books Table */}
    {books.length === 0 ? (
      <p>No books found.</p>
    ) : (
      <div style={{ overflowX: "auto" }}>
        <table
          style={{
            borderCollapse: "collapse",
            width: "100%",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            borderRadius: "12px",
            overflow: "hidden",
          }}
        >
          <thead style={{ backgroundColor: "#082155", color: "#fff" }}>
            <tr>
              {["ID", "Title", "Author", "Genre", "Subject", "Total", "Available", "Location", "Status", "Actions"].map((h) => (
                <th key={h} style={{ padding: "12px", textAlign: "left" }}>{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {books.map((b) => (
              <tr key={b.bookId} style={{ borderBottom: "1px solid #ddd" }}>
                <td style={{ padding: "10px" }}>{b.bookId}</td>
                <td style={{ padding: "10px" }}>{b.title}</td>
                <td style={{ padding: "10px" }}>{b.author}</td>
                <td style={{ padding: "10px" }}>{b.genre}</td>
                <td style={{ padding: "10px" }}>{b.subject}</td>
                <td style={{ padding: "10px" }}>{b.totalCopies}</td>
                <td style={{ padding: "10px" }}>{b.availableCopies}</td>
                <td style={{ padding: "10px" }}>{b.location}</td>
                <td style={{ padding: "10px" }}>{b.status}</td>
                <td style={{ padding: "10px" }}>
                  <button
                    onClick={() => handleDelete(b.bookId)}
                    style={{
                      backgroundColor: "#f44336",
                      color: "#fff",
                      border: "none",
                      padding: "8px 12px",
                      borderRadius: "6px",
                      cursor: "pointer",
                      transition: "background 0.3s",
                    }}
                    onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#d32f2f")}
                    onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#f44336")}
                  >
                    ❌ Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    )}
  </div>
);
}
