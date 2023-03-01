package backend.hobbiebackend.model.entities;

import backend.hobbiebackend.model.entities.enums.CategoryNameEnum;
import backend.hobbiebackend.model.entities.enums.LocationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_results")
public class Test extends BaseEntity {
    private String username;
    @Column(name = "category_one")
    private String categoryOne;
    @Column(name = "category_two")
    private String categoryTwo;
    @Column(name = "category_three")
    private String categoryThree;
    @Column(name = "category_four")
    private String categoryFour;
    @Column(name = "category_five")
    private String categoryFive;
    @Column(name = "category_six")
    private String categorySix;
    @Column(name = "category_seven")
    private String categorySeven;
    private String location;
}
