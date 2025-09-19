import { useState } from "react";       // ✅ import useState
import { useNavigate } from "react-router-dom"; // ✅ import useNavigate
import api from "../api";

export default function Login({ setUser }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post("/auth/login", form, { withCredentials: true });
      console.log("Logged in user:", res.data);

      // Save in localStorage & state
      localStorage.setItem("user", JSON.stringify(res.data));
      setUser(res.data);

      // Redirect based on role
      if (res.data.role === "LIBRARIAN") {
        navigate("/admin");
      } else {
        navigate("/dashboard"); // CUSTOMER dashboard
      }
    } catch (err) {
      console.error("Login failed:", err.response?.data || err.message);
      alert("Login failed: " + (err.response?.data || err.message));
    }
  };

  const formStyle = {
  display: "flex",
  flexDirection: "column",
  gap: "15px",
  width: "300px",
  margin: "50px auto",
  padding: "30px",
  borderRadius: "10px",
  backgroundColor: "#f0f4f8",
  boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
};

const inputStyle = {
  padding: "10px",
  borderRadius: "5px",
  border: "1px solid #ccc",
  fontSize: "16px",
};

const buttonStyle = {
  padding: "10px",
  borderRadius: "5px",
  border: "none",
  backgroundColor: "#082155",
  color: "#fff",
  fontSize: "16px",
  cursor: "pointer",
  transition: "background 0.3s",
};

  const titleStyle = {
  textAlign: "center",
  color: "#082155",
  marginBottom: "20px",
  fontFamily: "Arial, sans-serif",
};

// LOGIN COMPONENT
return (
  <div style={formStyle}>
    <h2 style={titleStyle}>Login</h2>
    <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
      <input name="username" placeholder="Username" onChange={handleChange} style={inputStyle} required />
      <input type="password" name="password" placeholder="Password" onChange={handleChange} style={inputStyle} required />
      <button type="submit" style={buttonStyle}>Login</button>
    </form>
  </div>
);
}
