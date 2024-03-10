import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './resetpassword.css';

function ResetPassword() {
  const navigate = useNavigate();
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const isPasswordMatch = newPassword && confirmPassword && newPassword === confirmPassword;

  const handleChangePassword = (event) => {
    event.preventDefault();
    if (isPasswordMatch) {
      alert('Your password has been changed successfully!');
      setNewPassword('');
      setConfirmPassword('');
      navigate('/signin');
    } else {
      alert('Passwords do not match. Please try again.');
    }
  };

  return (
    <div className="resetpassword-container">
      <Header />
      <form className="resetpassword-form" onSubmit={handleChangePassword}>
        <h2>Change Password</h2>
        <input
          type="password"
          id="newPassword"
          name="newPassword"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
          placeholder="New Password"
          required
        />
        <input
          type="password"
          id="confirmPassword"
          name="confirmPassword"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          placeholder="Confirm Password"
          required
        />
        <button type="submit" className="change-password-btn" disabled={!isPasswordMatch}>
          Change Password
        </button>
      </form>
    </div>
  );
}

export default ResetPassword;
