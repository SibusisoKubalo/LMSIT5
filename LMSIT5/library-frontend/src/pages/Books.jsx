import { useEffect, useState } from "react";
import api from "../api";

export default function Books({ username }) {
  const [books, setBooks] = useState([]);
  const [borrowed, setBorrowed] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch all books
  const fetchBooks = async () => {
    try {
      const res = await api.get("/books");
      setBooks(res.data);
    } catch (err) {
      console.error("Failed to fetch books:", err.response?.data || err.message);
      alert("Failed to fetch books");
    }
  };

  // Fetch borrowed books for the user
  const fetchBorrowed = async () => {
    try {
      const res = await api.get(`/books/borrowed?username=${username}`);
      setBorrowed(res.data);
    } catch (err) {
      console.error("Failed to fetch borrowed books:", err.response?.data || err.message);
      setBorrowed([]);
    }
  };

  // Combined fetch
  const fetchAll = async () => {
    setLoading(true);
    await fetchBooks();
    await fetchBorrowed();
    setLoading(false);
  };

  useEffect(() => {
    fetchAll();
  }, []);

  // Borrow a book
  const handleBorrow = async (bookId) => {
    if (!window.confirm("Borrow this book?")) return;
    try {
      const res = await api.post(`/books/borrow/${bookId}`, null, {
        params: { username, days: 7 }
      });
      alert(res.data.message || "Book borrowed successfully!");
      fetchAll();
    } catch (err) {
      console.error("Failed to borrow book:", err.response?.data || err.message);
      alert("Failed to borrow book: " + (err.response?.data?.error || err.message));
    }
  };

  // Return a book
  const handleReturn = async (transactionId) => {
    if (!window.confirm("Return this book?")) return;
    try {
      const res = await api.post(`/books/return/${transactionId}`);
      alert(res.data.message || "Book returned successfully!");
      fetchAll();
    } catch (err) {
      console.error("Failed to return book:", err.response?.data || err.message);
      alert("Failed to return book: " + (err.response?.data?.error || err.message));
    }
  };

  if (loading) return <p>Loading books...</p>;

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <h2 style={{ fontSize: "2rem", marginBottom: "20px" }}>📚 Books</h2>

      {/* All books */}
      <h3>Available Books</h3>
      {books.length === 0 ? (
        <p>No books available.</p>
      ) : (
        <div style={{ overflowX: "auto", marginBottom: "40px" }}>
          <table style={{ borderCollapse: "collapse", width: "100%", boxShadow: "0 2px 8px rgba(0,0,0,0.1)", borderRadius: "12px", overflow: "hidden" }}>
            <thead style={{ backgroundColor: "#082155", color: "#fff" }}>
              <tr>
                <th style={{ padding: "12px", textAlign: "left" }}>Title</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Author</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Available</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Action</th>
              </tr>
            </thead>
            <tbody>
              {books.map((b) => (
                <tr key={b.bookId} style={{ borderBottom: "1px solid #ddd" }}>
                  <td style={{ padding: "10px" }}>{b.title}</td>
                  <td style={{ padding: "10px" }}>{b.author}</td>
                  <td style={{ padding: "10px" }}>{b.availableCopies > 0 ? "✅" : "❌"}</td>
                  <td style={{ padding: "10px" }}>
                    {b.availableCopies > 0 ? (
                      <button onClick={() => handleBorrow(b.bookId)} style={{ backgroundColor: "#082155", color: "#fff", border: "none", padding: "8px 12px", borderRadius: "6px", cursor: "pointer" }}>
                        Borrow
                      </button>
                    ) : (
                      <span style={{ color: "gray" }}>Not available</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Borrowed books */}
      <h3>My Borrowed Books</h3>
      {borrowed.length === 0 ? (
        <p>You have no borrowed books.</p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <table style={{ borderCollapse: "collapse", width: "100%", boxShadow: "0 2px 8px rgba(0,0,0,0.1)", borderRadius: "12px", overflow: "hidden" }}>
            <thead style={{ backgroundColor: "#555", color: "#fff" }}>
              <tr>
                <th style={{ padding: "12px", textAlign: "left" }}>Title</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Author</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Borrowed On</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Due Date</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Fine</th>
                <th style={{ padding: "12px", textAlign: "left" }}>Action</th>
              </tr>
            </thead>
            <tbody>
              {borrowed.map((t) => (
                <tr key={t.id} style={{ borderBottom: "1px solid #ddd" }}>
                  <td style={{ padding: "10px" }}>{t.book.title}</td>
                  <td style={{ padding: "10px" }}>{t.book.author}</td>
                  <td style={{ padding: "10px" }}>{new Date(t.borrowDate).toLocaleDateString()}</td>
                  <td style={{ padding: "10px" }}>{new Date(t.dueDate).toLocaleDateString()}</td>
                  <td style={{ padding: "10px" }}>R{t.fine.toFixed(2)}</td>
                  <td style={{ padding: "10px" }}>
                    <button onClick={() => handleReturn(t.id)} style={{ backgroundColor: "#f44336", color: "#fff", border: "none", padding: "8px 12px", borderRadius: "6px", cursor: "pointer" }}>
                      Return
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
