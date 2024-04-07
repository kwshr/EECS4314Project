import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header';
import './dutchbidding.css';

function DutchBidding() {
  const navigate = useNavigate();
  const location = useLocation();
  const [currentPrice, setCurrentPrice] = useState('Fetching...');
  const userName = location.state.userName;

  // Access item details and user info from the location state
  const item = location.state?.item || {
    itemId: null,
    itemName: 'No Item Selected',
    itemDescription: 'N/A',
    shippingCost: '0.00'
  };

  useEffect(() => {
    // Function to fetch current price from an API
    const fetchCurrentPrice = async () => {
      try {
        const response = await axios.get(`https://auctionservice.onrender.com/getAuctionedItemDetails/${item.itemId}`);
        setCurrentPrice(response.data.data.CurrentPrice);
      } catch (error) {
        console.error('Error fetching current price:', error);
        setCurrentPrice('Unavailable');
      }
    };

    if (item.itemId) {
      fetchCurrentPrice();
    }
  }, [item.itemId]);

  const buyNow = () => {
    console.log('Buy Now clicked for item:', item.itemId, 'by user:', userName);
    // Navigate to /auctionended and pass item and user state forward
    navigate('/auctionended', { state: { item: item, userName: userName } });
  };

  return (
    <div className="dutchbidding-container">
      <Header />
      <div className="auction-container">
        <div className="item-details">
          <h2>{item.itemName}</h2>
          <p>Item Description: {item.itemDescription}</p>
          <p>Normal Shipping Cost: ${item.shippingCost}</p>
        </div>
        <div className="bidding-details">
          <p className="current-price">Current Price: {currentPrice}</p>
          <button onClick={buyNow} className="buy-now-btn">BUY NOW!</button>
        </div>
      </div>
    </div>
  );
}

export default DutchBidding;
