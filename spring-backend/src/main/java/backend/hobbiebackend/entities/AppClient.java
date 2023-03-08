package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_clients")
public class AppClient extends UserEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

    private String fullName;
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_results_id", referencedColumnName = "id")
    private Test testResults;
    private Set<Hobby> hobby_matches;
    private List<Hobby> saved_hobbies;

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Hobby> getHobby_matches() {
        return hobby_matches;
    }

    public void setHobby_matches(Set<Hobby> hobby_matches) {
        this.hobby_matches = hobby_matches;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Hobby> getSaved_hobbies() {
        return saved_hobbies;
    }

    public void setSaved_hobbies(List<Hobby> saved_hobbies) {
        this.saved_hobbies = saved_hobbies;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
