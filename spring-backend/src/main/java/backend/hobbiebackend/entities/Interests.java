package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "interests")
@NoArgsConstructor
@AllArgsConstructor
public class Interests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id", nullable = false, updatable = false)
    Integer interestId;

    @Column(name = "interest_name", nullable = false)
    String interestName;

    @Column(name = "interest_description", nullable = false)
    String interestDescription;

    @Column(name = "sys_creation_date", insertable = false)
    Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    Timestamp updateDate;
}


