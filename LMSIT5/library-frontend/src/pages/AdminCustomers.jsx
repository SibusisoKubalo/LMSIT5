export default function AdminCustomers() {
  const customers = [
    { id: 1, name: "ice Smith", email: "ice@example.com" },
    { id: 2, name: "Bb Johnson", email: "bb@example.com" },
    { id: 3, name: "lie Lee", email: "lie@example.com" },
  ];

  return (
    <div className="page-container">
      <h2>👤 Customers</h2>
      <table className="data-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(c => (
            <tr key={c.id}>
              <td>{c.name}</td>
              <td>{c.email}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
