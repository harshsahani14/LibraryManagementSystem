import { useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export default function AddBookPage() {
  
    const [formData, setFormData] = useState({
    name: "",
    genre:"",
    edition:"",
    author: "",
    isbn_no: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async(e) => {
    e.preventDefault();
    toast.loading("Adding book...");
    
    try {
        
        const response = await fetch("http://localhost:8080/api/addBook", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(formData),
        });

        if (!response.ok) {
          toast.dismiss();
          toast.error("Error while adding book");
          return;
        } 
        
        toast.dismiss();
        toast.success("Book added successfully!");
        navigate("/dashboard");

    } catch (error) {
        toast.dismiss();
        toast.error("Error while adding book");
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white mt-[100px] shadow-2xl border border-gray-200 rounded-lg p-6">
      <h2 className="text-lg font-semibold mb-1">Add New Book </h2>
      <p className="text-sm text-gray-500 mb-4">Enter the details of the new book</p>

      <form onSubmit={handleSubmit} className="space-y-3">
        <div>
          <label className="block text-sm font-medium">Title *</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium">Genre *</label>
          <input
            type="text"
            name="genre"
            value={formData.genre}
            onChange={handleChange}
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium">Edition *</label>
          <input
            type="text"
            name="edition"
            value={formData.edition}
            onChange={handleChange}
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>

        <div>
          <label className="block text-sm font-medium">Author *</label>
          <input
            type="text"
            name="author"
            value={formData.author}
            onChange={handleChange}
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          />
        </div>
        <div>
          <label className="block text-sm font-medium">ISBN * (6 digits)</label>
          <input
            type="number"
            name="isbn_no"
            value={formData.isbn_no}
            onChange={handleChange}
            className="w-full border rounded-md px-3 py-2 mt-1 text-sm focus:outline-none focus:ring-2 focus:ring-black"
            maxLength={6}
            minLength={6}
          />
        </div>

    
        <div className="flex justify-between mt-4">
          <button
            type="submit"
            className="bg-black text-white font-medium px-4 py-2 rounded-md text-sm hover:bg-gray-800"
          >
            Add Book
          </button>
          <button
            type="button"
            className="border px-4 py-2 rounded-md font-medium text-sm hover:bg-gray-100"
            onClick={() => navigate("/dashboard")}
          >
            Go back
          </button>
        </div>
      </form>
    </div>
  );
}
