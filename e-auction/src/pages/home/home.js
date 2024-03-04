import React from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './home.css';

function Home() {
  const navigate = useNavigate();

  const navigateToSignUp = () => {
    navigate('/signup');
  };

  const navigateToSignIn = () => {
    navigate('/signin');
  };

  return (
    <div className="home-container">
      <Header />
      <main className="home-main">
        <section className="welcome-section">
          <h2>WELCOME</h2>
          <p>Join us and discover a world of auctions!</p>
        </section>
        <section className="auth-section">
          <button onClick={navigateToSignIn} className="sign-in-btn">Sign-In</button>
          <button onClick={navigateToSignUp} className="sign-up-btn">Sign-Up</button>
        </section>
      </main>
    </div>
  );
};

export default Home;
