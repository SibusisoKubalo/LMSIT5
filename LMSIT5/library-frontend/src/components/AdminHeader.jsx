import { Link, useNavigate } from "react-router-dom";

export default function AdminHeader({ }) {
    const navigate = useNavigate();

  const logout = () => {
    // TODO: clear authentication state here
    console.log("Logout clicked");

    // Redirect to normal dashboard
    navigate("/"); 
  };

  return (
    <header className="header admin-header">
      <h1>👨‍💼 Admin Panel</h1>
      <nav>
        <Link to="/admin">Admin Home</Link>
        <Link to="/adminbooks">Manage Books</Link>
        <Link to="/admincustomers">Manage Users</Link>
        <Link to="/adminlibrarians">Manage Librarians</Link>
        <button onClick={logout}>Logout</button>
      </nav>
    </header>
  );
}
