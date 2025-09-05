import { useEffect, useState } from "react";
import axios from "axios";

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/api/notifications")
      .then(res => setNotifications(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading notifications...</p>;
  if (error) return <p>Error loading notifications.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">🔔 Notifications</h2>
      <ul className="card-list">
  {notifications.map(n => (
    <li key={n.notificationId} className="card-item">
      {n.content} <br />
      <small>{new Date(n.dateCreated).toLocaleString()}</small>
    </li>
  ))}
</ul>
    </div>
  );
}