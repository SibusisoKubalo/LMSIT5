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
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
          gap: "20px",
        }}
      >
        {[
          { title: "📖 Browse Books", desc: "See what’s available in our library.", link: "/books" },
          { title: "👤 Sign Up", desc: "Create an account to borrow books and track your activity.", link: "/signup" },
          { title: "🔐 Login", desc: "Already have an account? Log in to continue.", link: "/login" },
        ].map((card) => (
          <Link
            key={card.title}
            to={card.link}
            style={{
              textDecoration: "none",
              color: "#082155",
              backgroundColor: "#97aff0ff",
              padding: "20px",
              borderRadius: "12px",
              boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-between",
              transition: "transform 0.2s",
            }}
            onMouseEnter={(e) => (e.currentTarget.style.transform = "translateY(-5px)")}
            onMouseLeave={(e) => (e.currentTarget.style.transform = "translateY(0)")}
          >
            <h2 style={{ marginBottom: "10px" }}>{card.title}</h2>
            <p>{card.desc}</p>
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
              }}
            >
              Go
            </button>
          </Link>
        ))}
      </div>
    </div>
  );
}
