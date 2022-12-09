import { Link } from "react-router-dom";

import "./Header.css";

export const Header = () => {
  return (
    <header className="header">
      <Link to={`/`} className="to-home">
        <h1>PW2</h1>
      </Link>

      <h2>Web Service based on Google Calendar Api</h2>
      <h3>Gustavo Bittencourt dos Santos</h3>
      <button type="button" className="btn -header">
        <Link to={`/insert`}>Novo Evento</Link>
      </button>
    </header>
  )
}
