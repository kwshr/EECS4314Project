import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './resetpassword.css';

function ResetPassword() {
  const navigate = useNavigate();
  const location = useLocation();
  const userName = location.state.userName;
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const isPasswordMatch = newPassword && confirmPassword && newPassword === confirmPassword;

  const handleChangePassword = async(event) => {
    event.preventDefault();
    const formData ={
      userName: userName,
      password: newPassword
    }
    try{
    if (isPasswordMatch) {
      const response = await axios.put(`https://authorizationservice-fm4o.onrender.com/passwordReset`,formData);
      const status = response.data.status;
      if(status == 'OK'){
        alert('Your password has been changed successfully!');
        navigate('/signin');
      }
      else{
        alert('Error while changing password: '+ response.data.message);
      }
    } else {
      setNewPassword('');
      setConfirmPassword('');
      alert('Error while changin password:');
    }
  }catch(error){
    setNewPassword('');
    setConfirmPassword('');
    alert('Passwords reset failed');
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
