import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './forwardbidding.css';

// Dummy data
const itemData = {
  id: 1,
  name: 'Rolex GMT Master II',
  image: require('../../components/assets/rolex.jpeg'),
  description: 'A rare and exquisite Rolex GMT Master II from the late 90s for wristwatch connoisseurs',
  shippingCost: '45.00',
  currentPrice: '150000.00',
  highestBidder: 'Dani',
  remainingTime: '2h 15m' // calculate dynamically after backend integration
};

function ForwardBidding() {
    const navigate = useNavigate();

  const [bidAmount, setBidAmount] = useState('');

  // handle logic to submit bid
  const handleBid = (event) => {
    event.preventDefault();
    // send bid amount to Server
    console.log('Bid submitted:', bidAmount);
    alert(bidAmount);
    setBidAmount('');
    // fetch updated auction data from the server and update state
    navigate('/auctionended');
  };

  // replace with a real countdown
  const formatRemainingTime = (remainingTime) => {
    // format appropriately
    return remainingTime;
  };

  return (
    <div className="forward-bidding-container">
      <Header />
      <div className="content">
        <div className="item-details">
          <h2>{itemData.name}</h2>
          <img src={itemData.image} alt={itemData.name} />
          <p>Item Desciption: {itemData.description}</p>
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
              min={parseFloat(itemData.currentPrice) + 1} // bid amount > current price
              placeholder="Place Bids! (US$)"
            />
            <button type="submit">BID</button>
          </form>
          <p>Remaining Time: {formatRemainingTime(itemData.remainingTime)}</p>
        </div>
      </div>
    </div>
  );
};

export default ForwardBidding;
