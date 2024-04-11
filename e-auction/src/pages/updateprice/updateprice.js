import React, { useState, useEffect } from 'react';
import Header from '../../components/header/header';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import './updateprice.css';

function UpdatePrice() {
  const navigate = useNavigate();
  const location = useLocation();
  const userName = location.state?.userName; // Get userName passed from previous page
  const [itemId, setItemId] = useState('');
  const [updatedPrice, setUpdatedPrice] = useState('');

  useEffect(() => {
    // If userName is not passed, redirect back to the home or login page
    if (!userName) {
      navigate('/'); // or '/login' or any appropriate route
    }
  }, [userName, navigate]);

  const handleUpdatePrice = async (event) => {
    event.preventDefault();

    const payload = {
      itemId: parseInt(itemId),
      newPrice: parseFloat(updatedPrice),
    };

    try {
      const response = await axios.put('https://sellerservice.onrender.com/updatePrice', payload);
      if (response.data.status === 'OK') {
        alert('The price for the given Dutch auction item has been successfully updated.');
        setItemId('');
        setUpdatedPrice('');
      } else {
        alert('Failed to update the price. Please try again.');
      }
    } catch (error) {
      console.error('Error updating price:', error);
      alert('Failed to update the price. Please try again.');
    }
  };

  return (
    <div className="updateprice-container">
      <Header />
      <h2 className="updateprice-title">Decrease price for Dutch auction item</h2>
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
      <button onClick={() => navigate('/sellerhome', { state: { userName } })} className="back-btn">
        Back to Seller Home
      </button>
    </div>
  );
}

export default UpdatePrice;