package backend.hobbiebackend.utils;

import backend.hobbiebackend.handler.NotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static backend.hobbiebackend.constants.Constants.SEPARATOR;
import static backend.hobbiebackend.constants.Constants.USER_PHOTOS_PATH;

@Component
public class PhotoUtils {

    public void savePhoto(MultipartFile avatar, String username) {

        File filePath = new File(USER_PHOTOS_PATH + username + SEPARATOR + avatar.getOriginalFilename());
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

    public String getEncodedFile(String fileName, String username) {

        File directory = new File(USER_PHOTOS_PATH + username + SEPARATOR);
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