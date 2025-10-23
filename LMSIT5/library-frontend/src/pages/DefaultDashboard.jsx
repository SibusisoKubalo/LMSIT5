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

      {/* Mission and Vision Statements */}
      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(400px, 1fr))",
        gap: "30px",
        marginBottom: "40px"
      }}>
        {/* Mission Statement */}
        <div style={{
          background: "rgba(255, 255, 255, 0.95)",
          padding: "25px",
          borderRadius: "12px",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
          backdropFilter: "blur(10px)",
          border: "1px solid rgba(8, 33, 85, 0.1)"
        }}>
          <h2 style={{
            fontSize: "1.5rem",
            marginBottom: "15px",
            color: "#082155",
            display: "flex",
            alignItems: "center",
            gap: "10px"
          }}>
            🎯 Our Mission
          </h2>
          <p style={{
            fontSize: "1rem",
            lineHeight: "1.6",
            color: "#333",
            margin: 0
          }}>
            To provide accessible, innovative, and comprehensive library services that empower our community
            through knowledge, technology, and learning resources. We strive to create an inclusive environment
            where every individual can discover, learn, and grow through our diverse collection of books and
            digital resources.
          </p>
        </div>

        {/* Vision Statement */}
        <div style={{
          background: "rgba(255, 255, 255, 0.95)",
          padding: "25px",
          borderRadius: "12px",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
          backdropFilter: "blur(10px)",
          border: "1px solid rgba(8, 33, 85, 0.1)"
        }}>
          <h2 style={{
            fontSize: "1.5rem",
            marginBottom: "15px",
            color: "#082155",
            display: "flex",
            alignItems: "center",
            gap: "10px"
          }}>
            🔮 Our Vision
          </h2>
          <p style={{
            fontSize: "1rem",
            lineHeight: "1.6",
            color: "#333",
            margin: 0
          }}>
            To be the leading digital library platform that transforms how people access and engage with
            knowledge. We envision a future where learning knows no boundaries, where technology seamlessly
            connects readers with resources, and where our community thrives through shared knowledge and
            collaborative learning experiences.
          </p>
        </div>
      </div>

      {/* Browse Books Section */}
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
