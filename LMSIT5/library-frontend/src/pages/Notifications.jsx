export default function Notifications() {
  const notifications = [
    { id: 1, message: "New book added: Dune", date: "2025-09-01" },
    { id: 2, message: "Customer Alice borrowed Harry Potter", date: "2025-09-02" },
  ];

  return (
    <div className="page-container">
      <h2>🔔 Notifications</h2>
      <ul>
        {notifications.map(n => (
          <li key={n.id}>
            <strong>{n.date}:</strong> {n.message}
          </li>
        ))}
      </ul>
    </div>
  );
}
