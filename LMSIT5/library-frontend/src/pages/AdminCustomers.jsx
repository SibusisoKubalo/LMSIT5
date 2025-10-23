import { useEffect, useState } from "react";
import api from "../api"; // Axios instance

export default function AdminCustomers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newCustomer, setNewCustomer] = useState({
    username: "",
    password: "",
    email: "",
    name: "",
    surname: ""
  });

  // Fetch all customers
  const fetchCustomers = () => {
    setLoading(true);
    api.get("/customers")
      .then(res => {
        setCustomers(res.data);
        setLoading(false);
      })
      .catch(_err => {
        console.error("Failed to load customers", _err);
        alert("Failed to load customers: " + (_err.response?.data?.error || _err.message));
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  // Add customer
  const handleAdd = (e) => {
    e.preventDefault();
    if (!newCustomer.username || !newCustomer.password) {
      alert("Username and password are required");
      return;
    }

    api.post("/customers/register", newCustomer)
      .then(() => {
        alert("Customer added successfully!");
        setNewCustomer({ username: "", password: "", email: "", name: "", surname: "" });
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

    api.delete(`/customers/${id}`)
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
      <form onSubmit={handleAdd} style={{ marginBottom: "30px", display: "flex", flexDirection: "column", gap: "15px", backgroundColor: "#f0f4ff", padding: "20px", borderRadius: "12px", boxShadow: "0 2px 8px rgba(0,0,0,0.1)" }}>
        <h3>Add New Customer</h3>
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "15px" }}>
          <input
            type="text"
            placeholder="Username"
            value={newCustomer.username}
            onChange={(e) => setNewCustomer({ ...newCustomer, username: e.target.value })}
            required
            style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
          />
          <input
            type="email"
            placeholder="Email"
            value={newCustomer.email}
            onChange={(e) => setNewCustomer({ ...newCustomer, email: e.target.value })}
            style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
          />
          <input
            type="text"
            placeholder="First Name"
            value={newCustomer.name}
            onChange={(e) => setNewCustomer({ ...newCustomer, name: e.target.value })}
            style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
          />
          <input
            type="text"
            placeholder="Last Name"
            value={newCustomer.surname}
            onChange={(e) => setNewCustomer({ ...newCustomer, surname: e.target.value })}
            style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
          />
        </div>
        <input
          type="password"
          placeholder="Password"
          value={newCustomer.password}
          onChange={(e) => setNewCustomer({ ...newCustomer, password: e.target.value })}
          required
          style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
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
              <th style={{ padding: "12px", textAlign: "left" }}>Name</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Email</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Role</th>
              <th style={{ padding: "12px", textAlign: "left" }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((c, i) => (
              <tr key={c.customerId} style={{ backgroundColor: i % 2 === 0 ? "#fff" : "#f9f9f9" }}>
                <td style={{ padding: "10px" }}>{c.customerId}</td>
                <td style={{ padding: "10px" }}>{c.username}</td>
                <td style={{ padding: "10px" }}>{c.name} {c.surname}</td>
                <td style={{ padding: "10px" }}>{c.email}</td>
                <td style={{ padding: "10px" }}>{c.role}</td>
                <td style={{ padding: "10px" }}>
                  <button
                    onClick={() => handleDelete(c.customerId)}
                    style={{ backgroundColor: "#f44336", color: "#fff", border: "none", padding: "6px 12px", borderRadius: "6px", cursor: "pointer" }}
                  >
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
