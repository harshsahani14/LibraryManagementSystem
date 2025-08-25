import React, { useState } from "react";
import { IoMdBook } from "react-icons/io";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export default function ViewBook() {
  const [formData, setFormData] = useState({
    name: "",
    genre: "",
    edition: "",
    author: "",
  });
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    toast.loading("Searching...");
    try {
      const response = await fetch(`http://localhost:8080/api/viewBook?name=${formData.name}&genre=${formData.genre}&edition=${formData.edition}&author=${formData.author}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        toast.dismiss()
        toast.error("Error while fetching books");
        return
      }

      const data = await response.json();

      if(data.books.length === 0){
        setBooks([]);
        toast.dismiss();
        toast.error("No books found");
        return;
      }
      toast.dismiss();
      toast.success("Books fetched successfully");
      setBooks(data.books ); // assuming backend sends a list of books
    } catch (err) {
      toast.dismiss();
      toast.error("Error while fetching books");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto mt-10 p-6 bg-white rounded-2xl shadow-2xl border">
      <h2 className="text-xl font-bold mb-4 flex gap-2 items-center"><IoMdBook/> <p>View Book</p></h2>

      {/* Filter Form */}
      <form onSubmit={handleSearch} className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium">Name</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Enter book name"
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">Genre</label>
          <input
            type="text"
            name="genre"
            value={formData.genre}
            onChange={handleChange}
            placeholder="Enter genre"
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">Edition</label>
          <input
            type="text"
            name="edition"
            value={formData.edition}
            onChange={handleChange}
            placeholder="Enter edition"
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">Author</label>
          <input
            type="text"
            name="author"
            value={formData.author}
            onChange={handleChange}
            placeholder="Enter author name"
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>
        <div className="md:col-span-2 flex justify-end mt-2">
          <button
            type="submit"
            className="px-6 py-2 bg-black text-white rounded-lg hover:bg-gray-800 transition"
          >
            View Books
          </button>
        </div>
      </form>

      

      {/* Results Table */}
      {books.length > 0 && (
        <div className="mt-6 overflow-x-auto">
          <table className="w-full border border-gray-300 rounded-lg">
            <thead className="">
              <tr>
                <th className="px-4 py-2 border">Borrowed by</th>
                <th className="px-4 py-2 border">Return Date</th>
              </tr>
            </thead>
            <tbody>
              {books.map((book, index) => (
                <tr key={index} className="text-center">
                  <td className="px-4 py-2 border">{book.borrowedBy}</td>
                  <td className="px-4 py-2 border">{book.returnDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {!loading && books.length === 0 && (
        <p className="mt-8 text-sm font-bold text-center ">No books found.</p>
      )}

      <div className="  flex gap-2 justify-end mt-4 ">
        <button
            type="button"
            className="border px-4 py-2 rounded-md font-medium text-sm hover:bg-gray-100"
            onClick={() => navigate("/dashboard")}
          >
            Go back
          </button>
          <button
            type="button"
            className=" border px-4 py-2 bg-black rounded-md font-medium text-sm hover:bg-gray-900 text-white"
            onClick={() => setBooks([])}
          >
            Clear results
          </button>
          </div>
    </div>
  );
}
