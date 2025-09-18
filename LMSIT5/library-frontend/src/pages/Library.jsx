export default function Library() {
  const info = {
    name: "Central Library",
    address: "123 Main Street",
    hours: "Mon-Fri 8am-6pm",
  };

  return (
    <div className="page-container">
      <h2>🏛 Library Info</h2>
      <p><strong>Name:</strong> {info.name}</p>
      <p><strong>Address:</strong> {info.address}</p>
      <p><strong>Opening Hours:</strong> {info.hours}</p>
    </div>
  );
}
