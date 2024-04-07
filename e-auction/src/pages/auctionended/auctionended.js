import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './auctionended.css';


function AuctionEnded() {
  const [expeditedShipping, setExpeditedShipping] = useState(false);
  const navigate = useNavigate();
  const userName = location.state.userName;

  // Replace with actual logic to handle payments later
  const handlePayment = () => {
    // Only winning user can pay
    if (auctionData.userIsWinner) {
      console.log('Proceed to payment');
      navigate('/payments'); // Redirect to payment page
    } else {
      console.error('Only the winning user can pay for the item.');
    }
  };

  return (
    <div className="auction-ended-container">
      <Header />
      <h2 className="bidding-ended-message">Bidding Ended!!</h2>
      <div className="content-container">
        <div className="item-details">
          <h3>{auctionData.item.name}</h3>
          <img src={auctionData.item.image} alt={auctionData.item.name} />
          <p>Item Desciption: {auctionData.item.description}</p>
          <label>
            <input
              type="checkbox"
              checked={expeditedShipping}
              onChange={(e) => setExpeditedShipping(e.target.checked)}
            />
            Expedited Shipping (+${auctionData.item.expeditedShippingCost})
          </label>
        </div>
        <div className="winner-details">
          <p>Highest Bidder: {auctionData.highestBidder.username}</p>
          <p>Winning Price: ${auctionData.highestBidder.winningPrice}</p>
          <button onClick={handlePayment} className="pay-now-btn">Pay Now</button>
        </div>
      </div>
    </div>
  );
};

export default AuctionEnded;
