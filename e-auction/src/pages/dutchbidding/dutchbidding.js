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

  const buyNow = async()=>{
    try{
      const response3 = await axios.get(`https://authorizationservice-fm4o.onrender.com/getUserId/${userName}`);
      if(response3.data.status =='OK'){
        const userId = response3.data.data.UserId;
        const response2 = await axios.put(`https://auctionservice.onrender.com/endAuction/${item.itemId}/${userId}`);
        if(response2.data.status =='OK'){
          const response = await axios.put(`https://auctionservice.onrender.com/endAuction/${item.itemId}`)
          if(response.data.status = 'OK'){
            navigate('/auctionended', { state: { item: item, userName: userName } });
          }
          else{
            alert("Ending Auction failed!");
          }
        }
        else{
          alert("Ending Auction failed!");
        }
      }
      else{
        alert("Ending Auction failed!");
      }
      }
    catch(error){
      alert("Ending Auction failed!");
    }
   
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
          <button onClick={buyNow} className="buy-now-btn" type = 'submit'>BUY NOW!</button>
        </div>
      </div>
    </div>
  );
}

export default DutchBidding;
