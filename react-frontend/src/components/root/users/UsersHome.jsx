import React, { useState, useEffect } from "react";
import BackgroundHome from "../fragments/background/BackgroundHome";
import HomeDataService from "../../../api/hobby/HomeDataService";
import AuthenticationService from "../../../api/authentication/AuthenticationService";
import { Link, useNavigate } from "react-router-dom";
import TinderCard from "react-tinder-card";
import styles from "../../../css/UserHome.module.css";

const UserHome = () => {
  const navigate = useNavigate();
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
  const isBusinessLoggedIn = AuthenticationService.isBusinessLoggedIn();

  const [state, setState] = useState([]);
  const [welcomeDiv, setWelcomeDiv] = useState({ showDiv: false });
  const [likePressed, setLikePressed] = useState(false);
  const [dislikePressed, setDislikePressed] = useState(false);
  const [cards, setCards] = useState([]);
  const [page, setPage] = useState(0);
  const [index, setIndex] = useState(0);

  const handleSort = (value) => (event) => {
    event.preventDefault();

    if (isUserLoggedIn) {
      navigate(`/hobbie/${value}`, { state: { id: value } });
    } else if (isBusinessLoggedIn) {
      navigate(`/offer/${value}`, { state: { id: value } });
    }

    setPage(page + 1);
  };

  const swiped = (direction, id) => {
    console.log(`Removing card ${id} by swiping ${direction}`);

    // Set the pressed button state to true
    if (direction === 'right') {
      setLikePressed(true);
      setDislikePressed(false);
    } else {
      setLikePressed(false);
      setDislikePressed(true);
    }

    // Remove the card from the list
    setState(prevState => prevState.filter(hobby => hobby.id !== id));
  };

  const outOfFrame = (id) => {
    console.log(id + " left the screen");
  };

  useEffect(() => {
    let unmounted = false;

    const fetchData = async () => {
      try {
        const response = await HomeDataService(page);
        if (!unmounted) {
          setState(response.data);
          setWelcomeDiv({ showDiv: false });
          console.log(response);
        }
        if (!response.data.length) {
          setWelcomeDiv({ showDiv: true });
        }
      } catch (error) {
        console.error(error);
      }

      // Find the first card that hasn't been swiped yet
      const index = state.findIndex(hobby => !cards.includes(hobby.id));
      setIndex(index);

      // Log the pressed button
      if (likePressed) {
        console.log(`Like button pressed for card ${state[index].id}`);
      } else if (dislikePressed) {
        console.log(`Dislike button pressed for card ${state[index].id}`);
      }

      // Reset the button state
      setLikePressed(false);
      setDislikePressed(false);
    };

    fetchData();
    return () => {
      unmounted = true;
    };
  }, [isBusinessLoggedIn, isUserLoggedIn, cards, likePressed, dislikePressed, state]);

  return (
    <>
      <BackgroundHome />
      <main className={styles.users_main}>
        <section className={styles.users_container_home}>
          {state.length !== 0 && (
            <>
             <div className={styles.matchButtons}>
                <button
                  className={styles.rightButton}
                  onClick={() => swiped('right', state[index].id)}
                >
                  Like
                </button>

                <button
                  className={styles.leftButton}
                  onClick={() => swiped('left', state[index].id)}
                >
                  Dislike
                </button>
              </div>
            <div className={styles.tinderCards}>
              {state.map((user) => (
                <TinderCard
                  key={user.id}
                  className={styles.swipe}
                  onSwipe={(dir) => swiped(dir, user.id)}
                  onCardLeftScreen={() => outOfFrame(user.id)}
                  preventSwipe={["up", "down"]}
                >
                  <div className={styles.card}>
                    <Link
                      to="#"
                      onClick={handleSort(user.id)}
                      id={user.id}
                    >
                      <section className={styles.card_image_container}>
                        <img
                          src={`data:image/png;base64,${user.avatarFile}`}
                          alt="user"
                        />
                      </section>
                      <section className={styles.card_content}>
                        <p className={styles.card_title}>{user.username}</p>
                        <div className={styles.card_info}>
                          {/* <p className={styles.card_price}>{user.gender}</p> */}
                          <p className={styles.card_infoDetails}>Tap to see more</p>
                        </div>
                      </section>
                    </Link>
                  </div>
                </TinderCard>
              ))}
            </div>
            </>
          )}

          {welcomeDiv.showDiv && (
            <div>
              <article className={styles.introduction_home}>
                <div className={styles.intro_text}>
                  {isUserLoggedIn && (
                    <div>
                      <p className={styles.intro}>
                        You have no parteners to be matched with.
                      </p>
                      <div className={styles.buttons}>
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
                      <div className={styles.buttons}>
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
        </section>
      </main>
    </>
  );
};

export default UserHome;
