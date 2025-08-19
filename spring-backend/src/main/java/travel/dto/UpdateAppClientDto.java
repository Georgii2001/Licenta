package travel.dto;

import travel.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppClientDto {

    String email;
    private String displayName;
    private GenderEnum gender;
    private String password;
    String description;
}
