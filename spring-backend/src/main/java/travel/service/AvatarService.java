package travel.service;

import travel.dto.UserAvatarDto;

public interface AvatarService {

    void saveUserAvatar(String username, UserAvatarDto userAvatar);

    void deleteUserAvatar(String username, Integer avatarId);

    void changeUserMainAvatar(String username, Integer avatarId);
}
