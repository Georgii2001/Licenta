package backend.hobbiebackend.mapper;

import backend.hobbiebackend.dto.AvatarsDTO;
import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarFile", source = "avatarFile")
    @Mapping(target = "avatarFiles", source = "avatarFiles")
    @Mapping(target = "interests", source = "interests")
    UsersDTO mapUserToDTO(UserEntity user, String avatarFile, List<AvatarsDTO> avatarFiles, List<String> interests);


}
