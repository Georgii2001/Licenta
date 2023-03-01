import React from "react";
import { Link } from "react-router-dom";
import styles from "../../../css/Presentation.module.css";

const Presentation = () => {
  return (
    <section className={styles.presentation}>
      <section className={styles.introduction}>
        <article className={styles.intro_text}>
          <h1>The world is just one step from here</h1>
          <p>
          Embark on a journey of discovery and adventure with like-minded travel enthusiasts. 
          Create unforgettable memories as you explore new destinations and share your passions with new people.
          </p>
        </article>

        <article className={styles.buttons}>
          <button className={styles.btn_first} method="POST">
            <Link to="signup" className={styles.btn_first}>
              Sign up
            </Link>
          </button>
        </article>
        
      </section>
    </section>
  );
};

export default Presentation;
