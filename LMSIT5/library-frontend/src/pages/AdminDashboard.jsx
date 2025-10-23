import { Link } from "react-router-dom";

export default function AdminDashboard() {
  const cards = [
    { to: "/adminbooks", title: "📚 Books", desc: "Manage and organize the library’s books." },
    { to: "/admincustomers", title: "👤 Customers", desc: "View, add, and manage customer accounts." },
    { to: "/adminnotifications", title: "🔔 Notifications", desc: "Send updates and alerts to users." },
  ];

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h1 style={{ fontSize: "2rem", marginBottom: "10px" }}>👨‍💼 Admin Dashboard</h1>
        <p style={{ fontSize: "1rem" }}>
          Welcome back, Admin! 🚀 Manage everything in one place.
        </p>
      </header>

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
  );
}
