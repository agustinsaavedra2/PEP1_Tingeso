import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "../src/components/Home"
import Navbar from "../src/components/Navbar"
import AddClient from './components/AddClient'
import LoginClient from './components/LoginClient'
import AllBookings from './components/AllBookings'
import AddBooking from './components/AddBooking'
import SetPriceDuration from './components/SetPriceDuration'
import DiscountNumPeople from './components/DiscountNumPeople'
import DiscountFreqClient from './components/DiscountFreqClient'
import './App.css'

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
          <Route path="/setPriceAndDuration/:id" element={<SetPriceDuration/>} />
          <Route path="/setDiscountPeopleNumber/:id" element={<DiscountNumPeople/>} />
          <Route path="/setDiscountFreqClient/:id" element={<DiscountFreqClient/>} />
        </Routes>
    </Router>
  )
}

export default App
