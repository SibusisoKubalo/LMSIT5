import { Link } from "react-router-dom";

export default function UserHeader({ user, logout }) {
  return (
    <header className="header user-header">
      <h1>📚 Library System</h1>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/books">Books</Link>
        <Link to="/library">Library</Link>
        <Link to="/notifications">Notifications</Link>
      </nav>

      <div>
        {user ? (
          <>
            <span>
              👋 {user.name} ({user.role})
            </span>
            <button onClick={logout}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login">
            <button>Login</button>
            </Link>
            <Link to="/signup">
            <button>Sign Up</button>
            </Link>
          </>
        )}
      </div>
    </header>
  );
}
