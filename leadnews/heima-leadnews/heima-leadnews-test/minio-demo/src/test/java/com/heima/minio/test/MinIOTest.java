package com.heima.minio.test;


import com.heima.file.service.FileStorageService;
import com.heima.minio.MinIOApplication;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//@SpringBootTest(classes = MinIOApplication.class)
//@RunWith(SpringRunner.class)
//public class MinIOTest {
//
//
//    public static void main(String[] args) {
//
//
//        try {
//
//            FileInputStream fileInputStream = new FileInputStream("/Users/chanwoochoi/Downloads/123.jpg");
//            ;
//
//            //1.创建minio链接客户端
//            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123").endpoint("http://localhost:9090").build();
//            //2.上传
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .object("123.jpg")//文件名
//                    .contentType("image/jpeg")//文件类型
//                    .bucket("leadnews")//桶名词  与minio创建的名词一致
//                    .stream(fileInputStream, fileInputStream.available(), -1) //文件流
//                    .build();
//            minioClient.putObject(putObjectArgs);
//
//            System.out.println("success");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
////     @Autowired
////    private FileStorageService fileStorageService;
////
////    @Test
////    public void testUpdateImgFile() {
////        try {
////            FileInputStream fileInputStream = new FileInputStream("/Users/chanwoochoi/zp.jpg");
////            String path = fileStorageService.uploadHtmlFile("", "zp.jpg", fileInputStream);
////            System.out.println(path);
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
////    }
//    }
//}


@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
public class MinIOTest {

    public static void main(String[] args) {

        try {
            // 1. Open the FileInputStream for the image
            FileInputStream fileInputStream = new FileInputStream("/Users/chanwoochoi/Downloads/123.jpg");

            // 2. Create Minio client connection
            MinioClient minioClient = MinioClient.builder()
                    .credentials("minio", "minio123")
                    .endpoint("http://127.0.0.1:9000")
                    .build();

            // 3. Upload the image
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("images/2.jpeg") // Object name (destination in MinIO)
                    .contentType("image/jpeg") // Content type of the image
                    .bucket("leadnews") // Bucket name, should be the same as created in MinIO
                    .stream(fileInputStream, fileInputStream.available(), -1) // Provide input stream of the image
                    .build();
            minioClient.putObject(putObjectArgs);

            // 4. Print the URL of the uploaded image
            System.out.println("Image uploaded successfully. Access it at:");
            System.out.println("http://localhost:9000/leadnews/images/2.jpeg");

            // 5. Close the FileInputStream
            fileInputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
