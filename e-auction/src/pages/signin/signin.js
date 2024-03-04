import React from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './signin.css';

function SignUp() {
  const navigate = useNavigate();

  const handleSignIn = (event) => {
    event.preventDefault();
    // check the credentials and sign the user in if correct
    // redirecting to dummy route for now
    navigate('/itemsearch');
  };

  const handleForgotPassword = () => {
    // log on console for now, redirect to actual mechanism later
    console.log('Redirecting to forgot password page');
  };


  return (
    <div className="signin-container">
      <Header />
      <div className="signin-form-container">
        <h2 className="signin-title">Login</h2>
        <form className="signin-form" onSubmit={handleSignIn}>
          <input type="text" id="username" name="username" placeholder="User Name" required />
          <input type="password" id="password" name="password" placeholder="Password" required />
          <button type="submit" className="signin-btn">Submit</button>
        </form>
        <button onClick={handleForgotPassword} className="forgot-password-btn">
          Forgot password?
        </button>
      </div>
    </div>
  );
};

export default SignUp;
