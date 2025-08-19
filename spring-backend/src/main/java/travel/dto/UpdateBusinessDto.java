package travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessDto {
    private Integer id;
    private String businessName;
    private String address;
    private String password;
}
