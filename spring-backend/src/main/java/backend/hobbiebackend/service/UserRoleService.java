package backend.hobbiebackend.service;

import backend.hobbiebackend.entities.UserRoleEntity;
import backend.hobbiebackend.entities.enums.UserRoleEnum;

public interface UserRoleService {
    UserRoleEntity getUserRoleByEnumName(UserRoleEnum userRoleEnum);

    UserRoleEntity saveRole(UserRoleEntity userRoleEntity);
}
