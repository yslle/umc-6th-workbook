package umc.spring.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.spring.config.AmazonConfig;
import umc.spring.domain.Uuid;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    // 파일 삭제
    public void deleteFile(String fileName) {
        try {
            AmazonS3URI uri = new AmazonS3URI(fileName);
            String bucket = uri.getBucket();
            String key = uri.getKey();

            amazonS3.deleteObject(bucket, key);
        } catch (IllegalArgumentException e) {
            log.error("error at AmazonS3Manager deleteFile : {}", (Object) e.getStackTrace());
        }
    }

    public String generateReviewKeyName(Uuid uuid) {
        return amazonConfig.getReviewPath() + '/' + uuid.getUuid();
    }
}