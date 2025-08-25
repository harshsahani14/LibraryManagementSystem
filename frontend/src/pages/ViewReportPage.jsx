import React, { use, useEffect, useState } from "react";
import toast from "react-hot-toast";
import { TbReportSearch } from "react-icons/tb";
import { useNavigate } from "react-router-dom";

export default function ViewReportPage() {

  const [report, setReport] = useState([]);
  const [books, setBooks] = useState([]);
  const navigate = useNavigate();

 
  useEffect(() => {

    const fetchData = async () => {

        toast.loading("Loading report...")
        try {
            const response = await fetch(`http://localhost:8080/api/viewReport`, {
                method: "GET",
                headers: { "Content-Type": "application/json" },
            });

            if (!response.ok) {
                toast.dismiss();
                toast.error("Error while fetching books"+ response.body);
                return;
            }
            
            const data = await response.json();

            toast.dismiss();
            console.log(data)
            setBooks(data.books);
            setReport(data.report);
            
            toast.success("Books fetched successfully");
        
        } catch (err) {
            toast.dismiss();
            toast.error("Error while searching books" + err.message);
        }
        }

        fetchData()
  },[])

  return (
    <div className="w-[800px] mx-auto mt-10 p-6 bg-white border shadow-2xl rounded-2xl max-h-screen ">
      <h2 className="text-xl font-bold mb-4 flex gap-1 items-center justify-center"><TbReportSearch />View Report</h2>


        <div className=" flex gap-20">
            
        {

            report.length > 0 && (
            <div className="mt-6 overflow-x-auto">
                <p className=" mb-3 font-bold "> Books Due for Return Today</p>
                <table className="w-full border border-gray-300 rounded-lg">
                    <thead className="">
                    <tr>
                        <th className="px-4 py-2 border">Name</th>
                        <th className="px-4 py-2 border">Genre</th>
                        <th className="px-4 py-2 border">Edition</th>
                        <th className="px-4 py-2 border">Author</th>
                        <th className="px-4 py-2 border">Borrowed By</th>
                    </tr>
                    </thead>
                    <tbody>
                    {report.map((book, index) => (
                        <tr key={index} className="text-center">
                        <td className="px-4 py-2 border">{book.bookName}</td>
                        <td className="px-4 py-2 border">{book.bookGenre}</td>
                        <td className="px-4 py-2 border">{book.bookEdition}</td>
                        <td className="px-4 py-2 border">{book.bookAuthor}</td>
                        <td className="px-4 py-2 border">{book.userName}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                </div>  
            )

        }

           {

            books.length > 0 && (
            <div className="mt-6 overflow-x-auto">

                <p className=" mb-3 font-bold "> Genre-wise Book Count</p>
                <table className="w-full border border-gray-300 rounded-lg">
                    <thead className="">
                    <tr>
                        <th className="px-4 py-2 border">Genre</th>
                        <th className="px-4 py-2 border">Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.map((book, index) => (
                        <tr key={index} className="text-center">
                        <td className="px-4 py-2 border">{book.genre}</td>
                        <td className="px-4 py-2 border">{book.quantity}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                </div>  
            )

        }

        </div>

        {books.length === 0 && (
            <div className="mt-4 text-center font-bold">
                <p>No books found.</p>
            </div>
        )}

        
        <button
            type="button"
            className="border px-4 py-2 rounded-md mt-6 ml-[345px]  bg-black text-white font-medium text-sm hover:bg-gray-900"
            onClick={() => navigate("/dashboard")}
          >
            Go back
          </button>
    </div>
  );
}
