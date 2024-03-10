import React from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './signin.css';

function SignIn() {
  const navigate = useNavigate();

  const handleSignInUser = (event) => {
    event.preventDefault();
    navigate('/itemsearch');
  };

  const handleSignInSeller = (event) => {
    event.preventDefault();
    navigate('/sellerhome');
  };

  const handleForgotPassword = () => {
    console.log('Redirecting to forgot password page');
    navigate('/forgotpassword');
  };

  return (
    <div className="signin-container">
      <Header />
      <div className="signin-form-container">
        <h2 className="signin-title">Login</h2>
        <form className="signin-form">
          <input type="text" id="username" name="username" placeholder="User Name" required />
          <input type="password" id="password" name="password" placeholder="Password" required />
          <div className="signin-buttons">
            <button onClick={handleSignInUser} className="signin-btn user">Login as USER</button>
            <button onClick={handleSignInSeller} className="signin-btn seller">Login as SELLER</button>
          </div>
        </form>
        <button onClick={handleForgotPassword} className="forgot-password-btn">
          Forgot Password?
        </button>
      </div>
    </div>
  );
}

export default SignIn;
