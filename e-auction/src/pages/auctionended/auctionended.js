import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header';
import './auctionended.css';

function AuctionEnded() {
  const navigate = useNavigate();
  const location = useLocation();
  const [expeditedShipping, setExpeditedShipping] = useState(false);
  const [winningBidder, setWinningBidder] = useState({ username: '', winningPrice: '' });
  const [loading, setLoading] = useState(true);

  const item = location.state?.item;
  const userName = location.state?.userName;

  useEffect(() => {
    const fetchAuctionDetails = async () => {
      try {
        const auctionDetailsResponse = await axios.get(
          `https://auctionservice.onrender.com/getAuctionedItemDetails/${item.itemId}`
        );
        const winningPrice = auctionDetailsResponse.data.data.CurrentPrice;
        const winnerUserId = auctionDetailsResponse.data.data.WinningBidder;

        if (winnerUserId) {
          const userDetailsResponse = await axios.get(
            `https://authorizationservice-fm4o.onrender.com/getDetails/${winnerUserId}`
          );
          setWinningBidder({
            username: userDetailsResponse.data.data.UserName,
            winningPrice: winningPrice
          });
        } else {
          // If there's no winning bidder ID, set only the winning price
          setWinningBidder((prevBidder) => ({
            ...prevBidder,
            winningPrice: winningPrice
          }));
        }
      } catch (error) {
        console.error('Error fetching auction details:', error);
      } finally {
        setLoading(false);
      }
    };

    if (item && item.itemId) {
      fetchAuctionDetails();
    }
  }, [item]); // Removed winningBidder from dependencies to prevent re-triggering useEffect

  const handlePayment = async () => {
    if (userName === winningBidder.username) {
      console.log('Proceed to payment');
      
      if (expeditedShipping) {
        try {
          await axios.put(`https://e-auction-shipping.onrender.com/expeditedShipping/${item.itemId}`);
        } catch (error) {
          console.error('Error updating expedited shipping:', error);
        }
        
      }
      try {
        await axios.put(`https://e-auction-shipping.onrender.com/calculateShippingCost/${item.itemId}`);
        
        navigate('/payments', { state: { item: item, userName: userName, currentPrice: winningBidder.winningPrice } });
      } catch (error) {
        console.error('Error calculating shipment cost:', error);
      }
      
      
    } else {
      // console.error('Only the winning user can pay for the item.');
      navigate('/itemsearch', { state: { userName: userName} });
    }
  };

  const handleBackToItemSearch = () => {
    navigate('/itemsearch', { state: { userName: userName } });
  };

  if (loading) {
    return <p>Loading auction details...</p>;
  }

  return (
    <div className="auction-ended-container">
      <Header />
      <h2 className="bidding-ended-message">Bidding Ended!</h2>
      <div className="content-container">
        <div className="item-details">
          <h3>{item?.itemName}</h3>
          <p>Item Description: {item?.itemDescription}</p>
          <p>Expedited Shipping Cost: ${item?.expeditedShippingCost}</p>
          <label>
            <input
              type="checkbox"
              checked={expeditedShipping}
              onChange={(e) => setExpeditedShipping(e.target.checked)}
            />
            Add Expedited Shipping
          </label>
        </div>
        <div className="winner-details">
          <p>Highest Bidder: {winningBidder.username}</p>
          <p>Winning Price: ${winningBidder.winningPrice}</p>
          {/* {userName === winningBidder.username && ( */}
            <button onClick={handlePayment} className="pay-now-btn" disabled={userName !== winningBidder.username}>
              Pay Now
            </button>
          {/* )} */}
        </div>
      </div>
      <button onClick={handleBackToItemSearch} className="back-to-search-btn">Back to Item Search</button>
    </div>
  );
}

export default AuctionEnded;
