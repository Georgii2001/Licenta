package backend.hobbiebackend.dto;

import backend.hobbiebackend.entities.Test;
import backend.hobbiebackend.entities.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Integer id;
    private String username;
    private GenderEnum gender;
    private String email;
    private String password;
    private Test testResults;
    private String avatarFile;
}
