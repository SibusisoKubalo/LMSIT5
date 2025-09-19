import { useEffect, useState } from "react";
import api from "../api";

export default function AdminNotifications() {
  const [notifications, setNotifications] = useState([]);
  const [newNotification, setNewNotification] = useState("");

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

  const handleAddNotification = async (e) => {
    e.preventDefault();
    if (!newNotification.trim()) return;

    try {
      await api.post("/notifications", {
        content: newNotification,
        dateCreated: new Date(),
      });
      setNewNotification("");
      fetchNotifications();
    } catch (error) {
      console.error("Error sending notification:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/notifications/${id}`);
      setNotifications(notifications.filter((n) => n.notificationId !== id));
    } catch (error) {
      console.error("Error deleting notification:", error);
    }
  };

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h2 style={{ fontSize: "2rem", marginBottom: "10px" }}>📢 Manage Notifications</h2>
        <p>Send and manage user notifications.</p>
      </header>

      {/* Add Notification Form */}
      <form
        onSubmit={handleAddNotification}
        style={{
          marginBottom: "30px",
          backgroundColor: "#f0f4ff",
          padding: "20px",
          borderRadius: "12px",
          boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
          display: "flex",
          gap: "15px",
        }}
      >
        <input
          type="text"
          placeholder="Enter new notification..."
          value={newNotification}
          onChange={(e) => setNewNotification(e.target.value)}
          style={{
            flex: "1",
            padding: "10px",
            borderRadius: "8px",
            border: "1px solid #082155",
          }}
        />
        <button
          type="submit"
          style={{
            padding: "10px 20px",
            backgroundColor: "#082155",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            fontWeight: "bold",
          }}
        >
          ➕ Send
        </button>
      </form>

      {/* Notifications List */}
      {notifications.length === 0 ? (
        <p>No notifications found.</p>
      ) : (
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
            gap: "20px",
          }}
        >
          {notifications.map((n) => (
            <div
              key={n.notificationId}
              style={{
                backgroundColor: "#fff",
                border: "1px solid #ddd",
                borderRadius: "12px",
                boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
                padding: "20px",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
              }}
            >
              <p style={{ fontSize: "1rem", marginBottom: "10px" }}>{n.content}</p>
              <small style={{ color: "#555", marginBottom: "10px" }}>
                {new Date(n.dateCreated).toLocaleString()}
              </small>
              <button
                onClick={() => handleDelete(n.notificationId)}
                style={{
                  backgroundColor: "#f44336",
                  color: "#fff",
                  border: "none",
                  padding: "8px 12px",
                  borderRadius: "6px",
                  cursor: "pointer",
                  alignSelf: "flex-end",
                }}
              >
                ❌ Delete
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
