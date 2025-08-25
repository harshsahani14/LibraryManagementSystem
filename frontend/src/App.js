import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DashBoardPage from "./pages/DashBoardPage";
import AddBookPage from "./pages/AddBookPage";
import SearchBookPage from "./pages/SearchBookPage";
import ViewBookPage from "./pages/ViewBook";
import BorrowBookPage from "./pages/BorrowBookPage";
import ViewReportPage from "./pages/ViewReportPage";
import NotFoundPage from "./pages/NotFoundPage";
import { Toaster } from "react-hot-toast";
import { useSelector } from "react-redux";

const App = () => {
  const userId = useSelector((state) => state.userId.value);

  return (
    <div className="">
      <Routes>
        <Route
          path="/"
          element={userId > 0 ? <Navigate to="/dashboard" /> : <LoginPage />}
        />
        <Route
          path="/register"
          element={userId > 0 ? <Navigate to="/dashboard" /> : <RegisterPage />}
        />
        <Route
          path="/dashboard"
          element={userId === 0 ? <Navigate to="/" /> : <DashBoardPage />}
        />
        <Route
          path="/addBook"
          element={userId === 0 ? <Navigate to="/" /> : <AddBookPage />}
        />
        <Route
          path="/searchBook"
          element={userId === 0 ? <Navigate to="/" /> : <SearchBookPage />}
        />
        <Route
          path="/viewBook"
          element={userId === 0 ? <Navigate to="/" /> : <ViewBookPage />}
        />
        <Route
          path="/borrowBook"
          element={userId === 0 ? <Navigate to="/" /> : <BorrowBookPage />}
        />
        <Route
          path="/viewReport"
          element={userId === 0 ? <Navigate to="/" /> : <ViewReportPage />}
        />
      </Routes>
      <Toaster />
    </div>
  );
};

export default App;
