package top.lytree.minio;

import io.minio.MinioClient;
import io.minio.PostPolicy;

import java.time.ZonedDateTime;
import java.util.Map;

public class AuthHandler {
    /**
     * 获取桶的上传权限
     * @param minioClient
     * @param bucketName
     * @param expirationTime
     */
    public void presign(MinioClient minioClient, String bucketName, ZonedDateTime expirationTime){
        PostPolicy policy = new PostPolicy(bucketName,expirationTime);
        // Add condition that 'key' (object name) equals to 'my-objectname'.
        policy.addStartsWithCondition("key", "");

        // Add condition that 'Content-Type' starts with 'image/'.
        policy.addStartsWithCondition("Content-Type", "");
        // 限制文件大小，单位是字节byte，也就是说可以设置如：只允许10M以内的文件上传
        //        policy.setContentRange(1, 10 * 1024);
        // 限制上传文件请求的ContentType
        //        policy.setContentType("image/png");
        try {
            // 生成凭证并返回
            final Map<String, String> map = minioClient.getPresignedPostFormData(policy);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
