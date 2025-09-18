export default function Librarians() {
  const librarians = [
    { id: 1, name: "min One", email: "min1@example.com" },
    { id: 2, name: "min Two", email: "min2@example.com" },
  ];

  return (
    <div className="page-container">
      <h2>🧑‍🏫 Librarians</h2>
      <table className="data-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {librarians.map(l => (
            <tr key={l.id}>
              <td>{l.name}</td>
              <td>{l.email}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
