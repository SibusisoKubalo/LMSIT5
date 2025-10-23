import { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Header from "./components/Header";
import Footer from "./components/Footer";

import DefaultDashboard from "./pages/DefaultDashboard";
import Dashboard from "./pages/Dashboard";
import Books from "./pages/Books";
import Cart from "./pages/Cart";
import Notifications from "./pages/Notifications";
import Library from "./pages/Library";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import About from "./pages/About";

import AdminDashboard from "./pages/AdminDashboard";
import AdminCustomers from "./pages/AdminCustomers";
import AdminLibrary from "./pages/AdminLibrary";
import AdminBooks from "./pages/AdminBooks";
import AdminNotifications from "./pages/AdminNotifications";

export default function App() {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  });

  return (
    <Router>
      <div className="app-container">
        <Header user={user} setUser={setUser} />
        <div className="main-layout">
          <main className="page-content">
            <Routes>
              {/* Default / unsigned users */}
              <Route path="/" element={<DefaultDashboard />} />
              <Route path="/defaultdashboard" element={<DefaultDashboard />} />
              <Route path="/about" element={<About />} />
              <Route path="/cart" element={<Cart username={user?.username} />} />

              {/* Customer pages */}
              <Route path="/dashboard" element={user?.role === "CUSTOMER" ? <Dashboard /> : <DefaultDashboard />} />
              <Route path="/books" element={<Books username={user?.username} />} />
              <Route path="/notifications" element={<Notifications />} />
              <Route path="/library" element={<Library />} />
              <Route path="/login" element={<Login setUser={setUser} />} />
              <Route path="/signup" element={<Signup />} />

              {/* Admin pages */}
              <Route path="/admin" element={user?.role === "LIBRARIAN" ? <AdminDashboard /> : <DefaultDashboard />} />
              <Route path="/adminbooks" element={user?.role === "LIBRARIAN" ? <AdminBooks /> : <DefaultDashboard />} />
              <Route path="/admincustomers" element={user?.role === "LIBRARIAN" ? <AdminCustomers /> : <DefaultDashboard />} />
              <Route path="/adminlibrary" element={user?.role === "LIBRARIAN" ? <AdminLibrary /> : <DefaultDashboard />} />
              <Route path="/adminnotifications" element={user?.role === "LIBRARIAN" ? <AdminNotifications /> : <DefaultDashboard />} />

              {/* Catch-all for unknown paths */}
              <Route path="*" element={<DefaultDashboard />} />
            </Routes>
          </main>
        </div>
        <Footer />
      </div>
    </Router>
  );
}
