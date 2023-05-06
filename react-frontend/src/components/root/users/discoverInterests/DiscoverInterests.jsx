import {React, useState, useEffect} from "react";
import { Link } from "react-router-dom";
import styles from "../../../../css/DiscoverInterests.module.css";
import layout from "../../../../css/UserHome.module.css";
import BackgroundHome from "../../fragments/background/BackgroundHome";
import { useLocation } from "react-router-dom";
import PostDiscoveryInterestsService from "../../../../api/users/PostDiscoveryInterestsService";

const DiscoverInterests = () => {

  const questions = [
    {
      questionText: "What type of accommodation do you prefer?",
      answerOptions: [
        { answerText: "Hotel", category: "Hotel" },
        { answerText: "Hostel", category: "Hostel" },
        { answerText: "Airbnb", category: "Airbnb" },
        { answerText: "Couchsurfing", category: "Couchsurfing" },
        { answerText: "Resort", category: "Resort" }
      ]
    },
    {
      questionText: "How is your budget?",
      answerOptions: [
        { answerText: "I don't think about money", category: "Expensive" },
        { answerText: "Medium", category: "Medium" },
        { answerText: "The cheapest", category: "Cheap" },
        { answerText: "It depends", category: "Other" }
      ],
    },
    {
      questionText: "Where do you prefer to eat?",
      answerOptions: [
        { answerText: "Restaurants", category: "Restaurants" },
        { answerText: "Street Food", category: "Street Food" },
        { answerText: "Cooking", category: "Cooking" },
        { answerText: "Everything", category: "Everything" },
      ],
    },
    {
      questionText: "Where do you like to travel?",
      answerOptions: [
        { answerText: "Big cities", category: "City" },
        { answerText: "The mountains", category: "Mountains" },
        { answerText: "The sea", category: "Sea" },
        { answerText: "Nature", category: "Nature" },
        { answerText: "Everywhere", category: "Everywhere" },
      ],
    },
    {
      questionText: "Why do you travel?",
      answerOptions: [
        { answerText: "To relax", category: "Relax" },
        { answerText: "For culture", category: "Culture" },
        { answerText: "For fun", category: "Fun" },
        { answerText: "Other", category: "Other" },
      ],
    },
    {
      questionText: "What type of transport do you prefer?",
      answerOptions: [
        { answerText: "Walking", category: "Walking" },
        { answerText: "Public transport", category: "Public transport" },
        { answerText: "Taxi/Uber", category: "Taxi" },
        { answerText: "Rent a car", category: "Car" },
      ],
    },
    {
      questionText: "On which continent is your next journey?",
      answerOptions: [
        { answerText: "Europe", category: "Europe" },
        { answerText: "Asia", category: "Asia" },
        {  answerText: "Australia", category: "Australia",  },
        { answerText: "Africa", category: "Africa" },
        { answerText: "America", category: "America" }
      ],
    },
    {
      questionText: "What kind of trip do you want?",
      answerOptions: [
        { answerText: "City break", category: "City break" },
        { answerText: "Backpacking", category: "Backpacking" },
        { answerText: "Volunteer", category: "Volunteer" },
        { answerText: "Work and travel", category: "Work and travel" },
        { answerText: "Other", category: "Other" }
      ],
    },
  ];

  let key = 1;
  let [loading, setLoading] = useState(true);
  
  const location = useLocation();
  const id = location.state.id;

  const [saveInterestsList, setSaveInterestsList] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);

  const handleAnswerOptionClick = (answer) => {
    setSaveInterestsList(prevState => ([...prevState, answer]));

    const nextQuestion = currentQuestion + 1;
    setCurrentQuestion(nextQuestion);
    if (nextQuestion === questions.length) {
      setLoading(false);
    }
  };

  useEffect(() => {
    const check_uploaded = () => {
      if (!loading) {
        PostDiscoveryInterestsService(id, saveInterestsList);
      }
    };
    check_uploaded();
  }, [loading, id, saveInterestsList]);

  return (
    <>
      <main className={layout.hobbie_main}>
        {currentQuestion === questions.length && (
          <div className={styles.test_form_end}>
            <section className={styles.test_end}>
              Thank you! Please visit your homepage to discover new travel parteners!
              <br></br>
              <button type="submit" className={styles.button}>
                <Link to="/user-home" className={styles.link_home}>
                  Discover
                </Link>
              </button>
            </section>
          </div>
        )}

        {currentQuestion !== questions.length && (
          <div className={styles.test_form}>
            <section className={styles.question_section}>
              {currentQuestion !== questions.length && (
                <div className={styles.question_count}>
                  <span>Question {currentQuestion + 1}</span>
                </div>
              )}

              {currentQuestion !== questions.length && (
                <div className={styles.question_text}>
                  {questions[currentQuestion].questionText}
                </div>
              )}
            </section>
            <section className={styles.answer_section}>
              {currentQuestion !== questions.length &&
                questions[currentQuestion].answerOptions.map((answerOption) => (
                  <button
                    key={key++}
                    className={styles.test_button}
                    onClick={() =>
                      handleAnswerOptionClick(answerOption.category)
                    }
                  >
                    {answerOption.answerText}
                  </button>
                ))}
            </section>
          </div>
        )}
      </main>
      <BackgroundHome />
    </>
  );
};

export default DiscoverInterests;
