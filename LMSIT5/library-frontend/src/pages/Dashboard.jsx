export default function Dashboard() {
  const stats = { books: 120, customers: 45, librarians: 5, notifications: 10 };
  const cards = [
    { title: "Books", value: stats.books, color: "#00cec9" },
    { title: "Customers", value: stats.customers, color: "#6c5ce7" },
    { title: "Librarians", value: stats.librarians, color: "#fd79a8" },
    { title: "Notifications", value: stats.notifications, color: "#e17055" },
  ];

  return (
    <div className="page-container">
      <h2>📊 Dashboard</h2>
      <div className="dashboard-grid">
        {cards.map(card => (
          <div key={card.title} className="dashboard-card" style={{ borderTopColor: card.color }}>
            <h3>{card.title}</h3>
            <p className="stat-value">{card.value}</p>
          </div>
        ))}
      </div>
    </div>
  );
}