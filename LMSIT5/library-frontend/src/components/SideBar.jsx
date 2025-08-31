import { Link } from "react-router-dom";

function Sidebar() {
  return (
    <aside className="w-60 h-screen bg-gray-800 text-white p-4 fixed">
      <h2 className="text-xl font-bold mb-6">📚 Library</h2>
      <nav className="flex flex-col gap-4">
        <Link to="/dashboard" className="hover:bg-gray-700 p-2 rounded">🏠 Dashboard</Link>
        <Link to="/books" className="hover:bg-gray-700 p-2 rounded">📖 Books</Link>
        <Link to="/customers" className="hover:bg-gray-700 p-2 rounded">👤 Customers</Link>
        <Link to="/librarians" className="hover:bg-gray-700 p-2 rounded">👩‍🏫 Librarians</Link>
        <Link to="/library" className="hover:bg-gray-700 p-2 rounded">🏢 Library</Link>
        <Link to="/notifications" className="hover:bg-gray-700 p-2 rounded">🔔 Notifications</Link>
      </nav>
    </aside>
  );
}

export default Sidebar;