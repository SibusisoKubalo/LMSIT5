import { Link } from "react-router-dom";

export default function DefaultHeader() {
  return (
    <header className="header default-header">
      <h1>📚 Library System</h1>
      <nav>
        <Link to="/defaultdashboard">Home</Link>
        <Link to="/books">Books</Link>
      </nav>
      <div className="auth-buttons">
        <Link to="/login"><button>Login</button></Link>
        <Link to="/signup"><button>Sign Up</button></Link>
      </div>
    </header>
  );
}
