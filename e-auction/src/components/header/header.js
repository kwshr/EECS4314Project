import React from 'react';
import logo from '../assets/KJYY.png';
import './header.css';

function Header() {
  return (
    <header className="header">
      <img src={logo} alt="HammerStrike Logo" className="logo" />
      <h1 className="company-name">HammerStrike</h1>
    </header>
  );
};

export default Header;
