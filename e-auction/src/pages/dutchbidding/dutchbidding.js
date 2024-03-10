import React from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './dutchbidding.css';

// Dummy data
const item = {
    id: 1,
    name: 'Rolex GMT Master II',
    image: require('../../components/assets/rolex.jpeg'),
    description: 'A rare and exquisite Rolex GMT Master II from the late 90s for wristwatch connoisseurs',
    shippingCost: '45.00',
    currentPrice: '150000.00' //updates dynamically
};

function DutchBidding() {
  const navigate = useNavigate();

  // use actual logic to handle the 'Buy Now' action
  const buyNow = () => {
    console.log('Buy Now clicked for item:', item.id);
    
    navigate('/auctionended');
  };

  return (
    <div className="dutchbidding-container">
      <Header />
      <div className="auction-container">
        <div className="item-details">
          <h2>{item.name}</h2>
          <img src={item.image} alt={item.name} />
          <p>Item Description: {item.description}</p>
          <p>Normal Shipping Cost: ${item.shippingCost}</p>
        </div>
        <div className="bidding-details">
          <p className="current-price">Current Price: ${item.currentPrice}</p>
          <button onClick={buyNow} className="buy-now-btn">BUY NOW!</button>
        </div>
      </div>
    </div>
  );
};

export default DutchBidding;
