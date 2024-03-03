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
      <div className="receipt-section">
        <h2>Receipt</h2>
        <p>{userDetails.firstName} {userDetails.lastName}</p>
        <p>{userDetails.streetNumber} {userDetails.streetName}</p>
        <p>{userDetails.city}</p>
        <p>{userDetails.country}</p>
        <p>{userDetails.postalCode}</p>
        <p>Total Paid: ${userDetails.totalPaid}</p>
        <p>Item ID: {userDetails.itemId}</p>
      </div>
      <div className="shipping-section">
        <h2>Shipping Details</h2>
        <p>The Item will be shipped in {shippingDays} days.</p>
        <button onClick={handleBackToHome}>Back to Main Page</button>
      </div>
    </div>
  );
};

export default Confirmation;
