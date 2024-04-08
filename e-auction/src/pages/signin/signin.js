import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './signin.css';

function SignIn() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage,setErrorMessage] = useState('');

  const handleSignInUser = async (event) => {
    event.preventDefault();
    const formData = {
    userName: userName,
    password: password
  }
    try{
      const response = await axios.post(`https://authorizationservice-fm4o.onrender.com/signIn/user`,formData);
      const status = response.data.status;
      if(status === 'OK'){
        navigate('/itemsearch',{ state: { userName: userName } });
      }
      else{
        setUserName('');
        setPassword('');
        setErrorMessage('An error occured while logging in. Please try again');
      }
    } catch(error){
      setErrorMessage('An error occured while logging in. Please try again');
    }
  };

  const handleSignInSeller = async (event) => {
    event.preventDefault();
    const formData = {
    userName: userName,
    password: password
  }
    try{
      const response = await axios.post(`https://authorizationservice-fm4o.onrender.com/signIn/seller`,formData);
      const status = response.data.status;
      if(status === 'OK'){  
        navigate('/sellerhome',{ state: { userName: userName } });
      }
      else{
        setUserName('');
        setPassword('');
        setErrorMessage('An error occured while logging in: ',response.data.message);
      }
    } catch(error){
      setErrorMessage('An error occured while logging in: ',error);
    }
  };

  const handleForgotPassword = () => {
    navigate('/forgotpassword');
  };

  return (
    <div className="signin-container">
      <Header />
      <div className="signin-form-container">
        <h2 className="signin-title">Login</h2>
        <form className="signin-form">
          <input type="text" id="username" name="username" placeholder="User Name" required onChange={(e) => setUserName(e.target.value)}/>
          <input type="password" id="password" name="password" placeholder="Password" required onChange={(e) => setPassword(e.target.value)}/>
          <div className="signin-buttons">
            <button onClick={handleSignInUser} className="signin-btn user">Login as USER</button>
            <button onClick={handleSignInSeller} className="signin-btn seller">Login as SELLER</button>
          </div>
          {errorMessage && <div className = "error-message">{errorMessage}</div>}
        </form>
        <button onClick={handleForgotPassword} className="forgot-password-btn">
          Forgot Password?
        </button>
      </div>
    </div>
  );
}

export default SignIn;
