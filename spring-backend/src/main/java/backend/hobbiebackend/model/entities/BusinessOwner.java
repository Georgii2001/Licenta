package backend.hobbiebackend.model.entities;

import backend.hobbiebackend.model.entities.enums.UserRoleEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "business_owners")
public class BusinessOwner extends UserEntity {
    private String businessName;
    private String address;
    private Set<Hobby> hobby_offers;

    public BusinessOwner(String username, String email, UserRoleEnum role, String password, String businessName, String address) {
        super(username, email, null, null, role, password);
        this.businessName = businessName;
        this.address = address;
    }

    public BusinessOwner() {
    }

    @Column(name = "business_name", nullable = false)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Column(nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Set<Hobby> getHobby_offers() {
        return hobby_offers;
    }

    public void setHobby_offers(Set<Hobby> hobby_offers) {
        this.hobby_offers = hobby_offers;
    }
}
