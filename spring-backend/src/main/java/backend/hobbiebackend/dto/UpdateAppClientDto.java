package backend.hobbiebackend.dto;

import backend.hobbiebackend.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppClientDto {

    String email;
    private String fullName;
    private GenderEnum gender;
    private String password;
    String description;
}
