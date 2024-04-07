import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import Header from '../../components/header/header';
import './payments.css';

function Payments() {
  const location = useLocation();
  const userName = location.state.userName;
  const item = location.state.item
  const currentPrice = location.state.currentPrice;
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState(null);
  const finalShippingPrice = item.finalShippingPrice;
  const finalCost = currentPrice + finalShippingPrice;
  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(`https://your-backend-url.com/getUserId/${userName}`);
        const userId = response.data.data.userId;
        const userDetailsResponse = await axios.get(`https://your-backend-url.com/getDetails/${userId}`);
        const userDetails = userDetailsResponse.data.data;
        setUserDetails(userDetails);
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
        const response = await axios.get(`https://your-backend-url.com/calculateShippingCost/${item.itemId}`);
        const shippingCost = response.data.data.shippingCost;
        setShippingCost(shippingCost);
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
    try {
      const response = await axios.post(`https://your-api-domain.com/processPayment/${userName}/${item.itemId}`);
      if(response.data.status = 'OK'){
        navigate('/confirmation',{state:{userName: userName}});
      }
      else{
        setErrorMessage('Error processing payment:'+response.data.message);
      }
    } catch (error) {
      console.error('Error processing payment:', error);
    }
    
  };

  return (
    <div className="payments-container">
      <Header />
      <div className="contents">
      <div className="user-details">
        <h2>Winning User Details</h2>
        {/* Render user address details */}
        <p>{userDetails.firstName} {userDetails.lastName}</p>
        <p>{userDetails.streetNumber} {userDetails.streetName}</p>
        <p>{userDetails.city}</p>
        <p>{userDetails.country}</p>
        <p>{userDetails.postalCode}</p>
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
