import { useEffect, useState } from "react";
import axios from "axios";

export default function Librarians() {
  const [librarians, setLibrarians] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("/api/librarians")
      .then(res => setLibrarians(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading librarians...</p>;
  if (error) return <p>Error loading librarians.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">🧑‍🏫 Librarians</h2>
      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
          {librarians.map(l => (
            <tr key={l.id}>
              <td>{l.id}</td>
              <td>{l.name}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}