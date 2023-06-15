import { React, useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import styles from "../../../../css/DiscoverInterests.module.css";
import layout from "../../../../css/Background.module.css";
import BackgroundHome from "../../fragments/background/BackgroundHome";
import { useLocation } from "react-router-dom";
import PostDiscoveryInterestsService from "../../../../api/users/PostDiscoveryInterestsService";

const DiscoverInterests = () => {

  const questions = [
  
    {
      questionText: "What type of accommodation do you prefer?",
      multipleAnswers: false,
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
      multipleAnswers: false,
      answerOptions: [
        { answerText: "I don't think about money", category: "Expensive" },
        { answerText: "Medium", category: "Medium" },
        { answerText: "The cheapest", category: "Cheap" },
        { answerText: "It depends", category: "Other" }
      ],
    },
    {
      questionText: "Where do you prefer to eat?",
      multipleAnswers: false,
      answerOptions: [
        { answerText: "Restaurants", category: "Restaurants" },
        { answerText: "Street Food", category: "Street Food" },
        { answerText: "Cooking", category: "Cooking" },
        { answerText: "Everything", category: "Everything" },
      ],
    },
    {
      questionText: "Where do you like to travel?",
      multipleAnswers: false,
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
      multipleAnswers: false,
      answerOptions: [
        { answerText: "To relax", category: "Relax" },
        { answerText: "For culture", category: "Culture" },
        { answerText: "For fun", category: "Fun" },
        { answerText: "Other", category: "Other" },
      ],
    },
    {
      questionText: "What type of transport do you prefer when you traveling?",
      multipleAnswer: false,
      answerOptions: [
        { answerText: "Walking", category: "Walking" },
        { answerText: "Public transport", category: "Public transport" },
        { answerText: "Taxi/Uber", category: "Taxi" },
        { answerText: "Rent a car", category: "Car" },
      ],
    },
    {
      questionText: "On which continent is your next journey?",
      multipleAnswers: true,
      answerOptions: [
        { answerText: "Europe", category: "Europe" },
        { answerText: "Asia", category: "Asia" },
        { answerText: "Australia", category: "Australia", },
        { answerText: "Africa", category: "Africa" },
        { answerText: "America", category: "America" }
      ],
    },
    {
      questionText: "What kind of trip do you want?",
      multipleAnswers: false,
      answerOptions: [
        { answerText: "City break", category: "City break" },
        { answerText: "Backpacking", category: "Backpacking" },
        { answerText: "Volunteer", category: "Volunteer" },
        { answerText: "Work and travel", category: "Work and travel" },
        { answerText: "Other", category: "Other" }
      ],
    }, 
    {
      questionText: "How do you perceive travel planning??",
      multipleAnswers: false,
      answerOptions: [
        { answerText: "Meticulous planners", category: "Meticulous planners" },
        { answerText: "Flexible adaptors", category: "Flexible adaptors" },
        { answerText: "Impulsive travelers", category: "Impulsive travelers" }
      ]
    },
    {
      questionText: "Choose your passions:",
      multipleAnswers: true,
      answerOptions: [
        { answerText: "Photography", category: "Photography" },
        { answerText: "Music", category: "Music" },
        { answerText: "Cycling", category: "Cycling" },
        { answerText: "Sport", category: "Sport" },
        { answerText: "Museum visiting", category: "Museum visiting" },
        { answerText: "Tech", category: "Tech" }
      ],
    },
  ];

  let key = 1;
  let [loading, setLoading] = useState(true);

  const location = useLocation();
  const id = location.state.id;

  const [saveInterestsList, setSaveInterestsList] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const checkboxResult = useRef("");

  const handleAnswerOptionClick = (answer) => {
    setSaveInterestsList(prevState => ([...prevState, answer]));

    const nextQuestion = currentQuestion + 1;
    setCurrentQuestion(nextQuestion);
    if (nextQuestion === questions.length) {
      setLoading(false);
    }
  };

  const handleCheckboxOption = (answer) => {
    if (checkboxResult.current === "") {
      checkboxResult.current = answer;
    } else if (!checkboxResult.current.includes(answer)) {
      checkboxResult.current = checkboxResult.current + "," + answer;
    } else {
      checkboxResult.current = checkboxResult.current.replace(answer, "").replace(",,", ",");
      if (checkboxResult.current.startsWith(",")) {
        checkboxResult.current = checkboxResult.current.substring(1, checkboxResult.current.length);
      }
      if (checkboxResult.current.endsWith(",")) {
        checkboxResult.current = checkboxResult.current.substring(0, checkboxResult.current.length - 1);
      }
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
      <main className={layout.main_area}>
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
              {currentQuestion !== questions.length && (
                <div>
                  {!questions[currentQuestion].multipleAnswers ?
                    <div>
                      {questions[currentQuestion].answerOptions.map(answerOption => (
                        <button
                          key={key++}
                          className={styles.test_button}
                          onClick={() => handleAnswerOptionClick(answerOption.category)}
                        >
                          {answerOption.answerText}
                        </button>
                      ))}
                    </div>
                    :
                    <div>
                      {questions[currentQuestion].answerOptions.map(answerOption => (
                        <div className={styles.test_checkbox_container} key={key++}>
                          <input className={styles.test_checkbox} type="checkbox"
                            onClick={() => handleCheckboxOption(answerOption.category)}
                          />
                          <span className={styles.test_checkbox_label}>{answerOption.answerText}</span>
                        </div>
                      ))}
                      <button
                        key={key++}
                        className={styles.finish_button}
                        onClick={() => handleAnswerOptionClick(checkboxResult.current)}
                      >
                        Finish
                      </button>
                    </div>
                  }
                </div>
              )}
            </section>
          </div>
        )}
      </main>
      <BackgroundHome />
    </>
  );
};

export default DiscoverInterests;
