import { useCart } from "../contexts/CartContext";
import api from "../api";
import { useState } from "react";

export default function Cart({ username }) {
  const { cartItems, removeFromCart, clearCart } = useCart();
  const [isCheckingOut, setIsCheckingOut] = useState(false);

  const handleRemoveFromCart = (bookId) => {
    removeFromCart(bookId);
  };

  const handleCheckout = async () => {
    if (!username) {
      alert("Please log in to borrow books.");
      return;
    }

    if (cartItems.length === 0) {
      alert("Your cart is empty.");
      return;
    }

    if (!window.confirm(`Checkout ${cartItems.length} books?`)) return;

    setIsCheckingOut(true);
    const successfulBorrows = [];
    const failedBorrows = [];

    // Process each book in the cart
    for (const book of cartItems) {
      try {
        const res = await api.post(`/books/borrow/${book.bookId}`, null, {
          params: { username, days: 14 }
        });
        successfulBorrows.push(book.title);
      } catch (err) {
        console.error(`Failed to borrow ${book.title}:`, err.response?.data || err.message);
        failedBorrows.push({ title: book.title, error: err.response?.data?.error || err.message });
      }
    }

    setIsCheckingOut(false);

    // Show results
    let message = "";
    if (successfulBorrows.length > 0) {
      message += `Successfully borrowed: ${successfulBorrows.join(", ")}\n`;
    }
    if (failedBorrows.length > 0) {
      message += `Failed to borrow: ${failedBorrows.map(f => `${f.title} (${f.error})`).join(", ")}`;
    }

    alert(message);

    // Clear cart of successfully borrowed books
    successfulBorrows.forEach(() => {
      const bookIndex = cartItems.findIndex(item => successfulBorrows.includes(item.title));
      if (bookIndex !== -1) {
        removeFromCart(cartItems[bookIndex].bookId);
      }
    });

    // Refresh the page to update book availability
    window.location.reload();
  };

  const handleClearCart = () => {
    if (window.confirm("Clear all items from cart?")) {
      clearCart();
    }
  };

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "30px" }}>
        <h2 style={{ fontSize: "2rem" }}>🛒 Your Cart ({cartItems.length} items)</h2>
        {cartItems.length > 0 && (
          <div>
            <button
              onClick={handleClearCart}
              style={{
                padding: "10px 20px",
                backgroundColor: "#dc3545",
                color: "white",
                border: "none",
                borderRadius: "8px",
                cursor: "pointer",
                marginRight: "10px"
              }}
            >
              Clear Cart
            </button>
            <button
              onClick={handleCheckout}
              disabled={isCheckingOut || !username}
              style={{
                padding: "12px 24px",
                backgroundColor: username ? "#082155" : "#ccc",
                color: "white",
                border: "none",
                borderRadius: "8px",
                cursor: username ? "pointer" : "not-allowed",
                fontSize: "16px",
                fontWeight: "bold"
              }}
            >
              {isCheckingOut ? "Processing..." : "Checkout All Books"}
            </button>
          </div>
        )}
      </div>

      {!username && (
        <div style={{
          background: "rgba(255, 193, 7, 0.1)",
          border: "1px solid #ffc107",
          borderRadius: "8px",
          padding: "15px",
          marginBottom: "20px",
          textAlign: "center"
        }}>
          <p style={{ margin: 0, color: "#856404" }}>
            Please <a href="/login" style={{ color: "#082155", fontWeight: "bold" }}>log in</a> to checkout books from your cart.
          </p>
        </div>
      )}

      {cartItems.length === 0 ? (
        <div style={{ textAlign: "center", padding: "50px" }}>
          <h3>Your cart is empty</h3>
          <p>Browse our <a href="/books" style={{ color: "#082155" }}>book collection</a> to add books to your cart.</p>
        </div>
      ) : (
        <div style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fill, minmax(300px, 1fr))",
          gap: "20px"
        }}>
          {cartItems.map((book) => (
            <div key={book.bookId} style={{
              backgroundColor: "rgba(255, 255, 255, 0.95)",
              borderRadius: "12px",
              padding: "20px",
              boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
              border: "1px solid #e0e0e0",
              backdropFilter: "blur(5px)"
            }}>
              {/* Book Cover */}
              <div style={{ textAlign: "center", marginBottom: "15px" }}>
                {book.imageUrl ? (
                  <img
                    src={book.imageUrl}
                    alt={`Cover of ${book.title}`}
                    style={{
                      width: "100%",
                      maxWidth: "150px",
                      height: "200px",
                      objectFit: "cover",
                      borderRadius: "8px",
                      border: "2px solid #082155"
                    }}
                  />
                ) : (
                  <div style={{
                    width: "150px",
                    height: "200px",
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
                    No Cover
                  </div>
                )}
              </div>

              {/* Book Details */}
              <h3 style={{ fontSize: "1.2rem", marginBottom: "10px", textAlign: "center" }}>{book.title}</h3>
              <p style={{ margin: "5px 0", fontSize: "14px" }}><strong>Author:</strong> {book.author}</p>
              <p style={{ margin: "5px 0", fontSize: "14px" }}><strong>Genre:</strong> {book.genre}</p>
              <p style={{ margin: "5px 0", fontSize: "14px" }}><strong>ISBN:</strong> {book.isbn}</p>
              <p style={{ margin: "5px 0", fontSize: "14px" }}><strong>Available:</strong> {book.availableCopies} copies</p>

              {/* Remove from Cart Button */}
              <button
                onClick={() => handleRemoveFromCart(book.bookId)}
                style={{
                  width: "100%",
                  padding: "10px",
                  backgroundColor: "#dc3545",
                  color: "white",
                  border: "none",
                  borderRadius: "8px",
                  cursor: "pointer",
                  marginTop: "15px",
                  fontSize: "14px"
                }}
              >
                Remove from Cart
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
