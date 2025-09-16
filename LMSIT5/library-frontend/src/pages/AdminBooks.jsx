export default function AdminBooks() {
  const books = [
    { id: 1, title: "Harry Potter", author: "J.K. ", available: true },
    { id: 2, title: "The Hobbit", author: "J.R.R", available: false },
    { id: 3, title: "1984", author: "George ", available: true },
  ];

  return (
    <div className="page-container">
      <h2>📚 Books</h2>
      <table className="data-table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Available</th>
          </tr>
        </thead>
        <tbody>
          {books.map(book => (
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.available ? "✅" : "❌"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
