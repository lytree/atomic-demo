package top.lytree;

import io.minio.*;
import io.minio.http.Method;
import top.lytree.minio.AuthHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        uploading("F:\\Godot\\Godot_v4.2.2-stable_mono_win64.exe");
    }
    public static String uploading(String filePath) throws Exception {
        //3.将图片上传至minio
        //3.1.创建minio链接客户端
        FileInputStream fileInputStream = null;
        fileInputStream =  new FileInputStream(filePath);
        MinioClient minioClient = MinioClient.builder().credentials("minioadmin", "minioadmin")
                .endpoint("http://localhost:9000").build();
        //3.2上传
        String fileName = new File(filePath).getName();
        // 检查文件名是否有效
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("无效的文件名");
        }
        String objectName = fileName; // 使用原始文件名作为对象名
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .object(objectName)//文件名
                .bucket("image")//桶名词  与minio创建的名词一致
                .stream(fileInputStream, fileInputStream.available(), -1) //文件流
                .build();
        minioClient.putObject(putObjectArgs);

        //设置 Minio 存储桶（bucket）的访问策略（Policy）
        String bucketPolicy = "{"
                + "  \"Version\": \"2012-10-17\","
                + "  \"Statement\": ["
                + "    {"
                + "      \"Effect\": \"Allow\","
                + "      \"Principal\": \"*\","
                + "      \"Action\": [\"s3:GetObject\"],"
                + "      \"Resource\": [\"arn:aws:s3:::" + "image" + "/*\"]"
                + "    }"
                + "  ]"
                + "}";
        //设置为公开
        SetBucketPolicyArgs bucketPolicyArgs = SetBucketPolicyArgs.builder().config(bucketPolicy).bucket("image").build();
        minioClient.setBucketPolicy(bucketPolicyArgs);
        String image = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(
                "image").object(objectName).expiry(1, TimeUnit.DAYS).build());
        System.out.println("长期链接" + image);

        AuthHandler authHandler = new AuthHandler();
        authHandler.presign(minioClient,"image",  ZonedDateTime.now().plusMinutes(10));

        return "downloadUrl";
    }
}