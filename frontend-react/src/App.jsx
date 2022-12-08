import { Navbar } from "./components/Navbar";

import { Outlet } from "react-router-dom";

import './App.css'
import { Header } from "./components/Header";

function App() {
  return (
    <div className="App">
      <Header />
      <div className="container">
        <Outlet />
      </div>
    </div>
  )
}

export default App
