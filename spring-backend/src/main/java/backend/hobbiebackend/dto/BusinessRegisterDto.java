package backend.hobbiebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRegisterDto {
    private String username;
    private String businessName;
    private String address;
    private String email;
    private String password;
}
