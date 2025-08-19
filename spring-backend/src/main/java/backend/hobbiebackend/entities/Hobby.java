package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hobbies")
public class Hobby extends BaseEntity {
    private String name;
    private String slogan;
    private String intro;
    private String description;
    private String creator;
    private BigDecimal price;
    private String profileImgUrl;
    private String galleryImgUrl1;
    private String galleryImgUrl2;
    private String galleryImgUrl3;
    private String profileImg_id;
    private String galleryImg1_id;
    private String galleryImg2_id;
    private String galleryImg3_id;
    private String contactInfo;
}
