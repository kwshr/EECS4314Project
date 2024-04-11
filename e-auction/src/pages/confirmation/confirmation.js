import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header'; 
import './confirmation.css';

function Confirmation() {
  const navigate = useNavigate();
  const location = useLocation();
  const winningBidder = location.state.winningBidder;

  // Extracting data passed through location state
  const { userName, userDetails, finalCost, item } = location.state;
  const [shippingDays, setShippingDays] = useState('');

  useEffect(() => {
    // Fetch shipping details
    const fetchShippingDetails = async () => {
      try {
        const response = await axios.get(`https://e-auction-shipping.onrender.com/displayShippingDetails/${item.itemId}`);
        if (response.data.data) {
          setShippingDays(response.data.data);
        }
      } catch (error) {
        console.error('Error fetching shipping details:', error);
        setShippingDays('Unavailable');
      }
    };

    fetchShippingDetails();
  }, [item.itemId]);

  const handleBackToHome = () => {
    navigate('/home', { state: { userName: userName } });
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
        <div className="shipping-section">
          <h2>Shipping Details</h2>
          <p>The Item will be shipped in {shippingDays} days.</p>
          <div className="back-to-home-button-container">
            <button onClick={handleBackToHome}>BACK TO MAIN PAGE</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
