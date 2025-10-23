import { useEffect, useState } from "react";
import { useCart } from "../contexts/CartContext";
import api from "../api";

export default function Books({ username }) {
  const [books, setBooks] = useState([]);
  const [borrowed, setBorrowed] = useState([]);
  const [loading, setLoading] = useState(true);
  const { addToCart, removeFromCart, isInCart } = useCart();

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
    if (!username) {
      setBorrowed([]);
      return;
    }

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
    if (!username) {
      alert("Please log in to borrow books.");
      return;
    }

    if (!window.confirm("Borrow this book?")) return;

    try {
      console.log("Borrowing book with username:", username); // Debug log
      const res = await api.post(`/books/borrow/${bookId}`, null, {
        params: { username, days: 14 } // Changed to 14 days to match backend default
      });
      alert(res.data.message || "Book borrowed successfully!");
      fetchAll();
    } catch (err) {
      console.error("Failed to borrow book:", err.response?.data || err.message);
      const errorMessage = err.response?.data?.error || err.response?.data || err.message || "Unknown error";
      alert("Failed to borrow book: " + errorMessage);
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

  // Add to cart handler
  const handleAddToCart = (book) => {
    if (isInCart(book.bookId)) {
      removeFromCart(book.bookId);
    } else {
      addToCart(book);
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
        <div style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fill, minmax(300px, 1fr))",
          gap: "20px",
          marginBottom: "40px"
        }}>
          {books.map((b) => (
            <div key={b.bookId} style={{
              backgroundColor: "#fff",
              borderRadius: "12px",
              padding: "20px",
              boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
              border: "1px solid #e0e0e0",
              transition: "transform 0.2s, box-shadow 0.2s"
            }}
            onMouseEnter={e => {
              e.currentTarget.style.transform = "translateY(-5px)";
              e.currentTarget.style.boxShadow = "0 8px 20px rgba(0,0,0,0.15)";
            }}
            onMouseLeave={e => {
              e.currentTarget.style.transform = "translateY(0)";
              e.currentTarget.style.boxShadow = "0 4px 12px rgba(0,0,0,0.1)";
            }}>

              {/* Book Cover */}
              <div style={{ textAlign: "center", marginBottom: "15px" }}>
                {b.imageUrl ? (
                  <img
                    src={b.imageUrl}
                    alt={`Cover of ${b.title}`}
                    style={{
                      width: "100%",
                      maxWidth: "200px",
                      height: "250px",
                      objectFit: "cover",
                      borderRadius: "8px",
                      border: "2px solid #082155"
                    }}
                    onError={(e) => {
                      e.target.style.display = 'none';
                      e.target.nextSibling.style.display = 'flex';
                    }}
                  />
                ) : (
                  <div style={{
                    width: "100%",
                    maxWidth: "200px",
                    height: "250px",
                    backgroundColor: "#f0f0f0",
                    border: "2px dashed #082155",
                    borderRadius: "8px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    color: "#666",
                    fontSize: "14px",
                    margin: "0 auto"
                  }}>
                    📖 No Cover Available
                  </div>
                )}
                {!b.imageUrl && (
                  <div style={{
                    display: "none",
                    width: "100%",
                    maxWidth: "200px",
                    height: "250px",
                    backgroundColor: "#f0f0f0",
                    border: "2px dashed #082155",
                    borderRadius: "8px",
                    alignItems: "center",
                    justifyContent: "center",
                    color: "#666",
                    fontSize: "14px",
                    margin: "0 auto"
                  }}>
                    📖 Cover Unavailable
                  </div>
                )}
              </div>

              {/* Book Details */}
              <div style={{ textAlign: "center" }}>
                <h4 style={{ margin: "0 0 8px 0", color: "#082155", fontSize: "18px" }}>{b.title}</h4>
                <p style={{ margin: "0 0 8px 0", color: "#666", fontSize: "14px" }}>by {b.author}</p>
                <p style={{ margin: "0 0 8px 0", color: "#888", fontSize: "12px" }}>{b.genre} • {b.subject}</p>

                {/* Availability Status */}
                <div style={{
                  display: "inline-block",
                  padding: "4px 12px",
                  borderRadius: "20px",
                  fontSize: "12px",
                  fontWeight: "bold",
                  backgroundColor: b.availableCopies > 0 ? "#e8f5e8" : "#ffeaea",
                  color: b.availableCopies > 0 ? "#2e7d2e" : "#d32f2f",
                  marginBottom: "12px"
                }}>
                  {b.availableCopies > 0 ? `✅ ${b.availableCopies} Available` : "❌ Not Available"}
                </div>

                {/* Action Buttons */}
                <div style={{ display: "flex", gap: "8px", justifyContent: "center", flexWrap: "wrap" }}>
                  {/* PDF Link */}
                  {b.pdfUrl && (
                    <a
                      href={b.pdfUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      style={{
                        backgroundColor: "#4CAF50",
                        color: "#fff",
                        border: "none",
                        padding: "8px 16px",
                        borderRadius: "6px",
                        textDecoration: "none",
                        fontSize: "12px",
                        fontWeight: "bold"
                      }}
                    >
                      📄 View PDF
                    </a>
                  )}

                  {/* Add to Cart Button */}
                  {b.availableCopies > 0 ? (
                    <button
                      onClick={() => handleAddToCart(b)}
                      style={{
                        backgroundColor: isInCart(b.bookId) ? "#d32f2f" : "#007bff",
                        color: "#fff",
                        border: "none",
                        padding: "10px 20px",
                        borderRadius: "6px",
                        cursor: "pointer",
                        fontSize: "14px",
                        fontWeight: "bold",
                        flex: "1 1 120px",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center"
                      }}
                    >
                      {isInCart(b.bookId) ? "🛒 Remove from Cart" : "➕ Add to Cart"}
                    </button>
                  ) : (
                    <div style={{
                      padding: "10px 20px",
                      backgroundColor: "#f5f5f5",
                      color: "#999",
                      border: "1px solid #ddd",
                      borderRadius: "6px",
                      fontSize: "14px",
                      fontWeight: "bold",
                      flex: "1 1 120px",
                      textAlign: "center"
                    }}>
                      📚 Not Available
                    </div>
                  )}
                </div>

                {/* Price Display */}
                {b.price > 0 && (
                  <p style={{
                    margin: "8px 0 0 0",
                    color: "#082155",
                    fontSize: "14px",
                    fontWeight: "bold"
                  }}>
                    R{b.price.toFixed(2)}
                  </p>
                )}
              </div>
            </div>
          ))}
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
              {borrowed.map((t) => {
                // Calculate current fine for display
                let currentFine = 0;
                if (t.returnDate) {
                  // Book has been returned, use stored fine
                  currentFine = t.fine;
                } else {
                  // Book is still borrowed, calculate current fine if overdue
                  const today = new Date();
                  const dueDate = new Date(t.dueDate);
                  if (today > dueDate) {
                    const overdueDays = Math.ceil((today - dueDate) / (1000 * 60 * 60 * 24));
                    const dailyFineRate = t.book.fineRate || 5.0; // Use book's fine rate or default to R5
                    currentFine = overdueDays * dailyFineRate;
                  }
                }

                return (
                  <tr key={t.id} style={{ borderBottom: "1px solid #ddd" }}>
                    <td style={{ padding: "10px" }}>{t.book.title}</td>
                    <td style={{ padding: "10px" }}>{t.book.author}</td>
                    <td style={{ padding: "10px" }}>{new Date(t.borrowDate).toLocaleDateString()}</td>
                    <td style={{ padding: "10px" }}>{new Date(t.dueDate).toLocaleDateString()}</td>
                    <td style={{
                      padding: "10px",
                      color: currentFine > 0 ? "#d32f2f" : "#2e7d2e",
                      fontWeight: currentFine > 0 ? "bold" : "normal"
                    }}>
                      R{currentFine.toFixed(2)}
                      {!t.returnDate && currentFine > 0 && (
                        <div style={{ fontSize: "10px", color: "#666" }}>
                          (R{(t.book.fineRate || 5.0).toFixed(2)}/day)
                        </div>
                      )}
                    </td>
                    <td style={{ padding: "10px" }}>
                      {!t.returnDate ? (
                        <button
                          onClick={() => handleReturn(t.id)}
                          style={{
                            backgroundColor: "#f44336",
                            color: "#fff",
                            border: "none",
                            padding: "8px 12px",
                            borderRadius: "6px",
                            cursor: "pointer"
                          }}
                        >
                          Return
                        </button>
                      ) : (
                        <span style={{ color: "#2e7d2e", fontSize: "12px", fontWeight: "bold" }}>
                          ✅ Returned
                        </span>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
