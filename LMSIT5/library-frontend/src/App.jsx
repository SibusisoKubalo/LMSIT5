
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Header from "./components/Header";
import Footer from "./components/Footer";

import Dashboard from "./pages/Dashboard";
import Books from "./pages/Books";
import Notifications from "./pages/Notifications";
import Library from "./pages/Library";
import Login from "./pages/Login";
import Signup from "./pages/Signup";

import AdminDashboard from "./pages/AdminDashboard";
import AdminCustomers from "./pages/AdminCustomers";
import AdminLibrary from "./pages/AdminLibrary";
import AdminBooks from "./pages/AdminBooks";

import "./App.css";

export default function App() {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <div className="main-layout">
          <main className="page-content">
            <Routes>
              {/* User pages */}
              <Route path="/" element={<Dashboard />} />
              <Route path="/books" element={<Books />} />
              <Route path="/notifications" element={<Notifications />} />
              <Route path="/library" element={<Library />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup />} />

              {/* Admin pages */}
              <Route path="/admin" element={<AdminDashboard />} />
              <Route path="/adminbooks" element={<AdminBooks />} />
              <Route path="/admincustomers" element={<AdminCustomers />} />
              <Route path="/adminlibrary" element={<AdminLibrary />} />
            </Routes>
          </main>
        </div>
        <Footer />
      </div>
    </Router>
  );
}
