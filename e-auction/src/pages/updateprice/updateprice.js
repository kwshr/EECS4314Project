import React, { useEffect, useState } from 'react';
import Header from '../../components/header/header';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './updateprice.css';

function UpdatePrice() {
  const navigate = useNavigate();
  const location = useLocation();
  const userName = location.state.userName;
  const [sellerId, setSellerId] = useState('');
  const [itemId, setItemId] = useState('');
  const [updatedPrice, setUpdatedPrice] = useState('');
  const [currentPrice, setCurrentPrice] = useState(); // Add state to hold the current price

  // Get sellerId when the component mounts or when userName changes
  useEffect(() => {
    const fetchSellerId = async () => {
      const response = await axios.get(`https://authorizationservice-fm4o.onrender.com/getSellerId/${userName}`);
      setSellerId(response.data.data.SellerID);
    };
    fetchSellerId();
  }, [userName]);

  const handleUpdatePrice = async (event) => {
    event.preventDefault();
    // You would want to get the current price of the item first
    // and compare to make sure the updated price is less.
    // Here's a pseudocode placeholder for that logic:
    // if (updatedPrice >= currentPrice) {
    //   alert('Updated price must be less than the current price.');
    //   return;
    // }

    try {
      const response = await axios.put('https://sellerservice.onrender.com/updatePrice', {
        itemId,
        updatedPrice: Number(updatedPrice),
        sellerId
      });
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
        {/* Form inputs remain the same */}
        <button type="submit" className="update-price-btn">Update Price</button>
      </form>
      <button onClick={() => navigate('/sellerhome', { state: { userName } })} className="back-btn">Back to Seller Home</button>
    </div>
  );
}

export default UpdatePrice;
