import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import Header from '../../components/header/header'; 
import './confirmation.css';

function Confirmation() {
  const navigate = useNavigate();
  const location = useLocation();

  // Extracting data passed through location state
  const { userDetails, finalCost, item } = location.state;

  const handleBackToHome = () => {
    navigate('/home');
  };

  return (
    <div className="confirmation-container">
      <Header />
      <div className="contents">
        <div className="receipt-section">
          <h2>Receipt</h2>
          <p data-label="First Name:"><span>{userDetails.FirstName}</span></p>
          <p data-label="Last Name:"><span>{userDetails.LastName}</span></p>
          <p data-label="Street Number:"><span>{userDetails.StreetNumber}</span></p>
          <p data-label="Street Name:"><span>{userDetails.StreetName}</span></p>
          <p data-label="City:"><span>{userDetails.City}</span></p>
          <p data-label="Country:"><span>{userDetails.Country}</span></p>
          <p data-label="Postal Code:"><span>{userDetails.PostalCode}</span></p>
          <p data-label="Total Paid:" className="total-paid"><span>${finalCost}</span></p>
          <p data-label="Item ID:"><span>{item.itemId}</span></p>
        </div>
        {/* Shipping Section will be adjusted in the next step */}
        <div className="shipping-section">
          <h2>Shipping Details</h2>
          {/* Shipping details will be displayed here after fetching from backend */}
          <div className="back-to-home-button-container">
            <button onClick={handleBackToHome}>BACK TO MAIN PAGE</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
