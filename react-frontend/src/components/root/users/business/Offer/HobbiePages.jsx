import React from "react";
import styles from "../../../../../css/Hobbie.module.css";
import AuthenticationService from "../../../../../api/authentication/AuthenticationService";
import { useState, useLayoutEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import gallery_styles from "../../../../../css/Gallery.module.css";
import DeleteHobbyService from "../../../../../api/hobby/DeleteHobbyService";
import SaveHobbyService from "../../../../../api/hobby/SaveHobbyService";
import RemoveHobbyService from "../../../../../api/hobby/RemoveHobbyService";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import UserByIdDataService from "../../../../../api/users/UserByIdDataService";

const HobbiePages = () => {
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
  const isBusinessLoggedIn = AuthenticationService.isBusinessLoggedIn();
  let navigate = useNavigate();
  const [saved, setSaved] = useState(false);
  let params = useParams();

  const id = params.id;
  const [user, setUser] = useState([]);

  const [currentPage, setCurrentPage] = useState("Media");

  const [hobby, setHobby] = useState({
    name: "",
    slogan: "",
    intro: "",
    description: "",
    price: "",
    profileImgUrl: "",
    galleryImgUrl1: "",
    galleryImgUrl2: "",
    galleryImgUrl3: "",
    contactInfo: "",
  });

  const [hobbieDiv, setHobbieDiv] = useState({ showDiv: false });

  const handleDelete = (hobby) => (event) => {
    event.preventDefault();

    confirmAlert({
      title: "Delete Offer",
      message: "Are you sure you want to delete this offer?",
      buttons: [
        {
          label: "Yes",
          onClick: async () => {
            const response = await DeleteHobbyService(hobby.id);

            if (response.data !== null) {
              window.location.href = "/business-home";
            }
          },
        },
        {
          label: "No",
        },
      ],
    });
  };

  const handleEdit = (hobby) => (event) => {
    event.preventDefault();
    let path = "/edit-offer";
    navigate(path, {
      state: {
        id: hobby.id,
        name: hobby.name,
        slogan: hobby.slogan,
        intro: hobby.intro,
        description: hobby.description,
        price: hobby.price,
        contactInfo: hobby.contactInfo,
        profileImgUrl: hobby.profileImgUrl,
        galleryImgUrl1: hobby.galleryImgUrl1,
        galleryImgUrl2: hobby.galleryImgUrl2,
        galleryImgUrl3: hobby.galleryImgUrl3,
      },
    });
  };

  const handleSave = (id) => (event) => {
    event.preventDefault();

    if (!saved) {
      SaveHobbyService(id).then((response) => {
        setSaved(true);
        console.log(saved);
      });
    } else {
      RemoveHobbyService(id).then((response) => {
        setSaved(false);
        console.log(saved);
      });
    }
  };

  useLayoutEffect(() => {
    let unmounted = false;

    UserByIdDataService(id).then((response) => {
      if (!unmounted) {
        setUser(response.data);
      }
    });
    return () => {
      unmounted = true;
    };
  }, []);

  const changePage = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      <section className={styles.hobbie_container}>
        {hobby !== undefined && (
          <div className={styles.hobbie_content}>
            <div className={styles.username}>
              {user.username}
            </div>
            <div className={styles.hobbie_content_body_small}>

              <article className={styles.hobbie_pages_horizontal}>
                <span
                  onClick={() => changePage("Media")}
                  className={
                    currentPage === "Media"
                      ? styles.page_title_active
                      : styles.page_title
                  }
                >
                  Media
                </span>
                <span
                  onClick={() => changePage("Description")}
                  className={
                    currentPage === "Description"
                      ? styles.page_title_active
                      : styles.page_title
                  }
                >
                  Description
                </span>
                <span
                  onClick={() => changePage("Contacts")}
                  className={
                    currentPage === "Contacts"
                      ? styles.page_title_active
                      : styles.page_title
                  }
                >
                  Contacts
                </span>
              </article>

              <section className={styles.hobbie_lable}>
                <div>
                  {" "}
                  <span className={styles.hobbie_title}>
                    <b>{hobby.name}</b>
                  </span>
                  <h4 className={styles.slogan}> {hobby.slogan} </h4>
                </div>

                {currentPage === "Media" && <p>{hobby.intro}</p>}

                {currentPage === "Description" && (
                  <section className={gallery_styles.gallery}>
                    <div className={gallery_styles.row}>
                      <article className={gallery_styles.column}>
                        <img
                          className={gallery_styles.img}
                          src={hobby.profileImgUrl}
                          alt="gallery"
                        />
                        <img
                          className={gallery_styles.img}
                          src={hobby.galleryImgUrl1}
                          alt="gallery"
                        />
                      </article>

                      <article className={gallery_styles.column}>
                        <img
                          className={gallery_styles.img}
                          src={hobby.galleryImgUrl2}
                          alt="gallery"
                        />
                        <img
                          className={gallery_styles.img}
                          src={hobby.galleryImgUrl3}
                          alt="gallery"
                        />
                      </article>
                    </div>
                  </section>
                )}

                {currentPage === "Contacts" && <p> {hobby.contactInfo} </p>}

                {currentPage !== "gallery" && (
                  <article className={styles.buttons}>
                    {isBusinessLoggedIn && (
                      <div>
                        <Link
                          to="#"
                          onClick={handleEdit(hobby)}
                          className={styles.btn}
                        >
                          Edit{" "}
                        </Link>
                        <Link
                          to="#"
                          onClick={handleDelete(hobby)}
                          className={styles.btn}
                        >
                          Delete{" "}
                        </Link>
                      </div>
                    )}
                    {isUserLoggedIn && (
                      <div onClick={handleSave(hobby.id)}>
                        {saved ? (
                          <span className={styles.btn}>Remove</span>
                        ) : (
                          <span className={styles.btn}>Save</span>
                        )}
                      </div>
                    )}
                  </article>
                )}
              </section>
            </div>
          </div>
        )}

        {hobbieDiv.showDiv && (
          <div className={styles.error_message}>
            <article className={styles.error_text}>
              <p> This hobby does not exist.</p>
            </article>
          </div>
        )}
      </section>
    </>
  );
};

export default HobbiePages;
