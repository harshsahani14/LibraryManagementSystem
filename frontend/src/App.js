import React from 'react'
import { Route,Routes } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import DashBoardPage from './pages/DashBoardPage'
import AddBookPage from './pages/AddBookPage'
import SearchBookPage from './pages/SearchBookPage'
import ViewBookPage from './pages/ViewBook'
import BorrowBookPage from './pages/BorrowBookPage'
import ViewReportPage from './pages/ViewReportPage'
import NotFoundPage from './pages/NotFoundPage'
import { Toaster } from 'react-hot-toast'
import {useSelector} from 'react-redux'

const App = () => {

  const userId = useSelector((state) => state.userId.value)

  return (
    <div className="">
      
      <Routes>

        {

            !userId && 
            <>
              <Route path="/" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
            </>
        }
        {

          userId && (
            <>
              <Route path="/dashboard" element={<DashBoardPage />} />
              <Route path="/addBook" element={<AddBookPage />} />
              <Route path="/searchBook" element={<SearchBookPage />} />
              <Route path="/viewBook" element={<ViewBookPage />} />
              <Route path="/borrowBook" element={<BorrowBookPage />} />
              <Route path="/viewReport" element={<ViewReportPage />} />
            </>
          )

        }

        <Route path='/*' element={<NotFoundPage />} />
      </Routes>
      <Toaster/>
    </div>
  )
}

export default App