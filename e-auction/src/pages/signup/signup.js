import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './signup.css';

function SignUp() {
  const navigate = useNavigate();
  const [userType, setUserType] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [formData, setFormData] = useState({
    userName: '',
    firstName: '',
    lastName: '',
    password: '',
    shippingAddress: {
      streetName: '',
      streetNumber: '',
      city: '',
      country: '',
      postalCode: ''
    }
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    try{
      const response = await axios.post(`https://authorizationservice-fm4o.onrender.com/signup/${userType}`, formData);
      const responseData = response.data;
      const status = responseData.status;
      if(status == 'SUCCESS'){
        console.log('User signed up successfully!');

      }
      else{
        setFormData({
          userName: '',
          firstName: '',
          lastName: '',
          password: '',
          shippingAddress: {
            streetName: '',
            streetNumber: '',
            city: '',
            country: '',
            postalCode: ''
          }
        });
        console.log('Error signing up:', responseData.message);
        setErrorMessage(responseData.message);
      }
    } catch(error){
      console.error('Error:', error);
      setErrorMessage('An error occurred while signing up. Please try again later.');
    }  };

  return (
    <div className="signup-container">
      <Header />
      <div className="user-type-selection">
        <label>
          <input
            type="radio"
            name="userType"
            value="user"
            onChange={(e) => setUserType(e.target.value)}
            checked={userType === 'user'}
          />
          Sign Up as USER
        </label>
        <label>
          <input
            type="radio"
            name="userType"
            value="seller"
            onChange={(e) => setUserType(e.target.value)}
            checked={userType === 'seller'}
          />
          Sign Up as SELLER
        </label>
      </div>
      {userType && (
        <div className="signup-form-container">
          <h2 className="signup-title">Sign Up Here!</h2>
          <form className="signup-form" onSubmit={handleSubmit}>
            <div className="form-column">
              <input type="text" id="firstName" name="firstName" placeholder="First Name" required onChange={handleInputChange}/>
              <input type="text" id="lastName" name="lastName" placeholder="Last Name" required onChange={handleInputChange}/>
              {userType === 'user' && (
                <>
                  <input type="text" id="streetNumber" name="streetNumber" placeholder="Street Number" required onChange={handleInputChange}/>
                  <input type="text" id="streetName" name="streetName" placeholder="Street Name" required onChange={handleInputChange}/>
                  <input type="text" id="city" name="city" placeholder="City" required onChange={handleInputChange}/>
                  <input type="text" id="country" name="country" placeholder="Country" required onChange={handleInputChange}/>
                  <input type="text" id="postalCode" name="postalCode" placeholder="Postal Code" required onChange={handleInputChange}/>
                </>
              )}
            </div>
            <div className="form-column">
              <input type="text" id="username" name="userName" placeholder="User Name" required onChange={handleInputChange}/>
              <input type="password" id="password" name="password" placeholder="Password" required onChange={handleInputChange}/>
            </div>
            <div className="form-submit">
              <button type="submit" className="submit-btn">Submit</button>
              {errorMessage && <div className="error-message">{errorMessage}</div>}
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

export default SignUp;
