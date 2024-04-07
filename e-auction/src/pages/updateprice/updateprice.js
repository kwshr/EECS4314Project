import React, { useState } from 'react';
import Header from '../../components/header/header';
import { useLocation, useNavigate } from 'react-router-dom';
import './updateprice.css';

function UpdatePrice() {
  const navigate = useNavigate();
  const location = useLocation(); 
  const userName = location.state.userName;
  const [itemId, setItemId] = useState('');
  const [updatedPrice, setUpdatedPrice] = useState('');

  const handleUpdatePrice = (event) => { // implement logic to check that entered price is less than current price.
    event.preventDefault();
    const success = true; //replace with actual value after backend integration

    if (success) {
      alert('The price for the given dutch auction item has been successfully updated.');
      setItemId('');
      setUpdatedPrice('');
    } else {
      alert('Failed to update the price. Please try again.');
    }
  };

  return (
    <div className="updateprice-container">
      <Header />
      <h2 className="updateprice-title">Decrease price for dutch auction item</h2>
      <form className="updateprice-form" onSubmit={handleUpdatePrice}>
        <input
          type="text"
          id="itemId"
          name="itemId"
          value={itemId}
          onChange={(e) => setItemId(e.target.value)}
          placeholder="Item ID"
          required
        />
        <input
          type="number"
          id="updatedPrice"
          name="updatedPrice"
          value={updatedPrice}
          onChange={(e) => setUpdatedPrice(e.target.value)}
          placeholder="Updated Price (US$)"
          required
        />
        <button type="submit" className="update-price-btn">Update Price</button>
      </form>
      <button onClick={() => navigate('/sellerhome')} className="back-btn">Back to Seller Home Page</button>
    </div>
  );
}

export default UpdatePrice;
