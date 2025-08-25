import React, { use } from "react";
import { IoMdAdd, IoMdSearch, IoMdBook } from "react-icons/io";
import { TbReportSearch } from "react-icons/tb";
import { LuBookLock } from "react-icons/lu";
import { Link } from "react-router-dom";
import { CiLogout } from "react-icons/ci"
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { useDispatch } from "react-redux";
import { update } from "../slices/userIdSlice";

export default function DashBoardPage() {
  

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const logout = () => {    

    dispatch(update(0));
    toast.success("Logged out successfully");
    navigate("/");
  }

  return (
    <div className=" min-h-screen bg-white">

        <button onClick={logout} className="flex items-center justify-center gap-1  px-4 py-2 border border-gray-300 rounded-lg bg-black text-white font-medium hover:bg-gray-900 transition ml-[1420px]">
            <div className=" flex items-center gap-2"><CiLogout className=" text-white" />Logout</div>
        </button>

        <div className=" flex items-center justify-center mt-[125px] ">
      <div className="w-full max-w-md p-8 space-y-6 rounded-2xl shadow-2xl border border-gray-200">
        <h2 className="text-2xl font-bold text-center text-black">
          Dashboard
        </h2>
        
        <div className="flex flex-col space-y-4">
            <Link to={"/addBook"}>
          <button className=" flex items-center justify-center gap-1 w-full px-4 py-2 bg-black text-white rounded-lg hover:bg-gray-800 transition duration-200">
            <IoMdAdd /><div>Add Book</div>
          </button>
          </Link>

          <Link to={"/searchBook"}>
          <button className="flex items-center justify-center gap-1 w-full px-4 py-2 bg-black text-white rounded-lg hover:bg-gray-800 transition duration-200">
            <IoMdSearch />Search Book
          </button>
          </Link>
          <Link to={"/viewBook"}>
          <button className="flex items-center  justify-center gap-1 w-full px-4 py-2 border border-gray-300 rounded-lg bg-white text-black font-medium hover:bg-gray-100 transition">
            <IoMdBook />View Book
            </button>
          </Link>
          <Link to={"/borrowBook"}>
          <button className="flex items-center justify-center gap-1 w-full px-4 py-2 border border-gray-300 rounded-lg bg-white text-black font-medium hover:bg-gray-100 transition">
            <LuBookLock/>Borrow book
            </button>
          </Link>
          <Link to={"/viewReport"}>
          <button className="flex items-center justify-center gap-1 w-full px-4 py-2 border border-gray-300 rounded-lg bg-white text-black font-medium hover:bg-gray-100 transition">
            <TbReportSearch /><div>View Report</div>
            </button>
          </Link>
        </div>
      </div>
    </div>
    </div>
  );
}
