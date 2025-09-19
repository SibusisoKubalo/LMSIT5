import { Link } from "react-router-dom";

export default function UserHeader({ user, logout }) {
  return (
    <header className="header user-header">
      <h1>📚 Library System</h1>
      <nav>
        <Link to="/dashboard">Home</Link>
        <Link to="/books">Books</Link>
        <Link to="/library">Library</Link>
        <Link to="/notifications">Notifications</Link>
      </nav>
      <div className="user-info">
        <span>👋 Welcome {user.username}!</span>
        <button onClick={logout}>Logout</button>
      </div>
    </header>
  );
}

