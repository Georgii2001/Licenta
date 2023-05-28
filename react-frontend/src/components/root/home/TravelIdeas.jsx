import React from 'react';
import styles from "../../../css/TravelIdeas.module.css";

const TravelIdeas = () => {
    return (

        <div className={styles.travelideas}>
                <iframe
                    src="https://www.tripadvisor.com/TravelersChoice-Destinations"
                    title="Travelers' Choice Destinations"
                    width="100%"    
                    height="620px"
                />
            </div>
    );
};

export default TravelIdeas;