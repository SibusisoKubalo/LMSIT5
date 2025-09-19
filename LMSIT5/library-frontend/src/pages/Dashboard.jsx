export default function Dashboard() {
  const stats = { books: 120, borrowed: 15, notifications: 3 };
  const cards = [
    { title: "Available Books", value: stats.books },
    { title: "Books Borrowed", value: stats.borrowed },
    { title: "Notifications", value: stats.notifications },
  ];

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h1 style={{ fontSize: "2rem", marginBottom: "10px" }}>📚 Your Library Dashboard</h1>
        <p style={{ fontSize: "1rem" }}>Welcome back! Track your library activity below.</p>
      </header>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
          gap: "20px",
        }}
      >
        {cards.map((card) => (
          <div
            key={card.title}
            style={{
              backgroundColor: "#97aff0ff",
              padding: "20px",
              borderRadius: "12px",
              boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              justifyContent: "center",
              textAlign: "center",
              transition: "transform 0.2s",
            }}
            onMouseEnter={(e) => (e.currentTarget.style.transform = "translateY(-5px)")}
            onMouseLeave={(e) => (e.currentTarget.style.transform = "translateY(0)")}
          >
            <h2 style={{ marginBottom: "10px" }}>{card.title}</h2>
            <p style={{ fontSize: "1.5rem", fontWeight: "bold" }}>{card.value}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

