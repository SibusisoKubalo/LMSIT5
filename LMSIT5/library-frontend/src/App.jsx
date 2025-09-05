import { Routes, Route, Navigate, Link } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import Books from "./pages/Books";
import Customers from "./pages/Customers";
import Librarians from "./pages/Librarians";
import Notifications from "./pages/Notifications";
import Library from "./pages/Library";
import "./App.css";

export default function App() {
  return (
    <div className="app-layout">
      {/* Sidebar */}
      <aside className="sidebar">
        <h1 className="logo">📚 Library</h1>
        <nav>
          <ul>
            <li><Link className="nav-link" to="/dashboard">Dashboard</Link></li>
            <li><Link className="nav-link" to="/books">Books</Link></li>
            <li><Link className="nav-link" to="/customers">Customers</Link></li>
            <li><Link className="nav-link" to="/librarians">Librarians</Link></li>
            <li><Link className="nav-link" to="/notifications">Notifications</Link></li>
            <li><Link className="nav-link" to="/library">Library</Link></li>
          </ul>
        </nav>
      </aside>

      {/* Main content */}
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/books" element={<Books />} />
          <Route path="/customers" element={<Customers />} />
          <Route path="/librarians" element={<Librarians />} />
          <Route path="/notifications" element={<Notifications />} />
          <Route path="/library" element={<Library />} />
        </Routes>
      </main>
    </div>
  );
}