import { useEffect, useState } from "react";
import api from "../api"; // Axios instance

export default function AdminBooks() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    title: "",
    author: "",
    subject: "",
    genre: "",
    totalCopies: "",
    availableCopies: "",
    location: "",
    status: "AVAILABLE",
    imageUrl: "",
    pdfUrl: "",
    price: "",
    fineRate: ""
  });
  const [errors, setErrors] = useState({});
  const [uploading, setUploading] = useState({ image: false, pdf: false });
  const [previewImage, setPreviewImage] = useState("");

  // Fetch all books
  const fetchBooks = async () => {
    try {
      const res = await api.get("/books");
      setBooks(res.data);
    } catch (err) {
      console.error("Failed to fetch books:", err.response?.data || err.message);
      alert("Failed to fetch books");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  // Validate input fields
  const validate = () => {
    const newErrors = {};
    if (!form.title.trim()) newErrors.title = "Title is required";
    if (!form.author.trim()) newErrors.author = "Author is required";
    if (!form.genre) newErrors.genre = "Genre is required";
    if (!form.totalCopies || isNaN(form.totalCopies) || form.totalCopies <= 0)
      newErrors.totalCopies = "Total copies must be a positive number";
    if (!form.availableCopies || isNaN(form.availableCopies) || form.availableCopies < 0)
      newErrors.availableCopies = "Available copies must be 0 or more";
    if (form.price && (isNaN(form.price) || form.price < 0))
      newErrors.price = "Price must be a positive number";
    if (form.fineRate && (isNaN(form.fineRate) || form.fineRate < 0))
      newErrors.fineRate = "Fine rate must be a positive number";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form change
  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  // Handle image upload
  const handleImageUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    // Show preview
    const reader = new FileReader();
    reader.onload = (event) => setPreviewImage(event.target.result);
    reader.readAsDataURL(file);

    setUploading({ ...uploading, image: true });

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await api.post('/files/upload/image', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });

      setForm({ ...form, imageUrl: response.data.imageUrl });
      alert('Book cover uploaded successfully!');
    } catch (error) {
      console.error('Image upload failed:', error);
      alert('Failed to upload image: ' + (error.response?.data?.error || error.message));
      setPreviewImage("");
    } finally {
      setUploading({ ...uploading, image: false });
    }
  };

  // Handle PDF upload
  const handlePdfUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setUploading({ ...uploading, pdf: true });

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await api.post('/files/upload/pdf', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });

      setForm({ ...form, pdfUrl: response.data.pdfUrl });
      alert('Book PDF uploaded successfully!');
    } catch (error) {
      console.error('PDF upload failed:', error);
      alert('Failed to upload PDF: ' + (error.response?.data?.error || error.message));
    } finally {
      setUploading({ ...uploading, pdf: false });
    }
  };

  // Add book
  const handleAdd = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      await api.post("/books/add", {
        ...form,
        totalCopies: parseInt(form.totalCopies),
        availableCopies: parseInt(form.availableCopies),
        price: parseFloat(form.price) || 0,
        fineRate: parseFloat(form.fineRate) || 5.0 // Default to R5 per day if not specified
      });
      alert("Book added successfully!");
      setForm({
        title: "", author: "", subject: "", genre: "", totalCopies: "",
        availableCopies: "", location: "", status: "AVAILABLE",
        imageUrl: "", pdfUrl: "", price: "", fineRate: ""
      });
      setPreviewImage("");
      fetchBooks();
    } catch (err) {
      console.error("Failed to add book:", err.response?.data || err.message);
      alert("Failed to add book: " + (err.response?.data?.error || err.message));
    }
  };

  // Delete a book
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this book?")) return;
    try {
      await api.delete(`/books/delete/${id}`);
      fetchBooks();
    } catch (err) {
      console.error("Failed to delete book:", err.response?.data || err.message);
      alert("Failed to delete book: " + (err.response?.data?.error || err.message));
    }
  };

  if (loading) return <p>Loading books...</p>;

  return (
  <div style={{ padding: "30px", fontFamily: "Arial, sans-serif", color: "#082155" }}>
    <h2 style={{ marginBottom: "20px" }}>📚 Admin Books</h2>

    {/* Add Book Form */}
    <form
      onSubmit={handleAdd}
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
        gap: "15px",
        marginBottom: "30px",
        backgroundColor: "#f0f4ff",
        padding: "20px",
        borderRadius: "12px",
        boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
      }}
    >
      {[
        { name: "title", placeholder: "Title" },
        { name: "author", placeholder: "Author" },
        { name: "subject", placeholder: "Subject" },
        { name: "totalCopies", placeholder: "Total Copies", type: "number" },
        { name: "availableCopies", placeholder: "Available Copies", type: "number" },
        { name: "location", placeholder: "Location" },
        { name: "price", placeholder: "Price (R)", type: "number", step: "0.01" },
        { name: "fineRate", placeholder: "Daily Fine Rate (R)", type: "number", step: "0.01" },
      ].map((field) => (
        <div key={field.name} style={{ display: "flex", flexDirection: "column" }}>
          <input
            type={field.type || "text"}
            name={field.name}
            placeholder={field.placeholder}
            value={form[field.name]}
            onChange={handleChange}
            style={{
              padding: "10px",
              borderRadius: "8px",
              border: "1px solid #082155",
              outline: "none",
            }}
          />
          {errors[field.name] && <span style={{ color: "#f44336" }}>{errors[field.name]}</span>}
        </div>
      ))}

      <select
        name="genre"
        value={form.genre}
        onChange={handleChange}
        style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
      >
        <option value="">Select Genre</option>
        <option value="Fiction">Fiction</option>
        <option value="Non-Fiction">Non-Fiction</option>
        <option value="Science">Science</option>
        <option value="Biography">Biography</option>
      </select>
      {errors.genre && <span style={{ color: "#f44336" }}>{errors.genre}</span>}

      <select
        name="status"
        value={form.status}
        onChange={handleChange}
        style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
      >
        <option value="AVAILABLE">AVAILABLE</option>
        <option value="ON_LOAN">ON_LOAN</option>
        <option value="RESERVED">RESERVED</option>
        <option value="DAMAGED">DAMAGED</option>
      </select>

      <div style={{ display: "flex", flexDirection: "column" }}>
        <label style={{ marginBottom: "5px", fontWeight: "bold" }}>Book Cover</label>
        <input
          type="file"
          accept="image/*"
          onChange={handleImageUpload}
          style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
        />
        {uploading.image && <span style={{ color: "#4caf50" }}>Uploading image...</span>}
        {previewImage && (
          <div style={{ marginTop: "10px" }}>
            <img
              src={previewImage}
              alt="Preview"
              style={{ maxWidth: "100%", borderRadius: "8px", border: "1px solid #082155" }}
            />
          </div>
        )}
      </div>

      <div style={{ display: "flex", flexDirection: "column" }}>
        <label style={{ marginBottom: "5px", fontWeight: "bold" }}>Book PDF</label>
        <input
          type="file"
          accept="application/pdf"
          onChange={handlePdfUpload}
          style={{ padding: "10px", borderRadius: "8px", border: "1px solid #082155" }}
        />
        {uploading.pdf && <span style={{ color: "#4caf50" }}>Uploading PDF...</span>}
      </div>

      <button
        type="submit"
        style={{
          gridColumn: "1 / -1",
          padding: "12px",
          borderRadius: "8px",
          backgroundColor: "#082155",
          color: "#fff",
          fontWeight: "bold",
          cursor: "pointer",
          border: "none",
          transition: "background 0.3s",
        }}
        onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#0b2a7a")}
        onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#082155")}
      >
        ➕ Add Book
      </button>
    </form>

    {/* Books Table */}
    {books.length === 0 ? (
      <p>No books found.</p>
    ) : (
      <div style={{ overflowX: "auto" }}>
        <table
          style={{
            borderCollapse: "collapse",
            width: "100%",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            borderRadius: "12px",
            overflow: "hidden",
          }}
        >
          <thead style={{ backgroundColor: "#082155", color: "#fff" }}>
            <tr>
              {["ID", "Title", "Author", "Genre", "Subject", "Total", "Available", "Location", "Status", "Actions"].map((h) => (
                <th key={h} style={{ padding: "12px", textAlign: "left" }}>{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {books.map((b) => (
              <tr key={b.bookId} style={{ borderBottom: "1px solid #ddd" }}>
                <td style={{ padding: "10px" }}>{b.bookId}</td>
                <td style={{ padding: "10px" }}>{b.title}</td>
                <td style={{ padding: "10px" }}>{b.author}</td>
                <td style={{ padding: "10px" }}>{b.genre}</td>
                <td style={{ padding: "10px" }}>{b.subject}</td>
                <td style={{ padding: "10px" }}>{b.totalCopies}</td>
                <td style={{ padding: "10px" }}>{b.availableCopies}</td>
                <td style={{ padding: "10px" }}>{b.location}</td>
                <td style={{ padding: "10px" }}>{b.status}</td>
                <td style={{ padding: "10px" }}>
                  <button
                    onClick={() => handleDelete(b.bookId)}
                    style={{
                      backgroundColor: "#f44336",
                      color: "#fff",
                      border: "none",
                      padding: "8px 12px",
                      borderRadius: "6px",
                      cursor: "pointer",
                      transition: "background 0.3s",
                    }}
                    onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#d32f2f")}
                    onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#f44336")}
                  >
                    ❌ Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    )}
  </div>
);
}
