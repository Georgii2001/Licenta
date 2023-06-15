import React from "react";
import Background from "../../fragments/background/Background";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SignUpAppClientService from "../../../../api/signup/SignUpAppClientService";
import styles from "../../../../css/Forms.module.css";
import LoadingDotsDark from "../login/animation/LoadingDotsDark";

const SignUp = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [checked, setCheckBoxChecked] = useState("other");
  const [error, setError] = useState(false);
  const [avatarUrl, setAvatarUrl] = useState("");
  const [info, setInfo] = useState({
    username: "",
    gender: "OTHER",
    email: "",
    password: "",
    repeatpassword: "",
    avatar: null,
  });

  const [errors, setErrors] = useState({});

  const avatarInput = React.useRef(null);
  
  const handleClick = event => {
    avatarInput.current.click();
  };

  const validate = () => {
    const errors = {};

    if (!info.username) {
      errors.username = "Required";
    } else if (info.username.length < 5) {
      errors.username = "Minimum 5 char";
    }

    if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(info.email)) {
      errors.email = "Invalid email address";
    }

    if (!info.password) {
      errors.password = "Required";
    }
    if (!info.repeatpassword) {
      errors.repeatpassword = "Repeate";
    }
    if (info.password !== info.repeatpassword) {
      errors.repeatpassword = "Passwords don't match";
    }

    return errors;
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    let errors = validate(info);
    setErrors(errors);

    if (Object.keys(errors).length === 0) {
      console.log(info);
      setLoading(true);

      const formData = new FormData();
      formData.append("username", info.username);
      formData.append("gender", info.gender);
      formData.append("email", info.email);
      formData.append("password", info.password);
      formData.append("repeatpassword", info.repeatpassword);
      formData.append("avatar", info.avatar);
      await SignUpAppClientService(formData)
        .then((response) => {
          if (response.status === 201) {
            navigate("/login");
          }
        })
        .catch((err) => {
          setError(true);
          setLoading(false);
        });
    } else {
      console.log(errors);
    }
  };


  return (
    <>
      <main className={styles.form_style}>
        <h2>Sign up</h2>
        {error && (
          <div className={styles.errors}>
            This username or email already exist.
          </div>
        )}

        <form className={styles.signup_form} onSubmit={submitHandler}>
          <section className={styles.form_field}>
            <input
              id="name"
              onChange={(e) => setInfo({ ...info, username: e.target.value })}
              type="text"
              name="name"
            />
            <label htmlFor="name" className={styles.label_name}>
              <span className={styles.content_name}>Username</span>
              {errors.username && (
                <small className={styles.errors}>{errors.username}</small>
              )}
            </label>
          </section>

          <section className={styles.form_field}>
            <label id="gender" className={styles.label_name}>
              <span className={styles.content_name}>Gender</span>
            </label>
          </section>

          <section className={styles.checkbox_choice_section}>
            <input
              className={styles.sign_up_checkbox}
              onClick={() => setCheckBoxChecked("male")}
              onChange={(e) => setInfo({ ...info, gender: "MALE" })}
              checked={checked === "male"}
              type="checkbox"
              id="checkbox1"
            />
            <label className={styles.checkbox} htmlFor="checkbox1">
              Male
            </label>
            <input
              className={styles.sign_up_checkbox}
              onClick={() => setCheckBoxChecked("female")}
              onChange={(e) => setInfo({ ...info, gender: "FEMALE" })}
              checked={checked === "female"}
              type="checkbox"
              id="checkbox2"
            />
            <label className={styles.checkbox} htmlFor="checkbox2">
              Female
            </label>
            <input
              className={styles.sign_up_checkbox}
              onClick={() => setCheckBoxChecked("other")}
              onChange={(e) => setInfo({ ...info, gender: "OTHER" })}
              checked={checked === "other"}
              type="checkbox"
              id="checkbox3"
            />
            <label className={styles.checkbox} htmlFor="checkbox3">
              Other
            </label>
          </section>

          <section className={styles.form_field}>
            <input
              id="email"
              name="email"
              type="email"
              onChange={(e) => setInfo({ ...info, email: e.target.value })}
            />
            <label htmlFor="email" className={styles.label_name}>
              <span className={styles.content_name}>Email</span>
              {errors.email && (
                <small className={styles.errors}>{errors.email}</small>
              )}
            </label>
          </section>

          <section className={styles.form_field}>
            <input
              id="password"
              name="password"
              type="password"
              onChange={(e) => setInfo({ ...info, password: e.target.value })}
            />

            <label htmlFor="password" className={styles.label_name}>
              <span className={styles.content_name}>Password</span>
              {errors.password && (
                <small className={styles.errors}>{errors.password}</small>
              )}
            </label>
          </section>

          <section className={styles.form_field}>
            <input
              id="repassword"
              name="repassword"
              type="password"
              onChange={(e) =>
                setInfo({ ...info, repeatpassword: e.target.value })
              }
            />

            <label htmlFor="repassword" className={styles.label_name}>
              {!errors.repeatpassword && (
                <span className={styles.content_name}>Confirm Password</span>
              )}
              {errors.repeatpassword && (
                <small className={styles.errors}>{errors.repeatpassword}</small>
              )}
            </label>
          </section>

          <section className={styles.form_field}>
            <div className={styles.upload_wrapper}>
              <span>{info.avatar ?
                <img src={avatarUrl} className={styles.uploaded_avatar} />
                : <span className={styles.file_name}>No photo chosen</span>}
              </span>
              <div className={styles.upload_button} onClick={handleClick}>
                Choose a profile photo
              </div>
              <input
                  id="avatar"
                  name="avatar"
                  type="file"
                  ref={avatarInput}
                  style={{ display: "none" }}
                  onChange={(e) => {
                    const file = e.target.files[0];
                    setInfo({ ...info, avatar: file });

                    const reader = new FileReader();
                    reader.onload = () => {
                      setAvatarUrl(reader.result);
                    };
                    reader.readAsDataURL(file);
                  }}
                />
            </div>
          </section>
          <section className={styles.form_field}>
            {loading && <LoadingDotsDark />}

            {!loading && (
              <button id="button" type="submit" className={styles.sign_up_button}>
                Sign up
              </button>
            )}
          </section>
        </form>
      </main>
      <Background />
    </>
  );
};

export default SignUp;
