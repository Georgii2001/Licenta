import React from "react";
import styles from "../../../../css/MyMatches.module.css";
import layout from "../../../../css/Background.module.css"
import BackgroundHome from "../../fragments/background/BackgroundHome";
import { useState, useLayoutEffect } from "react";
import { Link } from "react-router-dom";
import MyMatches from "./modules/MyMatches";
import { useMediaQuery } from "beautiful-react-hooks";
import GetMyMatchesService from "../../../../api/users/myMatches/GetMyMatchesService";
import finalAvatarImg from "../../../../img/finalAvatar.jpg";

const MyMatchesPage = () => {

  const [matchedUsers, setMatchedUsers] = useState([]);
  const isColumnBasedSmall = useMediaQuery("(max-width: 385px)");

  useLayoutEffect(() => {
    let unmounted = false;

    GetMyMatchesService().then((response) => {
      if (!unmounted) {
        setMatchedUsers(response.data);
      }
    });
    return () => {
      unmounted = true;
    };
  }, []);

  return (
    <>
      <BackgroundHome />
      <main className={layout.main_area}>
        <section className={layout.main_area_container_home}>
          <section className={styles.matches_container}>
            <div className={styles.matches_content}>
              {matchedUsers && matchedUsers.length ?
                matchedUsers.map(matchedUser =>
                  <MyMatches key={matchedUser.id} avatarFile={matchedUser.avatarFile} username={matchedUser.username} id={matchedUser.id} />
                )
                :
                <div>
                  <div className={styles.no_matches_header}>
                    You have no matches yet
                    <Link to="/user-home">
                        <div className={styles.no_matches_button}>
                            Let`s find friends
                        </div>
                    </Link>
                  </div>
                  <div className={styles.my_matches_item_final}>
                    <div className={styles.image_container_final} style={{ backgroundImage: "url(" + finalAvatarImg + ")" }}>
                    </div>
                    <div className={styles.username_block}>
                      <div className={styles.username_redirect_final}>
                        <div>Name</div>
                      </div>
                    </div>
                    <div className={styles.send_email_button_container}>
                      <div className={styles.send_email_button_final}>
                        {isColumnBasedSmall ? "Mail" : "Send email"}
                      </div>
                    </div>
                  </div>
                  <div className={styles.my_matches_item_final}>
                    <div className={styles.image_container_final} style={{ backgroundImage: "url(" + finalAvatarImg + ")" }}>
                    </div>
                    <div className={styles.username_block}>
                      <div className={styles.username_redirect_final}>
                        <div>Name</div>
                      </div>
                    </div>
                    <div className={styles.send_email_button_container}>
                      <div className={styles.send_email_button_final}>
                        {isColumnBasedSmall ? "Mail" : "Send email"}
                      </div>
                    </div>
                  </div>
                  <div className={styles.my_matches_item_final}>
                    <div className={styles.image_container_final} style={{ backgroundImage: "url(" + finalAvatarImg + ")" }}>
                    </div>
                    <div className={styles.username_block}>
                      <div className={styles.username_redirect_final}>
                        <div>Name</div>
                      </div>
                    </div>
                    <div className={styles.send_email_button_container}>
                      <div className={styles.send_email_button_final}>
                        {isColumnBasedSmall ? "Mail" : "Send email"}
                      </div>
                    </div>
                  </div>
                </div>
              }
            </div>
          </section>
        </section>
      </main>
    </>
  );
};

export default MyMatchesPage;
