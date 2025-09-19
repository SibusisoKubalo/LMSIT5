import { useEffect, useState } from "react";
import api from "../api";

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const response = await api.get("/notifications");
      setNotifications(response.data);
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  return (
    <div
      style={{
        padding: "30px",
        fontFamily: "Arial, sans-serif",
        color: "#082155",
      }}
    >
      <h2 style={{ fontSize: "2rem", marginBottom: "20px" }}>🔔 Notifications</h2>

      {notifications.length === 0 ? (
        <p>No notifications available.</p>
      ) : (
        <ul
          style={{
            listStyle: "none",
            padding: 0,
            display: "flex",
            flexDirection: "column",
            gap: "15px",
          }}
        >
          {notifications.map((n) => (
            <li
              key={n.notificationId}
              style={{
                backgroundColor: "#f9f9f9",
                border: "1px solid #ddd",
                borderRadius: "10px",
                padding: "15px",
                boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
              }}
            >
              <strong style={{ display: "block", marginBottom: "8px" }}>
                {new Date(n.dateCreated).toLocaleString()}
              </strong>
              {n.content}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
