package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_results")
public class Test extends BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
