import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './forgotpassword.css';

function ForgotPassword() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');

  const handleSendLink = (event) => {
    event.preventDefault();
    alert('If the email is registered, a password reset link will be sent. Following that link, you will be redirected to a /resetpassword page with a token.');
    setEmail('');
    navigate('/resetpassword');
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
          type="email"
          id="userEmail"
          name="userEmail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Enter your email"
          required
        />
        <button type="submit" className="send-link-btn">Send Link</button>
      </form>
      <button onClick={handleBackToSignIn} className="back-signin-btn">
        Back to Sign In
      </button>
    </div>
  );
}

export default ForgotPassword;
