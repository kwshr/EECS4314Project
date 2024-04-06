import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './signup.css';

function SignUp() {
  const navigate = useNavigate();
  const [userType, setUserType] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [userName,setUserName] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [password, setPassword] = useState('');
  const [streetName, setStreetName] = useState('');
  const [streetNumber, setStreetNumber] = useState('');
  const [city, setCity] = useState('');
  const [country, setCountry] = useState('');
  const [postalCode, setPostalCode] = useState('');

  
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = {
      userName: userName,
      firstName: firstName,
      lastName: lastName,
      password: password,
      shippingAddress: {
          streetName: streetName,
          streetNumber: streetNumber,
          city: city,
          country: country,
          postalCode: postalCode
      }
  };
    try{
      const response = await axios.post(`https://authorizationservice-fm4o.onrender.com/signUp/${userType}`, formData);
      const status = response.data.status;
      if(status === 'OK'){
        navigate('/signin');
        console.log('User signed up successfully!');
      }
      else{
        setCity('');
        setUserName('');
        setCountry('');
        setLastName('');
        setFirstName('');
        setPassword('');
        setPostalCode('');
        setStreetName('');
        setStreetNumber('');
        console.log('Error )signing up:', response.message);
        setErrorMessage(response.message);
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
              <input type="text" id="firstName" name="firstName" placeholder="First Name" required onChange={(e) => setFirstName(e.target.value)}/>
              <input type="text" id="lastName" name="lastName" placeholder="Last Name" required onChange={(e) => setLastName(e.target.value)}/>
              {userType === 'user' && (
                <>
                  <input type="text" id="streetNumber" name="streetNumber" placeholder="Street Number" required onChange={(e) => setStreetNumber(e.target.value)}/>
                  <input type="text" id="streetName" name="streetName" placeholder="Street Name" required onChange={(e) => setStreetName(e.target.value)}/>
                  <input type="text" id="city" name="city" placeholder="City" required onChange={(e) => setCity(e.target.value)}/>
                  <input type="text" id="country" name="country" placeholder="Country" required onChange={(e) => setCountry(e.target.value)}/>
                  <input type="text" id="postalCode" name="postalCode" placeholder="Postal Code" required onChange={(e) => setPostalCode(e.target.value)}/>
                </>
              )}
            </div>
            <div className="form-column">
              <input type="text" id="username" name="userName" placeholder="User Name" required onChange={(e) => setUserName(e.target.value)}/>
              <input type="password" id="password" name="password" placeholder="Password" required onChange={(e) => setPassword(e.target.value)}/>
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
