import { useEffect, useState } from "react";
import axios from "axios";

export default function Library() {
  const [library, setLibrary] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("/api/library")
      .then(res => setLibrary(res.data))
      .catch(err => setError(err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading library info...</p>;
  if (error) return <p>Error loading library info.</p>;

  return (
    <div className="page-container">
      <h2 className="page-title">🏛 Library Info</h2>
      <div className="info-card">
        <p><strong>Name:</strong> {library.name}</p>
        <p><strong>Location:</strong> {library.location}</p>
        <p><strong>Total Books:</strong> {library.totalBooks}</p>
      </div>
    </div>
  );
}