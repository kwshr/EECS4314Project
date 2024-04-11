import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import Header from '../../components/header/header';
import './forwardbidding.css';

function ForwardBidding() {
  const navigate = useNavigate();
  const location = useLocation();
  const { item, userName } = location.state;

  const [currentPrice, setCurrentPrice] = useState('');
  const [highestBidder, setHighestBidder] = useState('');
  const [remainingTime, setRemainingTime] = useState('');
  const [bidAmount, setBidAmount] = useState('');

  // Function to fetch item details, remaining time, and highest bidder's username
  const fetchItemDetails = async () => {
    try {
      // Fetch auctioned item details
      const response = await axios.get(`https://auctionservice.onrender.com/getAuctionedItemDetails/${item.itemId}`);
      setCurrentPrice(response.data.data.CurrentPrice);
      const auctionStatus = response.data.data.AuctionStatus;
      if(response.data.status ==='OK'){
      const winningBidderId = response.data.data.WinningBidder;
      const userDetailsResponse = await axios.get(
        `https://authorizationservice-fm4o.onrender.com/getDetails/${winningBidderId}`
      );
      setHighestBidder(userDetailsResponse.data.data.UserName);
      }
      // Fetch remaining time
      const timeResponse = await axios.get(`https://auctionservice.onrender.com/getRemainingTime/${item.itemId}`);
      const formattedTime = formatDuration(timeResponse.data.data);
      setRemainingTime(formattedTime);
    
      // Check if auction ended based on remaining time and navigate
      if (auctionStatus === 'Ended') {
        navigate('/auctionended', { state: { item, userName } });
      }

      // Fetch winning bidder's username if there's a winning bidder ID
      
    } catch (error) {
      console.error('Error fetching item details:', error);
    }
  };

  useEffect(() => {
    fetchItemDetails();
  }, [item.itemId]);

  const handleBid = async (event) => {
    event.preventDefault();
    try {
      // Attempt to place a bid
      const bidResponse = await axios.post(`https://biddingservice.onrender.com/placeBid/${userName}/${item.itemId}/${bidAmount}`);
      if (bidResponse.data.status === 'OK') {
        alert('Bid successful!');
        setBidAmount('');
        fetchItemDetails(); // Refresh item details after successful bid
      } else {
        alert('Bid failed: ' + bidResponse.data.message);
      }
    } catch (error) {
      console.error('Error placing bid:', error);
      alert('Error placing bid');
    }
  };

  // Function to format duration from ISO 8601 to a more readable format
  function formatDuration(duration) {
    if (!duration || duration === 'N/A') return 'Auction Ended';
    const pattern = /PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?/;
    const matches = pattern.exec(duration);
    if (!matches) return duration;

    const hours = matches[1] ? `${matches[1]} Hours, ` : '';
    const minutes = matches[2] ? `${matches[2]} Minutes, ` : '';
    const seconds = matches[3] ? `${matches[3]} Seconds` : '';
    return hours + minutes + seconds.trimEnd(',');
  }

  return (
    <div className="forward-bidding-container">
      <Header />
      <div className="content">
        <div className="item-details">
          <h2>{item.itemName}</h2>
          <p>Item Description: {item.itemDescription}</p>
          <p>Normal Shipping Cost: ${item.shippingCost}</p>
        </div>
        <div className="bidding-details">
          <p>Current Price: ${currentPrice}</p>
          <p>Highest Bidder: {highestBidder}</p>
          <form onSubmit={handleBid}>
            <input
              type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              min={parseFloat(currentPrice) + 1}
              placeholder="Enter your bid (US$)"
              required
            />
            <button type="submit">BID</button>
          </form>
          <p>Remaining Time: {remainingTime}</p>
        </div>
      </div>
    </div>
  );
}

export default ForwardBidding;
