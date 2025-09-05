import { useEffect, useState } from "react";
import axios from "axios";

export default function Library() {
  const [libraries, setLibraries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const API_URL = "http://localhost:8080/api/library";

  useEffect(() => {
    axios.get(API_URL)
      .then(res => setLibraries(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading libraries...</p>;
  if (error) return <p>Error loading libraries.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">🏛 Libraries</h2>
      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email</th>
            <th>Phone (num)</th>
          </tr>
        </thead>
        <tbody>
          {libraries.map((lib) => (
            <tr key={lib.id}>
              <td>{lib.id}</td>
              <td>{lib.name}</td>
              <td>{lib.username}</td>
              <td>{lib.email}</td>
              <td>{lib.num}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}