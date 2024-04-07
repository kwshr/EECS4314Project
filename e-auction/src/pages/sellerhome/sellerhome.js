import React from 'react';
import Header from '../../components/header/header';
import { useLocation, useNavigate } from 'react-router-dom';
import './sellerhome.css';

function SellerHome() {
  const navigate = useNavigate();
  const location = useLocation(); 
  const userName = location.state.userName;

  const handleUploadItem = () => {
    navigate('/sellitem',{ state: { userName: userName } });
  };

  const handleDutchAuctionUpdate = () => {
    navigate('/updateprice',{ state: { userName: userName } });
  };

  return (
    <div className="sellerhome-container">
      <Header />
      <h1 className="welcome-message">Welcome to the Seller Home Page!</h1>
      <div className="buttons-container">
        <button onClick={handleUploadItem} className="upload-item-btn">
          Upload Item for Auction
        </button>
        <button onClick={handleDutchAuctionUpdate} className="dutch-auction-update-btn">
          Dutch Auction Update Price
        </button>
      </div>
    </div>
  );
}

export default SellerHome;
