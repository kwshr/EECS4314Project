import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/header';
import './itemsearch.css';

function ItemSearch() {
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

  const handleSearch = (event) => {
    event.preventDefault();
    // handle the search logic with the searchTerm state.
    // don't have backend integration rn, just redirect to the display results page for now
    navigate('/displayresults');
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
