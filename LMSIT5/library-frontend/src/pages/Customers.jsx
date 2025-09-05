import { useEffect, useState } from "react";
import axios from "axios";

export default function Customers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newCustomer, setNewCustomer] = useState({ username: "", password: "" });

  const fetchCustomers = () => {
    setLoading(true);
    axios.get("http://localhost:8080/api/customers")
      .then(res => setCustomers(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  const registerCustomer = () => {
    axios.post("http://localhost:8080/api/customers/register", newCustomer)
      .then(res => {
        if (res.data) {
          fetchCustomers();
          setNewCustomer({ username: "", password: "" });
        } else {
          alert("Failed to register customer.");
        }
      })
      .catch(err => console.error("Error registering customer:", err));
  };

  const deleteCustomer = (id) => {
    axios.delete(`http://localhost:8080/api/customers/${id}`)
      .then(res => {
        if (res.data) {
          setCustomers(customers.filter(c => c.customerId !== id));
        } else {
          alert("Failed to delete customer.");
        }
      })
      .catch(err => console.error("Error deleting customer:", err));
  };

  const getCustomerCount = () => {
    axios.get("http://localhost:8080/api/customers/count")
      .then(res => alert(`Customer count: ${res.data}`))
      .catch(err => console.error("Error fetching count:", err));
  };

  if (loading) return <p>Loading customers...</p>;
  if (error) return <p>Error loading customers.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">👤 Customers</h2>

      <div style={{ marginBottom: "20px" }}>
        <input
          placeholder="Username"
          value={newCustomer.username}
          onChange={(e) => setNewCustomer({...newCustomer, username: e.target.value})}
        />
        <input
          placeholder="Password"
          type="password"
          value={newCustomer.password}
          onChange={(e) => setNewCustomer({...newCustomer, password: e.target.value})}
        />
        <button onClick={registerCustomer}>Register</button>
      </div>

      <button onClick={getCustomerCount} style={{ marginBottom: "20px" }}>Get Customer Count</button>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Password</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(c => (
            <tr key={c.customerId}>
              <td>{c.customerId}</td>
              <td>{c.username}</td>
              <td>{c.password}</td>
              <td>
                <button onClick={() => deleteCustomer(c.customerId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}