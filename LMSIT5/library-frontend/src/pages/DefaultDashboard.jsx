import { Link } from "react-router-dom";

export default function DefaultDashboard() {
  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h1 style={{ fontSize: "2rem", marginBottom: "10px" }}>📚 Welcome to the Library System</h1>
        <p style={{ fontSize: "1rem" }}>
          Explore our collection, borrow books, and stay updated with notifications.
        </p>
      </header>

      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginTop: "40px",
        }}
      >
        <Link
          to="/books"
          style={{
            textDecoration: "none",
            color: "#082155",
            backgroundColor: "#97aff0ff",
            padding: "30px 40px",
            borderRadius: "12px",
            boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            transition: "all 0.3s ease",
            width: "300px",
            textAlign: "center",
          }}
          onMouseEnter={(e) => {
            e.currentTarget.style.transform = "translateY(-5px)";
            e.currentTarget.style.boxShadow = "0 6px 20px rgba(0,0,0,0.2)";
          }}
          onMouseLeave={(e) => {
            e.currentTarget.style.transform = "translateY(0)";
            e.currentTarget.style.boxShadow = "0 4px 12px rgba(0,0,0,0.15)";
          }}
        >
          <h2 style={{ marginBottom: "15px", fontSize: "1.5rem" }}>📖 Browse Books</h2>
          <p style={{ marginBottom: "20px", lineHeight: "1.5" }}>See what's available in our library.</p>
          <button
            style={{
              padding: "12px 24px",
              backgroundColor: "#082155",
              color: "#fff",
              border: "none",
              borderRadius: "8px",
              cursor: "pointer",
              fontWeight: "bold",
              fontSize: "16px",
              transition: "background 0.3s ease",
            }}
          >
            Go
          </button>
        </Link>
      </div>
    </div>
  );
}
