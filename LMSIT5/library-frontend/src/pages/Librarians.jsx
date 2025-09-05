import { useEffect, useState } from "react";
import axios from "axios";

export default function Librarians() {
  const [librarians, setLibrarians] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [form, setForm] = useState({ id: "", name: "" });
  const [editing, setEditing] = useState(false);

  const API_URL = "http://localhost:8080/api/librarians";

  const fetchLibrarians = () => {
    setLoading(true);
    axios
      .get(API_URL)
      .then((res) => setLibrarians(res.data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchLibrarians();
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

const handleSubmit = (e) => {
  e.preventDefault();
  if (editing) {
    axios
      .put(API_URL, { id: form.id, name: form.name }) 
      .then(() => {
        fetchLibrarians();
        setForm({ id: "", name: "" });
        setEditing(false);
      })
      .catch((err) => setError(err));
  } else {
    axios
      .post(API_URL, { name: form.name })
      .then(() => {
        fetchLibrarians();
        setForm({ id: "", name: "" });
      })
      .catch((err) => setError(err));
  }
};

  const handleDelete = (id) => {
    axios
      .delete(`${API_URL}/${id}`)
      .then(() => fetchLibrarians())
      .catch((err) => setError(err));
  };

  const handleEdit = (librarian) => {
    setForm({ id: librarian.id, name: librarian.name });
    setEditing(true);
  };

  if (loading) return <p>Loading librarians...</p>;
  if (error) return <p>Error loading librarians.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">📚 Librarians</h2>

      <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={form.name}
          onChange={handleChange}
          required
        />
        <button type="submit">{editing ? "Update" : "Add"}</button>
      </form>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {librarians.map((l) => (
            <tr key={l.id}>
              <td>{l.id}</td>
              <td>{l.name}</td>
              <td>
                <button onClick={() => handleEdit(l)}>Edit</button>
                <button onClick={() => handleDelete(l.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}