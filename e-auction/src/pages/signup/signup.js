import React from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './signup.css';

function SignUp() {
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    // handle the form submission to the backend later
    navigate('/signin'); // go to the sign-in page on clicking submit button for now
  };

  return (
    <div className="signup-container">
      <Header />
      <div className="signup-form-container">
        <h2 className="signup-title">Sign Up Here!</h2>
        <form className="signup-form" onSubmit={handleSubmit}>
          <div className="form-column">
            <input type="text" id="firstName" name="firstName" placeholder="First Name" required />
            <input type="text" id="lastName" name="lastName" placeholder="Last Name" required />
            <input type="text" id="streetNumber" name="streetNumber" placeholder="Street Number" required />
            <input type="text" id="streetAddress" name="streetAddress" placeholder="Street Address" required />
            <input type="text" id="city" name="city" placeholder="City" required />
            <input type="text" id="country" name="country" placeholder="Country" required />
            <input type="text" id="postalCode" name="postalCode" placeholder="Postal Code" required />
          </div>
          <div className="form-column">
            <input type="text" id="username" name="username" placeholder="User Name" required />
            <input type="password" id="password" name="password" placeholder="Password" required />
          </div>
          <div className="form-submit">
            <button type="submit" className="submit-btn">Submit</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
