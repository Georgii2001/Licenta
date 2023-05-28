import React from "react";
import styles from "../../../../../css/Account.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import { useState, useLayoutEffect } from "react";
import { useNavigate } from "react-router-dom";
import Popup from 'reactjs-popup';
import UserInterestsItem from "./elements/UserInterestsItem";
import InterestsService from "../../../../../api/users/InterestsService";
import AuthenticationService from "../../../../../api/authentication/AuthenticationService";
import DeleteUserInterestService from "../../../../../api/users/DeleteUserInterestService";
import PostUserInterestsService from "../../../../../api/users/PostUserInterestsService";

const UserInterests = ({ id, userInterests, refreshUserData }) => {

  const navigate = useNavigate();
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
  const [unassignedInterests, setUnassignedInterests] = useState([]);
  const [saveInterestsList, setSaveInterestsList] = useState([]);
  
  useLayoutEffect(() => {
    refreshUnassignedInterests();
    
  }, []);

  const handleRemoveInterest = async (interest) => {
    await DeleteUserInterestService(interest);

    refreshUnassignedInterests();
    refreshUserData();
  }

  const saveUserInterests = async () => {
    
    await PostUserInterestsService(saveInterestsList);
    
    refreshUnassignedInterests();
    refreshUserData();
    setSaveInterestsList([]);
  }

  const refreshUnassignedInterests = () =>{
    InterestsService().then((response) => {
      setUnassignedInterests(response.data);
    });
  }

  const addUserInterests = (interestNames) => {
    setSaveInterestsList(prevState => ([...prevState, interestNames]));
  }

  const removeUserInterests = (id) => {
    setSaveInterestsList(saveInterestsList.filter(interest => interest !== id));
  }

  const discoverInterests = () => {
    if (isUserLoggedIn) {
      navigate(`/discover-interests`, {
        state: { id: id },
      });
    }
  }

  return (
    <>
      <div className={styles.account_content}>
        <span className={styles.account_title}>
          <b>Interests</b>
        </span>
        <hr className={styles.account_hr}></hr>

        <div className={styles.interest_input_container}>
          {userInterests && userInterests.length ?
            userInterests.map((interest) => (
              <div key={interest} className={styles.interest_item}>
                <span >{interest}</span>
                <span className={styles.close} onClick={() => handleRemoveInterest(interest)}>&times;</span>
              </div>
            ))
            : <div>Add your interests to start jorney</div>
          }
          {unassignedInterests && unassignedInterests.length ?
            <Popup trigger={<button className={styles.add_interest_button}>Add interests</button>} position="right center">
              {close => (
                <div className={styles.form_popup}>
                  <div>
                    <div className={styles.popup_header}>Choose your interests</div>
                    <div className={styles.popup_body}>
                      {unassignedInterests.map((unassignedInterest) => (
                        <UserInterestsItem key={unassignedInterest} saveUserInterests={addUserInterests} removeUserInterests={removeUserInterests} interest={unassignedInterest} />
                      ))}
                    </div>
                    <button className={styles.popup_button} onClick={() => { saveUserInterests(); close(); }}>Save</button>
                  </div>
                </div>
              )}
            </Popup>
            : null
          }
          <button className={styles.add_interest_button} onClick={() => { discoverInterests(); }}>Discover interests</button>
        </div>
      </div>
    </>
  );
};

export default UserInterests;
