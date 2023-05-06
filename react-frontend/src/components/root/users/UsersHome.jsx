import React, { useState, useEffect, useMemo, useRef } from "react";
import BackgroundHome from "../fragments/background/BackgroundHome";
import GetPossibleMatchesService from "../../../api/hobby/GetPossibleMatchesService";
import AuthenticationService from "../../../api/authentication/AuthenticationService";
import { Link, useNavigate } from "react-router-dom";
import TinderCard from "react-tinder-card";
import styles from "../../../css/UserHome.module.css";

const UserHome = () => {
  const navigate = useNavigate();
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
  const isBusinessLoggedIn = AuthenticationService.isBusinessLoggedIn();

  const [possibleMatches, setPossibleMatches] = useState([]);
  const [welcomeDiv, setWelcomeDiv] = useState({ showDiv: false });

  const [currentIndex, setCurrentIndex] = useState([]);
  const currentIndexRef = useRef(currentIndex);
  const canSwipe = currentIndex >= 0;
  const [childRefs, setChildRefs] = useState([]);

  const handleSort = (value) => (event) => {
    event.preventDefault();

    if (isUserLoggedIn) {
      navigate(`/user-details`, {
        state: { id: value },
      });
    }
  };


  const updateCurrentIndex = (val) => {
    setCurrentIndex(val);
    console.log(currentIndexRef.current);
    currentIndexRef.current = val;
  }

  const swiped = (direction, nameToDelete, index) => {
    console.log('removing: ' + nameToDelete);
    console.log(direction);
    updateCurrentIndex(index - 1);
  }

  const outOfFrame = (name) => {
    console.log(name + ' left the screen!');
  }

  const swipe = async (dir) => {
    if (canSwipe && currentIndex < possibleMatches.length) {
      await childRefs[currentIndex].current.swipe(dir);
    }
  }

  useEffect(() => {
    let unmounted = false;

    const fetchData = async () => {
      try {
        const response = await GetPossibleMatchesService();
        if (!unmounted) {
          setPossibleMatches(response.data);
          setWelcomeDiv({ showDiv: false });
          setCurrentIndex(response.data.length - 1);
          setChildRefs(() =>
            Array(response.data.length)
              .fill(0)
              .map((i) => React.createRef()),
            []);
        }
        if (!response.data.length) {
          setWelcomeDiv({ showDiv: true });
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();

    return () => {
      unmounted = true;
    };
  }, [isBusinessLoggedIn, isUserLoggedIn]);

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
              onCardLeftScreen={() => outOfFrame(user.id)}
              preventSwipe={[`up`, `down`]}
            >
              <div className={styles.card}>
                <div className={styles.card_image_container} style={{ backgroundImage: "url(data:image/png;base64," + user.avatarFile + ")" }}>
                </div>
                <div className={styles.card_title}>{user.username}</div>
                <div className={styles.card_matches}>You have <b>{user.userMatchCount}</b> matches</div>
                <div className={styles.card_description}>{user.gender}</div>
                <Link to="#" onClick={handleSort(user.id)} id={user.id}>
                  <div className={styles.card_user_details}>Tap to see more</div>
                </Link>
              </div>
            </TinderCard>
          ))}
          <div className={styles.final_card_no_swipe}>
            <div className={styles.final_card}>
              <div className={styles.final_card_image_container}>You have no matches. Try later.</div>
              <div className={styles.final_card_title}>Name</div>
              <div className={styles.final_card_matches}>You have <b>x</b> matches</div>
              <div className={styles.final_card_description}>Gender</div>
              <div className={styles.final_card_user_details}><br></br></div>
            </div>
          </div>
        </div>
      )}

      <div className={styles.buttons_container}>
        <div className={styles.buttons}>
          <button className={styles.button_pass} style={{ backgroundColor: !canSwipe && '#c3c4d3' }} onClick={() => swipe('left')}>Pass</button>
          <button className={styles.button_go} style={{ backgroundColor: !canSwipe && '#c3c4d3' }} onClick={() => swipe('right')}>Let's go!</button>
        </div>
      </div>

      {welcomeDiv.showDiv && (
        <div>
          <article className={styles.introduction_home}>
            <div className={styles.intro_text}>
              {isUserLoggedIn && (
                <div>
                  <p className={styles.intro}>
                    You have no parteners to be matched with.
                  </p>
                  <div className={styles.buttuns}>
                    <button className={styles.link}>
                      <Link to="/test" className={styles.btn}>
                        Discover
                      </Link>
                    </button>
                  </div>
                </div>
              )}
              {isBusinessLoggedIn && (
                <div>
                  <p className={styles.intro}>You have no parteners to be matched with.</p>
                  <div className={styles.buttuns}>
                    <button className={styles.link}>
                      <Link to="/create-offer" className={styles.btn}>
                        Create Offer
                      </Link>
                    </button>
                  </div>
                </div>
              )}
            </div>
          </article>
        </div>
      )}
    </>
  );
};

export default UserHome;
