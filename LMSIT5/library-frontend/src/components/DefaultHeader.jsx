import { Link } from "react-router-dom";
import { useCart } from "../contexts/CartContext";

export default function DefaultHeader() {
  const { getCartCount } = useCart();
  const cartCount = getCartCount();

  return (
    <header className="header default-header">
      <h1>📚 Library System</h1>
      <nav>
        <Link to="/defaultdashboard">Home</Link>
        <Link to="/books">Books</Link>
        <Link to="/about">About</Link>
      </nav>
      <div className="auth-buttons">
        <Link to="/cart" className="cart-link">
          <div className="cart-icon">
            🛒
            {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
          </div>
        </Link>
        <Link to="/login"><button>Login</button></Link>
      </div>
    </header>
  );
}
