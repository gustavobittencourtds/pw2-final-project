import { Link } from "react-router-dom";

import "./Navbar.css";

export const Navbar = () => {
  return (
    <nav className='navbar'>
      <h2>
        <Link to={`/`}>Home</Link>
      </h2>

      <ul>
        <li>
          <Link to={`/`}>Home</Link>
        </li>
        <li>
          <Link to={`/insert`} className="new-event">NewEvent</Link>
        </li>
      </ul>
    </nav>
  )
}
