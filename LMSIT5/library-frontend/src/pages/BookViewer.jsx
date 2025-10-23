import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api";

export default function BookViewer({ username }) {
  const { bookId } = useParams();
  const navigate = useNavigate();
  const [bookData, setBookData] = useState(null);
  const [hasAccess, setHasAccess] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    checkAccess();
  }, [bookId, username]);

  const checkAccess = async () => {
    if (!username) {
      setError("Please log in to view books");
      setLoading(false);
      return;
    }

    try {
      const response = await api.get(`/viewer/access/${bookId}?username=${username}`);
      const data = response.data;

      if (data.hasAccess) {
        setHasAccess(true);
        setBookData(data);
        console.log("PDF URL:", data.pdfUrl); // Debug log
      } else {
        setError("Access denied. You must borrow this book to view it.");
      }
    } catch (err) {
      console.error("Access check error:", err);
      setError("Failed to verify book access");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "50vh",
        fontSize: "1.2rem",
        color: "#082155"
      }}>
        Loading book viewer...
      </div>
    );
  }

  if (error) {
    return (
      <div style={{ padding: "30px", textAlign: "center" }}>
        <div style={{
          background: "rgba(220, 53, 69, 0.1)",
          border: "1px solid #dc3545",
          borderRadius: "8px",
          padding: "20px",
          marginBottom: "20px"
        }}>
          <h3 style={{ color: "#dc3545", margin: "0 0 10px 0" }}>Access Denied</h3>
          <p style={{ color: "#721c24", margin: 0 }}>{error}</p>
        </div>
        <button
          onClick={() => navigate("/books")}
          style={{
            padding: "10px 20px",
            backgroundColor: "#082155",
            color: "white",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer"
          }}
        >
          Back to Books
        </button>
      </div>
    );
  }

  if (!hasAccess || !bookData) {
    return (
      <div style={{ padding: "30px", textAlign: "center" }}>
        <p>Unable to access book content</p>
      </div>
    );
  }

  return (
    <div style={{
      padding: "20px",
      fontFamily: "Arial, sans-serif",
      height: "100vh",
      display: "flex",
      flexDirection: "column"
    }}>
      {/* Header */}
      <div style={{
        background: "rgba(8, 33, 85, 0.9)",
        color: "white",
        padding: "15px 20px",
        borderRadius: "8px 8px 0 0",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center"
      }}>
        <div>
          <h2 style={{ margin: "0 0 5px 0", fontSize: "1.3rem" }}>{bookData.title}</h2>
          <p style={{ margin: 0, fontSize: "0.9rem", opacity: 0.8 }}>by {bookData.author}</p>
        </div>
        <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>
          <span style={{
            background: "rgba(255, 255, 255, 0.2)",
            padding: "5px 10px",
            borderRadius: "15px",
            fontSize: "0.8rem"
          }}>
            📖 View Only - No Download
          </span>
          <button
            onClick={() => navigate("/books")}
            style={{
              padding: "8px 15px",
              backgroundColor: "transparent",
              color: "white",
              border: "1px solid white",
              borderRadius: "5px",
              cursor: "pointer"
            }}
          >
            Close
          </button>
        </div>
      </div>

      {/* PDF Viewer */}
      <div style={{
        flex: 1,
        border: "1px solid #ddd",
        borderTop: "none",
        borderRadius: "0 0 8px 8px",
        overflow: "hidden",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
        background: "#f8f9fa"
      }}>
        {bookData.pdfUrl ? (
          <div style={{ textAlign: "center", padding: "40px" }}>
            <h3 style={{ color: "#082155", marginBottom: "20px" }}>📖 {bookData.title}</h3>
            <p style={{ color: "#666", marginBottom: "30px" }}>Click the button below to open your book in a new tab</p>
            <button
              onClick={() => window.open(bookData.pdfUrl, '_blank')}
              style={{
                padding: "15px 30px",
                backgroundColor: "#4CAF50",
                color: "white",
                border: "none",
                borderRadius: "8px",
                fontSize: "16px",
                fontWeight: "bold",
                cursor: "pointer",
                display: "flex",
                alignItems: "center",
                gap: "10px",
                margin: "0 auto"
              }}
            >
              📖 Open Book in New Tab
            </button>
            <p style={{ color: "#888", fontSize: "14px", marginTop: "20px" }}>
              The book will open in your browser's built-in PDF viewer
            </p>
          </div>
        ) : (
          <div style={{ textAlign: "center" }}>
            <h3>📄 Book content not available</h3>
            <p>This book doesn't have digital content available for viewing.</p>
          </div>
        )}
      </div>

      {/* Footer notice */}
      <div style={{
        background: "rgba(255, 193, 7, 0.1)",
        border: "1px solid #ffc107",
        borderRadius: "5px",
        padding: "10px",
        marginTop: "10px",
        textAlign: "center",
        fontSize: "0.9rem",
        color: "#856404"
      }}>
        🔒 This book is protected. You can view it while borrowed, but downloading is not permitted.
      </div>
    </div>
  );
}
