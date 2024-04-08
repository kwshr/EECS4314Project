import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header';
import './displayresults.css';

function formatDuration(duration) {
  const pattern = /PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?/;
  const matches = pattern.exec(duration);
  if (!matches) return duration; // Return original if no match

  const hours = matches[1] ? `${matches[1]} Hours, ` : '';
  const minutes = matches[2] ? `${matches[2]} Minutes, ` : '';
  const seconds = matches[3] ? `${matches[3]} Seconds` : '';
  return hours + minutes + seconds;
}

function DisplayResults() {
  const navigate = useNavigate();
  const location = useLocation();
  const items = location.state?.items || [];
  const userName = location.state.userName;
  const [selectedItemId, setSelectedItemId] = useState(null);
  const [itemsWithRemainingTime, setItemsWithRemainingTime] = useState([]);

  useEffect(() => {
    const fetchRemainingTimes = async () => {
      const fetchedItems = await Promise.all(items.map(async (item) => {
        if (item.auctionType.toLowerCase() === 'dutch') {
          return { ...item, remainingTime: 'NOW' };
        } else {
          try {
            const response = await axios.get(`https://auctionservice.onrender.com/getRemainingTime/${item.itemId}`);
            const formattedTime = formatDuration(response.data.data || 'N/A');
            return { ...item, remainingTime: formattedTime };
          } catch (error) {
            console.error('Error fetching remaining time for item:', error);
            return { ...item, remainingTime: 'Unavailable' };
          }
        }
      }));
      setItemsWithRemainingTime(fetchedItems);
    };

    if (items.length > 0) {
      fetchRemainingTimes();
    }
  }, [items]);

  const handleBackToSearch = () => {
    navigate('/itemsearch', { state: { userName: userName } });
  };

  const handleBid = () => {
    const selectedItem = itemsWithRemainingTime.find(item => item.itemId === selectedItemId);
    if (selectedItem) {
      const page = selectedItem.auctionType.toLowerCase() === 'dutch' ? '/dutchbidding' : '/forwardbidding';
      navigate(page, { state: { item: selectedItem, userName: userName } });
    }
  };

  return (
    <div className="displayresults-container">
      <Header />
      <table className="results-table">
        <thead>
          <tr>
            <th>Item Name</th>
            <th>Current Price</th>
            <th>Auction Type</th>
            <th>Remaining Time</th>
            <th>Select</th>
          </tr>
        </thead>
        <tbody>
          {itemsWithRemainingTime.map((item) => (
            <tr key={item.itemId}>
              <td>{item.itemName}</td>
              <td>${item.price}</td>
              <td>{item.auctionType}</td>
              <td>{item.remainingTime}</td>
              <td>
                <input
                  type="radio"
                  name="selectedItem"
                  value={item.itemId}
                  onChange={() => setSelectedItemId(item.itemId)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="buttons-container">
        <button onClick={handleBackToSearch} className="back-search-btn">Back to Search</button>
        <button onClick={handleBid} className="bid-btn" disabled={!selectedItemId}>BID</button>
      </div>
    </div>
  );
};

export default DisplayResults;
