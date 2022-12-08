import { Link } from "react-router-dom";

import "./Header.css";

export const Header = () => {
  return (
    <header className="header">
        <h1>
          PW2 - Final Project - Gustavo Bittencourt dos Santos
          <Link to={`/`} className="to-home"></Link>
        </h1>
        <h2>Web Service based on Google Calendar Api</h2>
        <button type="button" className="btn -header">
          <Link to={`/insert`}>Novo Evento</Link>
        </button>
    </header>
  )
}
