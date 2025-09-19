import { useEffect, useState } from "react";
import api from "../api";

export default function AdminLibrary() {
  const [libraries, setLibraries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newLibrary, setNewLibrary] = useState({
    name: "",
    username: "",
    password: "",
    num: "",
    email: "",
  });
  const [errors, setErrors] = useState({});

  const fetchLibraries = async () => {
    try {
      const res = await api.get("/library");
      setLibraries(res.data);
    } catch (err) {
      console.error("Failed to fetch libraries:", err.response?.data || err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchLibraries();
  }, []);

  const validate = () => {
    const newErrors = {};
    if (!newLibrary.name.trim()) newErrors.name = "Name is required";
    if (!newLibrary.username.trim()) newErrors.username = "Username is required";
    if (!newLibrary.password.trim()) newErrors.password = "Password is required";
    if (!newLibrary.email.trim()) newErrors.email = "Email is required";
    else if (!/\S+@\S+\.\S+/.test(newLibrary.email)) newErrors.email = "Invalid email";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    const payload = {
      name: newLibrary.name,
      username: newLibrary.username,
      password: newLibrary.password,
      num: newLibrary.num ? parseInt(newLibrary.num) : 0,
      email: newLibrary.email,
    };

    try {
      await api.post("/library", payload);
      setNewLibrary({ name: "", username: "", password: "", num: "", email: "" });
      fetchLibraries();
    } catch (err) {
      console.error("Failed to add library:", err.response?.data || err.message);
      alert("Failed to add library: " + JSON.stringify(err.response?.data));
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this library?")) return;
    try {
      await api.delete(`/library/${id}`);
      fetchLibraries();
    } catch (err) {
      console.error("Failed to delete library:", err.response?.data || err.message);
    }
  };

  if (loading) return <p style={{ color: "#082155" }}>Loading libraries...</p>;

  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
      <header style={{ marginBottom: "30px" }}>
        <h2 style={{ fontSize: "2rem", marginBottom: "10px" }}>🏛 Libraries</h2>
        <p>Manage library branches and librarian accounts.</p>
      </header>

      {/* Add Library Form */}
      <form
        onSubmit={handleAdd}
        style={{
          marginBottom: "30px",
          backgroundColor: "#f0f4ff",
          padding: "20px",
          borderRadius: "12px",
          boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
          display: "grid",
          gap: "15px",
          maxWidth: "600px",
        }}
      >
        {["name", "username", "password", "email"].map((field) => (
          <div key={field} style={{ display: "flex", flexDirection: "column" }}>
            <input
              type={
                field === "password"
                  ? "password"
                  : field === "email"
                  ? "email"
                  : "text"
              }
              placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
              value={newLibrary[field]}
              onChange={(e) =>
                setNewLibrary({ ...newLibrary, [field]: e.target.value })
              }
              style={{
                padding: "10px",
                borderRadius: "8px",
                border: "1px solid #082155",
              }}
            />
            {errors[field] && (
              <span style={{ color: "red", fontSize: "0.9rem" }}>
                {errors[field]}
              </span>
            )}
          </div>
        ))}

        <input
          type="number"
          placeholder="Contact Number"
          value={newLibrary.num}
          onChange={(e) =>
            setNewLibrary({ ...newLibrary, num: e.target.value })
          }
          style={{
            padding: "10px",
            borderRadius: "8px",
            border: "1px solid #082155",
          }}
        />

        <button
          type="submit"
          style={{
            padding: "10px 15px",
            backgroundColor: "#082155",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer",
            fontWeight: "bold",
            marginTop: "10px",
          }}
        >
          ➕ Add Library
        </button>
      </form>

      {/* Libraries Table */}
      {libraries.length === 0 ? (
        <p>No libraries found.</p>
      ) : (
        <table
          style={{
            borderCollapse: "collapse",
            width: "100%",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            borderRadius: "12px",
            overflow: "hidden",
          }}
        >
          <thead style={{ backgroundColor: "#082155", color: "#fff" }}>
            <tr>
              {["ID", "Name", "Username", "Email", "Contact", "Actions"].map(
                (h) => (
                  <th
                    key={h}
                    style={{ padding: "12px", textAlign: "left", fontWeight: "bold" }}
                  >
                    {h}
                  </th>
                )
              )}
            </tr>
          </thead>
          <tbody>
            {libraries.map((lib, i) => (
              <tr
                key={lib.id}
                style={{
                  backgroundColor: i % 2 === 0 ? "#fff" : "#f9f9f9",
                }}
              >
                <td style={{ padding: "10px" }}>{lib.id}</td>
                <td style={{ padding: "10px" }}>{lib.name}</td>
                <td style={{ padding: "10px" }}>{lib.username}</td>
                <td style={{ padding: "10px" }}>{lib.email}</td>
                <td style={{ padding: "10px" }}>{lib.num}</td>
                <td style={{ padding: "10px" }}>
                  <button
                    onClick={() => handleDelete(lib.id)}
                    style={{
                      backgroundColor: "#f44336",
                      color: "#fff",
                      border: "none",
                      padding: "6px 12px",
                      borderRadius: "6px",
                      cursor: "pointer",
                    }}
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
