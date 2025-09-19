import { Link } from "react-router-dom";

export default function Dashboard() {
  const cards = [
    {
      to: "/books",
      title: "📚 Books",
      desc: "Browse and borrow books from the library."
    },
    {
      to: "/library",
      title: "🏛 Library",
      desc: "View library branches and information."
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
        <p style={{ fontSize: "1rem" }}>Welcome back! Access your library features below.</p>
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
            onMouseEnter={e => (e.currentTarget.style.transform = "translateY(-5px)")}
            onMouseLeave={e => (e.currentTarget.style.transform = "translateY(0)")}
          >
            <h2 style={{ marginBottom: "10px" }}>{card.title}</h2>
            <p style={{ flex: "1" }}>{card.desc}</p>
          </Link>
        ))}
      </div>
    </div>
  );
}
