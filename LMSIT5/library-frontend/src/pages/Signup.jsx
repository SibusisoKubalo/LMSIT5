import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

export default function Signup() {
  const [form, setForm] = useState({ username: "", password: "", role: "CUSTOMER" });
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post("/auth/signup", form);
      console.log(res.data); // should log "User registered successfully"
      navigate("/login");
    } catch (err) {
      console.error("Signup failed:", err.response?.data || err.message);
      alert("Signup failed: " + (err.response?.data || err.message));
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

const buttonHoverStyle = {
  backgroundColor: "#0a2a70",
};

const titleStyle = {
  textAlign: "center",
  color: "#082155",
  marginBottom: "20px",
  fontFamily: "Arial, sans-serif",
};

// SIGN UP COMPONENT
return (
  <div style={formStyle}>
    <h2 style={titleStyle}>Sign Up</h2>
    <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
      <input name="username" placeholder="Username" onChange={handleChange} style={inputStyle} required />
      <input type="password" name="password" placeholder="Password" onChange={handleChange} style={inputStyle} required />
      <select name="role" value={form.role} onChange={handleChange} style={inputStyle}>
        <option value="CUSTOMER">Customer</option>
        <option value="LIBRARIAN">Librarian</option>
      </select>
      <button type="submit" style={buttonStyle}>Sign Up</button>
    </form>
  </div>
);}
