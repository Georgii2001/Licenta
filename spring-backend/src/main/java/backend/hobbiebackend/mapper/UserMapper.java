package backend.hobbiebackend.mapper;

import backend.hobbiebackend.dto.UsersDTO;
import backend.hobbiebackend.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarFile", source = "avatarFile")
    UsersDTO mapUserToDTO(UserEntity user, String avatarFile);
}
