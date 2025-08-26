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
            element={<Navigate to={userId > 0 ? "/dashboard" : "/login"} />}
          />

          {/* Public Routes */}
          <Route
            path="/login"
            element={userId > 0 ? <Navigate to="/dashboard" /> : <LoginPage />}
          />
          <Route
            path="/register"
            element={userId > 0 ? <Navigate to="/dashboard" /> : <RegisterPage />}
          />

          {/* Private Routes */}
          <Route
            path="/dashboard"
            element={userId === 0 ? <Navigate to="/login" /> : <DashBoardPage />}
          />
          <Route
            path="/addBook"
            element={userId === 0 ? <Navigate to="/login" /> : <AddBookPage />}
          />
          <Route
            path="/searchBook"
            element={userId === 0 ? <Navigate to="/login" /> : <SearchBookPage />}
          />
          <Route
            path="/viewBook"
            element={userId === 0 ? <Navigate to="/login" /> : <ViewBookPage />}
          />
          <Route
            path="/borrowBook"
            element={userId === 0 ? <Navigate to="/login" /> : <BorrowBookPage />}
          />
          <Route
            path="/viewReport"
            element={userId === 0 ? <Navigate to="/login" /> : <ViewReportPage />}
          />
      </Routes>
      <Toaster />
    </div>
  );
};

export default App;
