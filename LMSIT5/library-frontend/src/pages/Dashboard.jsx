import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import api from "../api";

export default function Dashboard() {
  const [borrowedBooks, setBorrowedBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  // Get current user from localStorage
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const username = user.username;

  useEffect(() => {
    fetchBorrowedBooks();
  }, []);

  const fetchBorrowedBooks = async () => {
    if (!username) {
      setLoading(false);
      return;
    }

    try {
      const res = await api.get(`/books/borrowed?username=${username}`);
      // Filter to show only currently borrowed books (not returned)
      const activeBorrows = res.data.filter(transaction => !transaction.returnDate);
      setBorrowedBooks(activeBorrows);
    } catch (err) {
      console.error("Failed to fetch borrowed books:", err);
      setBorrowedBooks([]);
    } finally {
      setLoading(false);
    }
  };

  const cards = [
    {
      to: "/books",
      title: "📚 Browse Books",
      desc: "Browse and add books to your cart for borrowing."
    },
    {
      to: "/notifications",
      title: "🔔 Notifications",
      desc: "Check your latest notifications."
    },
  ];

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h1 style={{ fontSize: "2rem", marginBottom: "10px" }}>📚 Your Library Dashboard</h1>
        <p style={{ fontSize: "1rem" }}>Welcome back, {username}! Access your library features below.</p>
      </header>

      {/* My Borrowed Books Section */}
      <div style={{ marginBottom: "40px" }}>
        <h2 style={{ fontSize: "1.5rem", marginBottom: "20px", display: "flex", alignItems: "center", gap: "10px" }}>
          📖 My Borrowed Books
          {borrowedBooks.length > 0 && (
            <span style={{
              background: "#082155",
              color: "white",
              borderRadius: "50%",
              width: "24px",
              height: "24px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontSize: "12px",
              fontWeight: "bold"
            }}>
              {borrowedBooks.length}
            </span>
          )}
        </h2>

        {loading ? (
          <p>Loading your borrowed books...</p>
        ) : borrowedBooks.length === 0 ? (
          <div style={{
            background: "rgba(255, 255, 255, 0.95)",
            padding: "20px",
            borderRadius: "12px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            backdropFilter: "blur(10px)",
            textAlign: "center"
          }}>
            <p style={{ margin: 0, color: "#666", fontSize: "1rem" }}>
              You haven't borrowed any books yet. <Link to="/books" style={{ color: "#082155", fontWeight: "bold" }}>Browse books</Link> to get started!
            </p>
          </div>
        ) : (
          <div style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))",
            gap: "20px"
          }}>
            {borrowedBooks.map((transaction) => {
              const book = transaction.book;
              const dueDate = new Date(transaction.dueDate);
              const today = new Date();
              const isOverdue = today > dueDate;
              const daysLeft = Math.ceil((dueDate - today) / (1000 * 60 * 60 * 24));

              return (
                <div key={transaction.id} style={{
                  background: "rgba(255, 255, 255, 0.95)",
                  padding: "20px",
                  borderRadius: "12px",
                  boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                  backdropFilter: "blur(10px)",
                  border: isOverdue ? "2px solid #dc3545" : "1px solid rgba(8, 33, 85, 0.1)"
                }}>
                  <div style={{ display: "flex", gap: "15px" }}>
                    {/* Book Cover */}
                    <div style={{ flexShrink: 0 }}>
                      {book.imageUrl ? (
                        <img
                          src={book.imageUrl}
                          alt={book.title}
                          style={{
                            width: "60px",
                            height: "80px",
                            objectFit: "cover",
                            borderRadius: "4px",
                            border: "1px solid #ddd"
                          }}
                        />
                      ) : (
                        <div style={{
                          width: "60px",
                          height: "80px",
                          background: "#f0f0f0",
                          border: "1px dashed #ccc",
                          borderRadius: "4px",
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center",
                          fontSize: "20px"
                        }}>
                          📖
                        </div>
                      )}
                    </div>

                    {/* Book Details */}
                    <div style={{ flex: 1 }}>
                      <h4 style={{ margin: "0 0 5px 0", fontSize: "1rem", color: "#082155" }}>{book.title}</h4>
                      <p style={{ margin: "0 0 5px 0", fontSize: "0.9rem", color: "#666" }}>by {book.author}</p>
                      <p style={{ margin: "0 0 10px 0", fontSize: "0.8rem", color: "#888" }}>
                        Due: {dueDate.toLocaleDateString()}
                      </p>

                      {/* Status Badge */}
                      <div style={{
                        display: "inline-block",
                        padding: "2px 8px",
                        borderRadius: "12px",
                        fontSize: "0.8rem",
                        fontWeight: "bold",
                        marginBottom: "10px",
                        backgroundColor: isOverdue ? "#ffeaea" : daysLeft <= 3 ? "#fff3cd" : "#e8f5e8",
                        color: isOverdue ? "#d32f2f" : daysLeft <= 3 ? "#856404" : "#2e7d2e"
                      }}>
                        {isOverdue ? `⚠️ Overdue` : daysLeft <= 3 ? `⏰ Due in ${daysLeft} days` : `✅ ${daysLeft} days left`}
                      </div>

                      {/* Action Buttons */}
                      <div style={{ display: "flex", gap: "8px", flexWrap: "wrap" }}>
                        {book.pdfUrl && (
                          <button
                            onClick={() => window.open(`/viewer/${book.bookId}`, '_blank')}
                            style={{
                              backgroundColor: "#4CAF50",
                              color: "white",
                              border: "none",
                              padding: "8px 12px",
                              borderRadius: "4px",
                              cursor: "pointer",
                              fontSize: "0.8rem",
                              fontWeight: "bold",
                              display: "flex",
                              alignItems: "center",
                              gap: "4px"
                            }}
                          >
                            📖 Read Online
                          </button>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </div>

      {/* Quick Actions */}
      <div>
        <h2 style={{ fontSize: "1.5rem", marginBottom: "20px" }}>🚀 Quick Actions</h2>
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
            gap: "20px",
          }}
        >
          {cards.map((card) => (
            <Link
              key={card.to}
              to={card.to}
              style={{
                textDecoration: "none",
                color: "#082155",
                backgroundColor: "rgba(151, 175, 240, 0.8)",
                padding: "20px",
                borderRadius: "12px",
                boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
                backdropFilter: "blur(10px)",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                transition: "transform 0.2s",
              }}
              onMouseEnter={(e) => (e.currentTarget.style.transform = "translateY(-5px)")}
              onMouseLeave={(e) => (e.currentTarget.style.transform = "translateY(0)")}
            >
              <h2 style={{ marginBottom: "10px" }}>{card.title}</h2>
              <p style={{ flex: "1" }}>{card.desc}</p>
              <button
                style={{
                  marginTop: "10px",
                  padding: "10px",
                  backgroundColor: "#082155",
                  color: "#fff",
                  border: "none",
                  borderRadius: "8px",
                  cursor: "pointer",
                  fontWeight: "bold",
                  alignSelf: "flex-start",
                }}
              >
                Open
              </button>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}
