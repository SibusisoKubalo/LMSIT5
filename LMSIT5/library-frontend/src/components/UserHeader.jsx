import { Link } from "react-router-dom";
import { useCart } from "../contexts/CartContext";

export default function UserHeader({ user, logout }) {
  const { getCartCount } = useCart();
  const cartCount = getCartCount();

  return (
    <header className="header user-header">
      <h1>📚 Library System</h1>
      <nav>
        <Link to="/dashboard">Home</Link>
        <Link to="/books">Books</Link>
        <Link to="/library">Library</Link>
        <Link to="/notifications">Notifications</Link>
        <Link to="/about">About</Link>
      </nav>
      <div className="user-info">
        <Link to="/cart" className="cart-link">
          <div className="cart-icon">
            🛒
            {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
          </div>
        </Link>
        <span>👋 Welcome {user.username}!</span>
        <button onClick={logout}>Logout</button>
      </div>
    </header>
  );
}
