import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/home/home';
import SignUp from './pages/signup/signup';
import SignIn from './pages/signin/signin';
import ItemSearch from './pages/itemsearch/itemsearch';
import DisplayResults from './pages/displayresults/displayresults';
import ForwardBidding from './pages/forwardbidding/forwardbidding';
import DutchBidding from './pages/dutchbidding/dutchbidding';
import AuctionEnded from './pages/auctionended/auctionended';
import Payments from './pages/payments/payments';
import Confirmation from './pages/confirmation/confirmation';
import SellerHome from './pages/sellerhome/sellerhome';
import SellItem from './pages/sellitem/sellitem';
import UpdatePrice from './pages/updateprice/updateprice';
import ForgotPassword from './pages/forgotpassword/forgotpassword';
import ResetPassword from './pages/resetpassword/resetpassword';
import './App.css';


function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/home" element={<Home />} />
          <Route exact path="/signup" element={<SignUp />} />
          <Route exact path="/signin" element={<SignIn />} />
          <Route exact path="/itemsearch" element={<ItemSearch />} />
          <Route exact path="/displayresults" element={<DisplayResults />} />
          <Route exact path="/forwardbidding" element={<ForwardBidding />} />
          <Route exact path="/dutchbidding" element={<DutchBidding />} />
          <Route exact path="/auctionended" element={<AuctionEnded />} />
          <Route exact path="/payments" element={<Payments />} />
          <Route exact path="/confirmation" element={<Confirmation />} />
          <Route exact path="/sellerhome" element={<SellerHome />} />
          <Route exact path="/sellitem" element={<SellItem />} />
          <Route exact path="/updateprice" element={<UpdatePrice />} />
          <Route exact path="/forgotpassword" element={<ForgotPassword />} />
          <Route exact path="/resetpassword" element={<ResetPassword />} />
          <Route path="*" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
