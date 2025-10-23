import { Link } from "react-router-dom";

export default function AdminHeader({ user, logout }) {
  return (
    <header className="header admin-header">
      <h1>👨‍💼 Admin Panel {user.username}</h1>
      <nav>
        <Link to="/admin">Dashboard</Link>
        <Link to="/adminbooks">Manage Books</Link>
        <Link to="/admincustomers">Manage Users</Link>
        <Link to="/adminnotifications">Manage Notifications</Link>
      </nav>
      <div className="user-info">
        <button onClick={logout}>Logout</button>
      </div>
    </header>
  );
}
