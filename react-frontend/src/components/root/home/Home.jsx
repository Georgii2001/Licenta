import React from "react";
import BackgroundCover from "./BackgroundCover";
import Presentation from "./Presentation";
import Cover from "./Cover";

const Home = () => {
  return (
    <>
      <main>
        <Presentation />
        <Cover />
        <BackgroundCover />
      </main>
    </>
  );
};

export default Home;
