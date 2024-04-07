import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './forwardbidding.css';

// Adjusted dummy data (Removed image property)
const itemData = {
  id: 1,
  name: 'Rolex GMT Master II',
  description: 'A rare and exquisite Rolex GMT Master II from the late 90s for wristwatch connoisseurs',
  shippingCost: '45.00',
  currentPrice: '150000.00',
  highestBidder: 'Dani',
  remainingTime: '2h 15m' // calculate dynamically after backend integration
};

function ForwardBidding() {
    const navigate = useNavigate();

  const [bidAmount, setBidAmount] = useState('');

  const handleBid = (event) => {
    event.preventDefault();
    console.log('Bid submitted:', bidAmount);
    alert('Bid submitted: ' + bidAmount);
    setBidAmount('');
    navigate('/auctionended');
  };

  const formatRemainingTime = (remainingTime) => {
    return remainingTime;
  };

  return (
    <div className="forward-bidding-container">
      <Header />
      <div className="content">
        <div className="item-details">
          <h2>{itemData.name}</h2>
          <p>Item Description: {itemData.description}</p>
          <p>Normal Shipping Cost: ${itemData.shippingCost}</p>
        </div>
        <div className="bidding-details">
          <p>Current Price: ${itemData.currentPrice}</p>
          <p>Highest Bidder: {itemData.highestBidder}</p>
          <form onSubmit={handleBid}>
            <input
              type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              min={parseFloat(itemData.currentPrice) + 1}
              placeholder="Place Bids! (US$)"
            />
            <button type="submit">BID</button>
          </form>
          <div className="remaining-time">
            <p>Remaining Time: {formatRemainingTime(itemData.remainingTime)}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ForwardBidding;
