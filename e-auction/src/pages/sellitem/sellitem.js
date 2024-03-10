import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useNavigate } from 'react-router-dom';
import './sellitem.css';

function SellItem() {
  const navigate = useNavigate();
  
  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [auctionType, setAuctionType] = useState('');
  const [initialPrice, setInitialPrice] = useState('');
  const [shippingTime, setShippingTime] = useState('');
  const [shippingCost, setShippingCost] = useState('');
  const [expeditedShippingCost, setExpeditedShippingCost] = useState('');
  const [reservedPrice, setReservedPrice] = useState('');
  const [dutchEndTimer, setDutchEndTimer] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    const success = true; //replace with actual value after backend integration

    if (success) {
      alert('Item has been successfully uploaded for auction! Let us go back to the seller home page!'); //can make a separate page returning the Item ID later.

      setItemName('');
      setItemDescription('');
      setAuctionType('');
      setInitialPrice('');
      setShippingTime('');
      setShippingCost('');
      setExpeditedShippingCost('');
      setReservedPrice('');
      setDutchEndTimer('');

      navigate('/sellerhome');
    } else {
      alert('Failed to upload the item. Please try again.'); //can be more specific later
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
          <option value="">Auction Type</option>
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
        <input type="number" id="reservedPrice" name="reservedPrice" value={reservedPrice}
          onChange={(e) => setReservedPrice(e.target.value)}placeholder="Item Reserved Price (US$)" required />
        <input type="number" id="dutchEndTimer" name="dutchEndTimer" value={dutchEndTimer}
          onChange={(e) => setDutchEndTimer(e.target.value)} placeholder="Dutch End Timer (seconds)" required />
        <button type="submit" className="upload-btn">Upload</button>
      </form>
    </div>
  );
}

export default SellItem;
