import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header'; 
import axios from 'axios'; 
import './confirmation.css';

function Confirmation() {
  const navigate = useNavigate();
  const location = useLocation(); 
  const userName = location.state.userName;
  const [receiptInfo, setReceiptInfo] = useState({});
  const [shippingDays, setShippingDays] = useState('');
  useEffect(() => {
    const fetchReceiptInfo = async () => {
      try {
        const itemId = 'exampleItemId';
        const response = await axios.get(`https://orderprocessingservice.onrender.com/generateReceipt/${userName}/${itemId}`);
        setReceiptInfo(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchReceiptInfo();
  }, []);

  const handleBackToHome = () => {
    navigate('/home'); // Navigate back to the home page for now
  };

  return (
    <div className="confirmation-container">
      <Header />
      <div className="contents">
        <div className="receipt-section">
          <h2>Receipt</h2>
          {/* Display receipt information */}
          {Object.keys(receiptInfo).map((key) => (
            <p key={key} data-label={key}><span>{receiptInfo[key]}</span></p>
          ))}
        </div>
        <div className="shipping-section">
          <h2>Shipping Details</h2>
          {/* Display shipping details */}
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
