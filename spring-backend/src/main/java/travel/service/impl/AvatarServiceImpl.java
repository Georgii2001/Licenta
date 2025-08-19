package travel.service.impl;

import travel.dto.UserAvatarDto;
import travel.entities.Avatars;
import travel.entities.UserEntity;
import travel.repostiory.AvatarsRepository;
import travel.repostiory.UserRepository;
import travel.service.AvatarService;
import travel.utils.PhotoUtils;
import travel.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final PhotoUtils photoUtils;
    private final UserUtils userUtils;
    private final AvatarsRepository avatarsRepository;
    private final UserRepository userRepository;

    @Override
    public void saveUserAvatar(String username, UserAvatarDto userAvatar) {
        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        final Integer nextAvatarPriority = avatarsRepository.findNextAvatarPriority(userEntity.getId());
        final String finalAvatarName =
                photoUtils.saveAvatarInDB(userEntity,userAvatar.getAvatar().getOriginalFilename(),nextAvatarPriority);
        photoUtils.savePhoto(userAvatar.getAvatar(), username, finalAvatarName);
    }

    @Override
    public void deleteUserAvatar(String username, Integer avatarId) {
        UserEntity userEntity = userUtils.getUserEntity(username, null, null);
        final Integer userId = userEntity.getId();
        avatarsRepository.deleteUserAvatar(userId, avatarId);

        List<Avatars> avatars = photoUtils.getSortedAvatars(userId);

        for (int i = 0; i < avatars.size(); i++) {
            avatars.get(i).setAvatarPriority(i);
        }

        avatarsRepository.saveAll(avatars);
    }

    @Override
    public void changeUserMainAvatar(String username, Integer avatarId) {
        UserEntity userEntity = userUtils.getUserEntity(username, null, null);

        List<Avatars> avatars = photoUtils.getSortedAvatars(userEntity.getId());
        for (Avatars avatar : avatars) {
            if (avatar.getAvatarsId().equals(avatarId)) {
                avatar.setAvatarPriority(0);
                userEntity.setAvatar(avatar.getAvatarName());
                break;
            }
            avatar.setAvatarPriority(avatar.getAvatarPriority() + 1);
        }

        userRepository.save(userEntity);
        avatarsRepository.saveAll(avatars);
    }
}
