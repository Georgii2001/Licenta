package backend.hobbiebackend.entities;

import backend.hobbiebackend.entities.enums.CategoryNameEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 3721604704531677300L;

    private CategoryNameEnum name;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}