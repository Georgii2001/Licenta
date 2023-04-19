import React from "react";
import styles from "../../../../../../css/Account.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import { useState, useLayoutEffect } from "react";
import Popup from 'reactjs-popup';
import UserInterestsItem from "./elements/UserInterestsItem";
import InterestsService from "../../../../../../api/users/InterestsService";
import UserInterestsService from "../../../../../../api/users/UserInterestsService";
import RemoveUserInterestService from "../../../../../../api/users/RemoveUserInterestService";
import UpdateUserInterestsService from "../../../../../../api/users/UpdateUserInterestsService";

const UserInterests = () => {

  const [userInterests, setUserInterests] = useState([]);
  const [unassignedInterests, setUnassignedInterests] = useState([]);
  const [saveInterestsList, setSaveInterestsList] = useState([]);

  const handleRemoveInterest = async (id) => {
    await RemoveUserInterestService(id);

    UserInterestsService().then((getResponse) => {
      setUserInterests(getResponse.data);
    });

    InterestsService().then((response) => {
      setUnassignedInterests(response.data);
    });

  }

  useLayoutEffect(() => {
    UserInterestsService().then((response) => {
      setUserInterests(response.data);
    });

    InterestsService().then((response) => {
      setUnassignedInterests(response.data);
    });
  }, []);

  const addUserInterests = (id) => {
    setSaveInterestsList(prevState => ([...prevState, id]));
  }

  const removeUserInterests = (id) => {
    setSaveInterestsList(saveInterestsList.filter(interest => interest !== id));
  }

  const saveUserInterests = async () => {
    await UpdateUserInterestsService(saveInterestsList);

    UserInterestsService().then((getResponse) => {
      setUserInterests(getResponse.data);
    });

    InterestsService().then((response) => {
      setUnassignedInterests(response.data);
    });

    setSaveInterestsList([]);
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
              <div className={styles.interest_item} key={interest.id}>
                <span >{interest.interest}</span>
                <span className={styles.close} onClick={() => handleRemoveInterest(interest.id)}>&times;</span>
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
                        <UserInterestsItem saveUserInterests={addUserInterests} removeUserInterests={removeUserInterests} id={unassignedInterest.id} interest={unassignedInterest.interest} />
                      ))}
                    </div>
                    <button className={styles.popup_button} onClick={() => { saveUserInterests(); close(); }}>Save</button>
                  </div>
                </div>
              )}
            </Popup>
            : null
          }
        </div>
      </div>
    </>
  );
};

export default UserInterests;
