import React from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header'; 
import './confirmation.css';

function Confirmation() {
  const navigate = useNavigate();

  // Dummy data. Replace with actual data fetched from backend.
  const userDetails = {
    firstName: 'John',
    lastName: 'Doe',
    streetNumber: '100',
    streetName: 'Liberty Street',
    city: 'Freedom City',
    country: 'Utopia',
    postalCode: '12345',
    totalPaid: '500',
    itemId: 'ABC123',
  };

  const shippingDays = '5'; // will be fetched from the item's details in the database

  const handleBackToHome = () => {
    navigate('/home'); // Navigate back to the home page for now
  };

  return (
    <div className="confirmation-container">
      <Header />
      <div className="contents">
        <div className="receipt-section">
          <h2>Receipt</h2>
          {/* Data-labels are added for styling purposes */}
          <p data-label="First Name:"><span>{userDetails.firstName}</span></p>
          <p data-label="Last Name:"><span>{userDetails.lastName}</span></p>
          <p data-label="Street Number:"><span>{userDetails.streetNumber}</span></p>
          <p data-label="Street Name:"><span>{userDetails.streetName}</span></p>
          <p data-label="City:"><span>{userDetails.city}</span></p>
          <p data-label="Country:"><span>{userDetails.country}</span></p>
          <p data-label="Postal Code:"><span>{userDetails.postalCode}</span></p>
          <p data-label="Total Paid:" className="total-paid"><span>${userDetails.totalPaid}</span></p>
          <p data-label="Item ID:"><span>{userDetails.itemId}</span></p>
        </div>
        <div className="shipping-section">
          <h2>Shipping Details</h2>
          <p><span>The Item will be shipped in {shippingDays} days.</span></p>
          <div className="back-to-home-button-container">
            <button onClick={handleBackToHome}>BACK TO MAIN PAGE</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
