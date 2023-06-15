import React, { useState, useEffect, useRef } from "react";
import BackgroundHome from "../fragments/background/BackgroundHome";
import GetPossibleMatchesService from "../../../api/users/myMatches/GetPossibleMatchesService";
import PostAddToMatches from "../../../api/users/myMatches/PostAddToMatches";
import AuthenticationService from "../../../api/authentication/AuthenticationService";
import { Link, useNavigate } from "react-router-dom";
import TinderCard from "react-tinder-card";
import styles from "../../../css/UserHome.module.css";
import { NotificationContainer, NotificationManager } from 'react-notifications';

import 'react-notifications/lib/notifications.css';

const UserHome = () => {
  const navigate = useNavigate();
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

  const [possibleMatches, setPossibleMatches] = useState([]);
  const [currentIndex, setCurrentIndex] = useState([]);
  const currentIndexRef = useRef(currentIndex);
  const canSwipe = currentIndex >= 0;
  const [childRefs, setChildRefs] = useState([]);
  const currentUserId = useRef("");

  const handleDetails = (value) => (event) => {
    event.preventDefault();

    if (isUserLoggedIn) {
      navigate(`/user-details`, {
        state: { id: value },
      });
    }
  };

  const updateCurrentIndex = (val) => {
    setCurrentIndex(val);
    currentIndexRef.current = val;
  }

  const swiped = (direction, userId, index) => {
    sendInfoToBe(direction, userId);
    updateCurrentIndex(index - 1);
  }

  const swipe = async (direction) => {
    if (canSwipe && currentIndex < possibleMatches.length) {
      await childRefs[currentIndex].current.swipe(direction);
    }
  }

  const sendInfoToBe = (direction, userId) => {
    if (userId !== currentUserId.current) {
      if (direction === 'right') {
        currentUserId.current = userId;
        PostAddToMatches(userId, "MATCHED").then((response) => {
          if (response.data.messageStatus === "newMessageSent") {
            NotificationManager.info("Check \"My Matches\" page!", "You have one new match!", 1000);
          }
        })
      } else {
        currentUserId.current = userId;
        PostAddToMatches(userId, "NOT_MATCHED");
      }
    }
  }

  useEffect(() => {
    let unmounted = false;

    const fetchData = async () => {
      try {
        const response = await GetPossibleMatchesService();
        if (!unmounted) {
          setPossibleMatches(response.data);
          setCurrentIndex(response.data.length - 1);
          setChildRefs(() =>
            Array(response.data.length)
              .fill(0)
              .map((i) => React.createRef()),
            []);
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();

    return () => {
      unmounted = true;
    };
  }, [isUserLoggedIn]);

  return (
    <>
      <BackgroundHome />
      {possibleMatches.length !== 0 && (
        <div className={styles.card_container}>
          {possibleMatches.map((user, index) => (
            <TinderCard
              key={user.id}
              ref={childRefs[index]}
              className={styles.swipe}
              onSwipe={(dir) => swiped(dir, user.id, index)}
              preventSwipe={[`up`, `down`]}
            >
              <div className={styles.card}>
                <div className={styles.card_image_container} style={{ backgroundImage: "url(data:image/png;base64," + user.avatarFile + ")" }}>
                </div>
                <div className={styles.card_title}>{user.username}</div>
                <div className={styles.card_matches}>You have <b>{user.userMatchCount}</b> matches</div>
                <div className={styles.card_description}>{user.gender}</div>
                <Link to="#" onClick={handleDetails(user.id)} id={user.id}>
                  <div className={styles.card_user_details}>Tap to see more</div>
                </Link>
              </div>
            </TinderCard>
          ))}
        </div>
      )}
      <div className={styles.card_container}>
        <div className={styles.final_card_no_swipe}>
          <div className={styles.final_card}>
            <div className={styles.final_card_image_container}>You have no matches. Try later.</div>
            <div className={styles.final_card_title}>Name</div>
            <div className={styles.final_card_matches}>You have <b>x</b> matches</div>
            <div className={styles.final_card_description}>Gender</div>
            <div className={styles.final_card_user_details}><br></br></div>
          </div>
        </div>
      </div >


      <div className={styles.buttons_container}>
        <div className={styles.buttons}>
          <button className={styles.button_pass} style={{ backgroundColor: !canSwipe && '#c3c4d3' }} onClick={() => swipe('left')}>Pass</button>
          <button className={styles.button_go} style={{ backgroundColor: !canSwipe && '#c3c4d3' }} onClick={() => swipe('right')}>Let's go!</button>
        </div>
      </div>
      <NotificationContainer />
    </>
  );
};

export default UserHome;
