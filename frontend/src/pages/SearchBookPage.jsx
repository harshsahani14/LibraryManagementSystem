import React, { use, useState } from "react";
import toast from "react-hot-toast";
import { IoSearch } from "react-icons/io5";
import { useNavigate } from "react-router-dom";

export default function SearchBook() {
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(false);
  const [books, setBooks] = useState([]);
  const navigate = useNavigate();


  const handleSearch = async (e) => {
    e.preventDefault();
    
    toast.loading("Searching books...");

    try {
      const response = await fetch(`http://localhost:8080/api/searchBook?name=${searchQuery}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        toast.dismiss();
        toast.error("Error while searching books");
        return;
      }
      
      const data = await response.json();


      toast.dismiss();
      if(data.books.length === 0){
        toast.error("No books found");
        setBooks([]);
        return;
      }
      toast.success("Books searched successfully");

      setBooks(data.books );

      
    } catch (err) {
        toast.dismiss();
        toast.error("Error while searching books");
    }
  };

  return (
    <div className="w-[700px] mx-auto mt-10 p-6 bg-white border shadow-2xl rounded-2xl max-h-screen ">
      <h2 className="text-xl font-bold mb-4 flex gap-1 items-center ml-[120px]">< IoSearch/> Search Book</h2>

      {/* Search Form */}
      <form onSubmit={handleSearch} className=" w-[400px] flex gap-2 mx-auto">
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Enter book name..."
          className="flex-1 border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-black"
          required
        />
        <button
          type="submit"
          className="px-4 py-2 bg-black text-white rounded-lg hover:bg-gray-800 transition"
        >
          Search
        </button>
      </form>

      
        {

            books.length > 0 && (
            <div className="mt-6 overflow-x-auto">
                <table className="w-full border border-gray-300 rounded-lg">
                    <thead className="">
                    <tr>
                        <th className="px-4 py-2 border">Name</th>
                        <th className="px-4 py-2 border">Genre</th>
                        <th className="px-4 py-2 border">Edition</th>
                        <th className="px-4 py-2 border">Author</th>
                        <th className="px-4 py-2 border">ISBN Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.map((book, index) => (
                        <tr key={index} className="text-center">
                        <td className="px-4 py-2 border">{book.name}</td>
                        <td className="px-4 py-2 border">{book.genre}</td>
                        <td className="px-4 py-2 border">{book.edition}</td>
                        <td className="px-4 py-2 border">{book.author}</td>
                        <td className="px-4 py-2 border">{book.isbn_no}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                </div>  
            )

        }

        {books.length === 0 && (
            <div className="mt-4 text-center font-bold">
                <p>No books found.</p>
            </div>
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
