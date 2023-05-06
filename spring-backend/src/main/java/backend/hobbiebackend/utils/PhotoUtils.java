package backend.hobbiebackend.utils;

import backend.hobbiebackend.entities.Avatars;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.handler.NotFoundException;
import backend.hobbiebackend.repostiory.AvatarsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static backend.hobbiebackend.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class PhotoUtils {

    private final AvatarsRepository avatarsRepository;

    public void savePhoto(MultipartFile avatar, String username, String avatarFileName) {
        final String pathname = getPathMyPhotos(username) + avatarFileName;
        File filePath = new File(pathname);
        createMyFolder(filePath);

        try {
            avatar.transferTo(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Photos wasn't uploaded.");
        }
    }

    private void createMyFolder(File filePath) {
        File parentDir = filePath.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    public String saveAvatarInDB(UserEntity userEntity, String avatarName, Integer priority) {
        Avatars avatar = Avatars.builder()
                .userEntity(userEntity)
                .avatarName(avatarName)
                .avatarPriority(priority)
                .build();
        avatarsRepository.saveAndFlush(avatar);

        final String finalAvatarName = avatar.getAvatarsId() + UNDERSCORE + avatarName;
        avatar.setAvatarName(finalAvatarName);
        avatarsRepository.saveAndFlush(avatar);

        return finalAvatarName;
    }

    public List<Avatars> getSortedAvatars(Integer userId) {
        return avatarsRepository.findByUserEntityId(userId).stream()
                .sorted(Comparator.comparing(Avatars::getAvatarPriority))
                .collect(Collectors.toList());
    }

    public String getEncodedFile(String fileName, String username) {
        final String pathname = getPathMyPhotos(username);
        File directory = new File(pathname);
        try {
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                assert files != null;
                for (File file : files) {
                    if (file.getName().equals(fileName)) {
                        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
                        return new String(encoded, StandardCharsets.US_ASCII);
                    }
                }
            }
            return null;

        } catch (IOException e) {
            throw new NotFoundException("File not found in directory");
        }
    }

    private String getPathMyPhotos(String username) {
        return USER_PHOTOS_PATH + username + SEPARATOR;
    }
}