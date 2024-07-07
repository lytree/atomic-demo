package top.lytree.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    /**
     * 上传文件
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @param fileName
     * @return
     * @throws IOException
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public ObjectWriteResponse uploadFile(MinioClient minioClient,String bucketName, String objectName, String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(fileName)
                        .build());
    }
    /**
     * 上传文件流
     *
     * @param minioClient
     * @param bucketName
     * @param fileName
     * @param stream
     * @param fileSize
     * @param type
     * @throws Exception
     */
    public void uploadFile(MinioClient minioClient, String bucketName, String fileName, InputStream stream, Long fileSize, String type)
            throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(stream, fileSize, -1)
                .contentType(type).build());
    }

    public void downloadFile(MinioClient minioClient, String bucketName, String fileName,
                             OutputStream outputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 获取对象并将其写入输出流
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build())) {
            byte[] buf = new byte[1204];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf, 0, buf.length)) >= 0) {
                outputStream.write(buf, 0, bytesRead);
            }
        }
    }

    /**
     * 文件夹是否存在
     *
     * @param minioClient
     * @param bucketName
     * @param prefix
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public Boolean folderExists(MinioClient minioClient, String bucketName, String prefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(
                prefix).recursive(false).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            if (item.isDir()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件是否存在
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @return
     */
    public boolean isObjectExist(MinioClient minioClient,String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean exist = true;
        minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        return exist;
    }
    /**
     * 创建文件夹
     *
     * @param minioClient
     * @param bucketName
     * @param path
     */
    public void createFolder(MinioClient minioClient, String bucketName, String path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(path)
                .stream(new ByteArrayInputStream(new byte[]{}), 0, -1).build());
    }

    /**
     * 获取文件外链
     *
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @param expires    过期时间 <=7 秒 （外链有效时间（单位：秒））
     * @return url
     */
    public String getPresignedObjectUrl(MinioClient minioClient,String bucketName, String objectName, Integer expires) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().expiry(expires).bucket(bucketName).object(objectName).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 获得文件外链
     *
     * @param bucketName
     * @param objectName
     * @return url
     */
    public String getPresignedObjectUrl(MinioClient minioClient,String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 批量删除文件
     *
     * @param filePathList 文件路径列表
     * @author HuangLongFei
     * @since 2023/7/17
     */
    public void deleteFile(MinioClient minioClient, String bucketName, List<String> filePathList) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (CollectionUtils.isEmpty(filePathList)) {
            return;
        }
        // 构建要删除的文件列表
        List<DeleteObject> deleteFileList = new ArrayList<>();
        for (String filePath : filePathList) {
            deleteFileList.add(new DeleteObject(filePath));
        }
        // 批量删除文件
        RemoveObjectsArgs fileArgs = RemoveObjectsArgs.builder().bucket(bucketName).objects(
                deleteFileList).build();
        Iterable<Result<DeleteError>> fileDeleteResult = minioClient.removeObjects(fileArgs);
        // 注意这里必须迭代返回结果,因为removeObjects是惰性的
        for (Result<DeleteError> result : fileDeleteResult) {
            DeleteError deleteError = result.get();
        }
    }
    public List<Item> getAllObjectsByPrefix(MinioClient minioClient,String bucketName,
                                            String prefix,
                                            boolean recursive) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }
    /**
     * 获取minio中,某个bucket中所有的文件名
     */
    public List<String> getFileList(MinioClient minioClient,String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 根据bucketName,获取该桶下所有的文件信息
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName)
                .recursive(true).build());
        List<String> fileNameList = new ArrayList<>();
        for (Result<Item> result : results) {
            Item item;
            String fileName = null;
            try {
                item = result.get();
                fileName = item.objectName();
                fileNameList.add(fileName);
            } catch (Exception e) {
                logger.error("call the minioClient.listObjects Error. result={} and message = {}", result, e);
            }
            // 获取文件的访问路径
            if (StringUtils.isNotBlank(fileName)) {
                String accessUrl = getPresignedObjectUrl(minioClient,bucketName, fileName);
            }
        }
        return fileNameList;
    }

    /**
     * 断点下载
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @param offset
     * @param length
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public InputStream getObject(MinioClient minioClient,String bucketName, String objectName, long offset, long length) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .offset(offset)
                        .length(length)
                        .build());
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public String getFileStatusInfo(MinioClient minioClient,String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()).toString();
    }
}
