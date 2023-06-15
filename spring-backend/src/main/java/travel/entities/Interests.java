package travel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "interest_code", nullable = false)
    int interestCode;
}


