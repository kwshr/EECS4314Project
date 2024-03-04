import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './displayresults.css';

// Generating additional dummy data
const generateDummyData = () => {
    const additionalItems = [];
    for (let i = 3; i <= 10; i++) {
      additionalItems.push({
        id: i,
        name: `Item ${i}`,
        price: `${100 * i}`,
        auctionType: i % 2 === 0 ? 'Dutch' : 'Forward',
        remainingTime: i % 2 === 0 ? 'N/A' : `${60 + i}mins ${i}secs`
      });
    }
    return additionalItems;
  };  

// Dummy data array
const items = [
  { id: 1, name: 'Bicycle', price: '100', auctionType: 'Dutch', remainingTime: 'N/A' },
  { id: 2, name: 'Car', price: '1000', auctionType: 'Forward', remainingTime: '1h 50mins 3secs' },
  ...generateDummyData() // populate array with generated dummy items
];

function DisplayResults() {
  const [selectedItemId, setSelectedItemId] = useState(null);
  const navigate = useNavigate();

  const handleBackToSearch = () => {
    navigate('/itemsearch');
  };

  const handleBid = () => {
    const selectedItem = items.find(item => item.id === selectedItemId);
    const page = selectedItem.auctionType.toLowerCase() === 'dutch' ? '/dutchbidding' : '/forwardbidding';
    navigate(page);
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
          {items.map(item => (
            <tr key={item.id}>
              <td>{item.name}</td>
              <td>${item.price}</td>
              <td>{item.auctionType}</td>
              <td>{item.remainingTime}</td>
              <td>
                <input
                  type="radio"
                  name="selectedItem"
                  value={item.id}
                  onChange={() => setSelectedItemId(item.id)}
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
