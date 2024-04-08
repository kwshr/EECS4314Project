import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header';
import './payments.css';

function Payments() {
  const location = useLocation();
  const userName = location.state.userName;
  const item = location.state.item
  const currentPrice = location.state.currentPrice;
  const [errorMessage, setErrorMessage] = useState('');

  const [paymentDetails, setPaymentDetails] = useState({
    cardName: '',
    cardNumber: '',
    expiryDate: '',
    securityCode: '',
  });

  const [shippingCost, setShippingCost] = useState(0);

  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({
    FirstName: '',
    LastName: '',
    StreetNumber: '',
    StreetName: '',
    City: '',
    Country: '',
    PostalCode: '',
  });
  
  const finalCost = currentPrice + shippingCost;
  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(`https://authorizationservice-fm4o.onrender.com/getUserId/${userName}`);
        if(response.data.status === 'OK') {
          const userId = response.data.data.UserId;
          const userDetailsResponse = await axios.get(`https://authorizationservice-fm4o.onrender.com/getDetails/${userId}`);
          if(userDetailsResponse.data.status === 'OK'){
            const userDetails = userDetailsResponse.data.data;
            setUserDetails(userDetails);
          }
        }
        
        
      } catch (error) {
        console.error('Error fetching user details:', error);
      }
    };

    if (userName) {
      fetchUserDetails();
    }
  }, [userName]);

  useEffect(() => {
    const fetchShippingCost = async () => {
      try {
        const response = await axios.put(`https://e-auction-shipping.onrender.com/calculateShippingCost/${item.itemId}`);
        if(response.data.status === 'OK') {
          const shippingCost = response.data.data;
          setShippingCost(shippingCost);
        }
      } catch (error) {
        console.error('Error fetching shipping cost:', error);
      }
    };

    fetchShippingCost();
  }, [item.itemId]);

  const handleInputChange = (e) => {
    setPaymentDetails({ ...paymentDetails, [e.target.name]: e.target.value });
  };

  const submitPayment =async (e) => {
    e.preventDefault();
    navigate('/confirmation', {
      state: {
        userName: userName,
        userDetails: userDetails,
        item: item,
        finalCost: finalCost
      }
    });
    // try {
    //   const response = await axios.post(`https://paymentservice-tbm3.onrender.com/processPayment/${userName}/${item.itemId}`);
    //   if(response.data.status === 'OK'){
    //     navigate('/confirmation',{state:{userName: userName}});
    //   }
    //   else{
    //     setErrorMessage('Error processing payment:'+response.data.message);
    //   }
    // } catch (error) {
    //   console.error('Error processing payment:', error);
    // }
    
  };

  return (
    <div className="payments-container">
      <Header />
      <div className="contents">
      <div className="user-details">
        <h2>Winning User Details</h2>
        {/* Render user address details */}
        <p>{userDetails.FirstName} {userDetails.LastName}</p>
        <p>{userDetails.StreetNumber} {userDetails.StreetName}</p>
        <p>{userDetails.City}</p>
        <p>{userDetails.Country}</p>
        <p>{userDetails.PostalCode}</p>
        <p>Total Cost: ${finalCost}</p>
      </div>
      <div className="payment-details">
        <h2>Credit Card</h2>
        <form onSubmit={submitPayment}>
          <input
            type="text"
            name="cardName"
            placeholder="Name on Card"
            value={paymentDetails.cardName}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="cardNumber"
            placeholder="Card Number"
            value={paymentDetails.cardNumber}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="expiryDate"
            placeholder="Expiration Date"
            value={paymentDetails.expiryDate}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="securityCode"
            placeholder="Security Code"
            value={paymentDetails.securityCode}
            onChange={handleInputChange}
            required
          />
          <button type="submit">SUBMIT</button>
        </form>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
      </div>
      </div>
    </div>
  );
};

export default Payments;