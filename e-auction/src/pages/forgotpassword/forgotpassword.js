import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './forgotpassword.css';

function ForgotPassword() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');

  const handleSendLink = (event) => {
    event.preventDefault();
    navigate('/resetpassword', { state: { userName: userName } });
  };

  const handleBackToSignIn = () => {
    navigate('/signin');
  };

  return (
    <div className="forgotpassword-container">
      <Header />
      <h2 className="forgotpassword-title">Forgot Password?</h2>
      <form className="forgotpassword-form" onSubmit={handleSendLink}>
        <input
          type="text"
          id="userName"
          name="userName"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          placeholder="Enter your user name"
          required
        />
        <button type="submit" className="send-link-btn">Reset Password</button>
      </form>
      <button onClick={handleBackToSignIn} className="back-signin-btn">
        Back to Sign In
      </button>
    </div>
  );
}

export default ForgotPassword;
