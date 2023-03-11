package backend.hobbiebackend.dto;

import backend.hobbiebackend.enums.CategoryNameEnum;
import backend.hobbiebackend.enums.LocationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HobbyInfoUpdateDto {
    private Integer id;
    private String name;
    private String slogan;
    private String intro;
    private String description;
    private CategoryNameEnum category;
    private String creator;
    private BigDecimal price;
    private LocationEnum location;
    private String contactInfo;
    private String profileImgUrl;
    private String galleryImgUrl1;
    private String galleryImgUrl2;
    private String galleryImgUrl3;
    private String profileImg_id;
    private String galleryImg1_id;
    private String galleryImg2_id;
    private String galleryImg3_id;
}
