import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './sellitem.css';

function SellItem() {
  const navigate = useNavigate();
  const location = useLocation();
  const userName = location.state?.userName;

  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [auctionType, setAuctionType] = useState('');
  const [initialPrice, setInitialPrice] = useState('');
  const [shippingTime, setShippingTime] = useState('');
  const [shippingCost, setShippingCost] = useState('');
  const [expeditedShippingCost, setExpeditedShippingCost] = useState('');
  const [reservedPrice, setReservedPrice] = useState('');
  const [dutchEndTimer, setDutchEndTimer] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // Fetch the seller ID
      const sellerResponse = await axios.get(`https://authorizationservice-fm4o.onrender.com/getSellerId/${userName}`);
      const sellerId = sellerResponse.data.data.SellerID;

      // Construct item data payload
      const itemData = {
        itemName,
        itemDescription,
        auctionType,
        price: Number(initialPrice),
        shippingTime: Number(shippingTime),
        shippingCost: Number(shippingCost),
        expeditedShippingCost: Number(expeditedShippingCost),
        ...(auctionType.toLowerCase() === 'dutch' && {
          reservedPrice: Number(reservedPrice),
          dutchEndTimer: Number(dutchEndTimer),
        }),
      };

      // Post the item data
      const addItemResponse = await axios.post(`https://sellerservice.onrender.com/addItem/${sellerId}`, itemData);

      if (addItemResponse.data.status === 'OK') {
        const itemId = addItemResponse.data.data;
        alert('Item has been successfully uploaded for auction! Please preserve the itemId for this item: '+itemId);
        navigate('/sellerhome', { state: { userName } });
      } else {
        alert('Failed to upload the item. Please try again.');
      }
    } catch (error) {
      console.error('Error during the item upload:', error);
      alert('Failed to upload the item. Please try again.');
    }
  };

  return (
    <div className="sellitem-container">
      <Header />
      <h2 className="sellitem-title">Register Item</h2>
      <form className="sellitem-form" onSubmit={handleSubmit}>
        <input
          type="text"
          id="itemName"
          name="itemName"
          value={itemName}
          onChange={(e) => setItemName(e.target.value)}
          placeholder="Item Name"
          required
        />
        <textarea
          id="itemDescription"
          name="itemDescription"
          value={itemDescription}
          onChange={(e) => setItemDescription(e.target.value)}
          placeholder="Item Description"
          required
        />
        <select id="auctionType" name="auctionType" value={auctionType}
          onChange={(e) => setAuctionType(e.target.value)} required>
          <option value="">Select Auction Type</option>
          <option value="forward">Forward</option>
          <option value="dutch">Dutch</option>
        </select>
        <input type="number" id="initialPrice" name="initialPrice" value={initialPrice}
          onChange={(e) => setInitialPrice(e.target.value)} placeholder="Starting Bid Price (US$)" required />
        <input type="number" id="shippingTime" name="shippingTime" value={shippingTime}
          onChange={(e) => setShippingTime(e.target.value)} placeholder="Shipping Time (days)" required />
        <input type="number" id="shippingCost" name="shippingCost" value={shippingCost}
          onChange={(e) => setShippingCost(e.target.value)} placeholder="Shipping Cost (US$)" required />
        <input type="number" id="expeditedShippingCost" name="expeditedShippingCost" value={expeditedShippingCost}
          onChange={(e) => setExpeditedShippingCost(e.target.value)} placeholder="Expedited Shipping Cost (US$)" required />
        {auctionType.toLowerCase() === 'dutch' && (
          <>
            <input
              type="number"
              id="reservedPrice"
              name="reservedPrice"
              value={reservedPrice}
              onChange={(e) => setReservedPrice(e.target.value)}
              placeholder="Reserved Price (US$)"
              required
            />
            <input
              type="number"
              id="dutchEndTimer"
              name="dutchEndTimer"
              value={dutchEndTimer}
              onChange={(e) => setDutchEndTimer(e.target.value)}
              placeholder="Dutch Auction End Timer (seconds)"
              required
            />
          </>
        )}
        <button type="submit" className="upload-btn">Upload Item</button>
      </form>
    </div>
  );
}

export default SellItem;