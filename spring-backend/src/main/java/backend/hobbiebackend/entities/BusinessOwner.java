package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_owners")
public class BusinessOwner extends UserEntity {
    private String businessName;
    private String address;
}
