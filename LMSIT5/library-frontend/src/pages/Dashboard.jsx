import { useEffect, useState } from "react";
import axios from "axios";

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("/api/dashboard")
      .then(res => setStats(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading dashboard...</p>;
  if (error) return <p>Error loading dashboard.</p>;

  const cards = [
    { title: "Books", value: stats.books, color: "#00cec9" },
    { title: "Customers", value: stats.customers, color: "#6c5ce7" },
    { title: "Librarians", value: stats.librarians, color: "#fd79a8" },
    { title: "Notifications", value: stats.notifications, color: "#e17055" },
  ];

  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">📊 Dashboard</h2>
      <div className="dashboard-grid">
        {cards.map(stat => (
          <div key={stat.title} className="dashboard-card" style={{ borderTopColor: stat.color }}>
            <h3>{stat.title}</h3>
            <p className="stat-value">{stat.value}</p>
          </div>
        ))}
      </div>
    </div>
  );
}