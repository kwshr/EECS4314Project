import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import Header from '../../components/header/header';
import './itemsearch.css';

function ItemSearch() {
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();
  const location = useLocation(); 
  const userName = location.state.userName;

  const handleSearch = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(`https://itemcatalogueservice.onrender.com/search/${searchTerm}`);
      navigate('/displayresults', { state: { items: response.data.data, userName: userName } });
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  };

  return (
    <div className="itemsearch-container">
      <Header />
      <div className="search-form-container">
        <h2>Search Items</h2>
        <form className="search-form" onSubmit={handleSearch}>
          <input
            type="text"
            id="search"
            name="search"
            placeholder="Search by name, price or auction type"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            required
          />
          <button type="submit" className="search-btn">SEARCH</button>
        </form>
      </div>
    </div>
  );
};

export default ItemSearch;
