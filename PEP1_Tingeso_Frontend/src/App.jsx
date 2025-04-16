import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "../src/components/Home"
import Navbar from "../src/components/Navbar"
import AddClient from './components/AddClient'
import LoginClient from './components/LoginClient'
import AllBookings from './components/AllBookings'
import './App.css'
import AddBooking from './components/AddBooking'
import BookingDetails from './components/BookingDetail'

function App() {

  return (
    <Router>
      <div className="container"></div>
      <Navbar></Navbar>
        <Routes>
          <Route path="/home" element={<Home/>}/>
          <Route path="/addClient" element={<AddClient/>}/>
          <Route path="/loginClient" element={<LoginClient/>}/>
          <Route path="/allBookings" element={<AllBookings/>}/>
          <Route path="/addBooking" element={<AddBooking/>} />
          <Route path="/setPriceDuration/:id" element={<BookingDetails/>} />
        </Routes>
    </Router>
  )
}

export default App
