import { useEffect, useState } from "react";
import api from "../api"; // Axios instance

export default function AdminCustomers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newCustomer, setNewCustomer] = useState({ username: "", password: "" });

  // Fetch all customers
  const fetchCustomers = () => {
    setLoading(true);
    api.get("/api/customers")
      .then(res => {
        setCustomers(res.data);
      })
      .catch(err => {
        console.error("Failed to load customers", err);
        alert("Failed to load customers");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  // Add customer
  const handleAdd = (e) => {
    e.preventDefault();
    if (!newCustomer.username || !newCustomer.password) return;

    api.post("/api/customers/register", { ...newCustomer, role: "CUSTOMER" })
      .then(() => {
        alert("Customer added successfully!");
        setNewCustomer({ username: "", password: "" });
        fetchCustomers();
      })
      .catch(err => {
        console.error("Failed to add customer", err);
        alert("Failed to add customer: " + (err.response?.data?.error || err.message));
      });
  };

  // Delete customer
  const handleDelete = (id) => {
    if (!window.confirm("Are you sure you want to delete this customer?")) return;

    api.delete(`/api/customers/${id}`)
      .then(() => fetchCustomers())
      .catch(err => {
        console.error("Failed to delete customer", err);
        alert("Failed to delete customer: " + (err.response?.data?.error || err.message));
      });
  };

  if (loading) return <p style={{ color: "#082155" }}>Loading customers...</p>;

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h2 style={{ fontSize: "2rem", marginBottom: "10px" }}>👤 Customers</h2>
        <p>Manage and organize customer accounts.</p>
      </header>

      {/* Add Customer Form */}
      <form onSubmit={handleAdd} style={{ marginBottom: "30px", display: "flex", gap: "15px", backgroundColor: "#f0f4ff", padding: "20px", borderRadius: "12px", boxShadow: "0 2px 8px rgba(0,0,0,0.1)" }}>
        <input
          type="text"
          placeholder="Username"
          value={newCustomer.username}
          onChange={(e) => setNewCustomer({ ...newCustomer, username: e.target.value })}
          required
          style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155", flex: 1 }}
        />
        <input
          type="password"
          placeholder="Password"
          value={newCustomer.password}
          onChange={(e) => setNewCustomer({ ...newCustomer, password: e.target.value })}
          required
          style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155", flex: 1 }}
        />
        <button type="submit" style={{ padding: "10px 15px", backgroundColor: "#082155", color: "#fff", border: "none", borderRadius: "8px", cursor: "pointer", fontWeight: "bold" }}>
          ➕ Add Customer
        </button>
      </form>

      {/* Customer Table */}
      {customers.length === 0 ? (
        <p>No customers found.</p>
      ) : (
        <table style={{ borderCollapse: "collapse", width: "100%", boxShadow: "0 2px 8px rgba(0,0,0,0.1)", borderRadius: "12px", overflow: "hidden" }}>
          <thead style={{ backgroundColor: "#082155", color: "#fff" }}>
            <tr>
              <th style={{ padding: "12px", textAlign: "left" }}>ID</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Username</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Password</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((c, i) => (
              <tr key={c.customerId} style={{ backgroundColor: i % 2 === 0 ? "#fff" : "#f9f9f9" }}>
                <td style={{ padding: "10px" }}>{c.customerId}</td>
                <td style={{ padding: "10px" }}>{c.username}</td>
                <td style={{ padding: "10px" }}>******</td>
                <td style={{ padding: "10px" }}>
                  <button onClick={() => handleDelete(c.customerId)} style={{ backgroundColor: "#f44336", color: "#fff", border: "none", padding: "6px 12px", borderRadius: "6px", cursor: "pointer" }}>
                    ❌ Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
