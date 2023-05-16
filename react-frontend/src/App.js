import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/root/fragments/header/Header";
import Home from "./components/root/home/Home";
import SignUp from "./components/root/users/signUp/SignUp";
import Login from "./components/root/users/login/Login";
import UserHome from "./components/root/users/UsersHome";
import AccountUser from "./components/root/users/user/accountUser/AccountUser";
import AccountBusiness from "./components/root/users/userDetails/AccountBusiness/AccountBusiness";
import DiscoverInterests from "./components/root/users/discoverInterests/DiscoverInterests";
import CreateOffer from "./components/root/users/userDetails/CreateOffer";
import MyHobbies from "./components/root/users/user/MyHobbies";
import ProtectedRoutesGuest from "./components/protectedRoutes/ProtectedRoutesGuest";
import ProtectedRoutesUser from "./components/protectedRoutes/ProtectedRoutesUser";
import ProtectedRoutesBusiness from "./components/protectedRoutes/ProtectedRoutesBusiness";
import EditUserProfile from "./components/root/users/user/accountUser/EditUserProfile";
import EditBusinessProfile from "./components/root/users/userDetails/AccountBusiness/EditBusinessProfile";
import UpdateOffer from "./components/root/users/userDetails/UpdateOffer";
import PasswordChange from "./components/root/users/login/forgottenPassword/PasswordChange";
import SetUpNewPassword from "./components/root/users/login/forgottenPassword/SetUpNewPassword";
import UserDetails from "./components/root/users/userDetails/UserDetails";

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Routes>
          <Route element={<ProtectedRoutesGuest />}>
            <Route path="/" element={<Home />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
            <Route path="/change-password" element={<PasswordChange />} />
            <Route path="/password/:id" element={<SetUpNewPassword />} />
          </Route>
          
          <Route element={<ProtectedRoutesUser />}>
            <Route path="/edit-profile" element={<EditUserProfile />} />
            <Route path="/user-home" element={<UserHome />} />
            <Route path="/account-user" element={<AccountUser />} />
            <Route path="/discover-interests" element={<DiscoverInterests />} />
            <Route path="/my-hobbies" element={<MyHobbies />} />
            <Route path="/user-details" element={<UserDetails />} />
          </Route>

          <Route element={<ProtectedRoutesBusiness />}>
            <Route
              path="/edit-business-profile"
              element={<EditBusinessProfile />}
            />
            <Route path="/edit-offer" element={<UpdateOffer />} />
            <Route path="/business-home" element={<UserHome />} />
            <Route path="/account-business" element={<AccountBusiness />} />
            <Route path="/create-offer" element={<CreateOffer />} />
            <Route path="/account-business" element={<AccountBusiness />} />
            <Route path="/offer" element={<UserDetails />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
