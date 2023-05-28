import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/root/fragments/header/Header";
import Home from "./components/root/home/Home";
import SignUp from "./components/root/users/signUp/SignUp";
import Login from "./components/root/users/login/Login";
import UserHome from "./components/root/users/UsersHome";
import AccountUser from "./components/root/users/accountUser/AccountUser";
import DiscoverInterests from "./components/root/users/discoverInterests/DiscoverInterests";
import MyMatchesPage from "./components/root/users/myMatches/MyMatchesPage";
import ProtectedRoutesGuest from "./components/protectedRoutes/ProtectedRoutesGuest";
import ProtectedRoutesUser from "./components/protectedRoutes/ProtectedRoutesUser";
import EditUserProfile from "./components/root/users/accountUser/EditUserProfile";
import PasswordChange from "./components/root/users/login/forgottenPassword/PasswordChange";
import SetUpNewPassword from "./components/root/users/login/forgottenPassword/SetUpNewPassword";
import UserDetails from "./components/root/users/userDetails/UserDetails";
import MatchedUserDetails from "./components/root/users/myMatches/MatchedUserDetails";

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
            <Route path="/my-matches" element={<MyMatchesPage />} />
            <Route path="/user-details" element={<UserDetails />} />
            <Route path="/matched-user-details" element={<MatchedUserDetails />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
