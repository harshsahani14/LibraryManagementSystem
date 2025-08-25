import React from 'react'
import { useState } from "react";
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { update } from '../slices/userIdSlice';

const LoginPage = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
    const dispatch = useDispatch();

  const handleSubmit = async(e) => {
    e.preventDefault();

    toast.loading("Loading");
    
    try {
        const response = await fetch("http://localhost:8080/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            toast.dismiss()
            toast.error("Invalid username or password");
            return;
        }

        const data = await response.json();


        dispatch(update(data.user_id));
        toast.dismiss()
        toast.success("Login successful!");
        navigate("/dashboard");

    } catch (error) {
        toast.dismiss();
        toast.error("Server error while logging in");
    }

  };
  return (
      <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-8 space-y-6 bg-white shadow-lg rounded-2xl">
        <h2 className="text-2xl font-bold text-center text-gray-800">
          Welcome back
        </h2>
        <p className="text-sm text-center text-gray-500">
          Login to your Library account
        </p>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="block mb-1 text-sm font-medium text-gray-700">
              Username
            </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="Enter your username"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="Enter your password"
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 font-semibold text-white bg-black rounded-lg hover:bg-gray-800 transition"
          >
            Login
          </button>
        </form>
        <p className="text-sm text-center text-gray-500">
          Don't have an account?{" "}
          <span
            href="/register"
            className="font-medium text-indigo-600 hover:text-indigo-400 hover:cursor-pointer "
            onClick={() => navigate("/register")}
          >
            Register
          </span>
        </p>
      </div>
    </div>
  
  );
  
}

export default LoginPage