package travel.mapper;

import travel.dto.AvatarsDTO;
import travel.dto.UsersDTO;
import travel.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarFile", source = "avatarFile")
    @Mapping(target = "avatarFiles", source = "avatarFiles")
    @Mapping(target = "interests", source = "interests")
    @Mapping(target = "userMatchCount", source = "userMatchCount")
    UsersDTO mapUserToDTO(UserEntity user, String avatarFile, List<AvatarsDTO> avatarFiles, List<String> interests, Long userMatchCount);
}
