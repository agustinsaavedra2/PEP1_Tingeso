import { useState } from 'react'
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "../src/components/Home"
import Navbar from "../src/components/Navbar"
import AddClient from './components/AddClient'
import './App.css'
import LoginClient from './components/LoginClient'

function App() {

  return (
    <Router>
      <div className="container"></div>
      <Navbar></Navbar>
        <Routes>
          <Route path="/home" element={<Home/>}/>
          <Route path="/addClient" element={<AddClient/>}/>
          <Route path="/loginClient" element={<LoginClient/>}/>
        </Routes>
    </Router>
  )
}

export default App
