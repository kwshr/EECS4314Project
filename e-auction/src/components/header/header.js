import React from 'react';
import logo from '../assets/KJYY.png';
import { Link } from 'react-router-dom';
import './header.css';

function Header() {
  return (
    <header className="header">
      <Link to="/home" className="logo-link">
      <img src={logo} alt="HammerStrike Logo" className="logo" />
      <h1 className="company-name">HammerStrike</h1>
      </Link>
    </header>
  );
};

export default Header;
