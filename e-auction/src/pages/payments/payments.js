import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './payments.css';

function Payments() {
  const [paymentDetails, setPaymentDetails] = useState({
    // Dummy data structures for user and payment details
    // Replace with actual user data fetched from the backend
    firstName: 'Jane',
    lastName: 'Doe',
    streetNumber: '123',
    streetName: 'Liberty Street',
    city: 'Freedom City',
    country: 'Utopia',
    postalCode: '12345',
    totalCost: '300.00',
  });

  const navigate = useNavigate();

  // Update payment details state
  const handleInputChange = (e) => {
    setPaymentDetails({ ...paymentDetails, [e.target.name]: e.target.value });
  };

  // add the payment submission logic here
  const submitPayment = (e) => {
    e.preventDefault();
    console.log('Payment details submitted:', paymentDetails);
    // TODO: Integrate with Stripe API for payment processing
    // TODO: Redirect to the confirmation/receipt page upon successful payment
    navigate('/confirmation');
  };

  return (
    <div className="payments-container">
      <Header />
      <div className="contents">
      <div className="user-details">
        <h2>Winning User Details</h2>
        {/* Render user address details */}
        <p>{paymentDetails.firstName} {paymentDetails.lastName}</p>
        <p>{paymentDetails.streetNumber} {paymentDetails.streetName}</p>
        <p>{paymentDetails.city}</p>
        <p>{paymentDetails.country}</p>
        <p>{paymentDetails.postalCode}</p>
        <p>Total Cost: ${paymentDetails.totalCost}</p>
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
      </div>
      </div>
    </div>
  );
};

export default Payments;
