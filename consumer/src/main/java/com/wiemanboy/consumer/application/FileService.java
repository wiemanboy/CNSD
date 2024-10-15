package com.wiemanboy.consumer.application;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static java.lang.String.format;

@Service
public class FileService {

    private final S3Client s3Client;
    private final String bucketName;

    public FileService(S3Client s3Client) {
        this.s3Client = s3Client;
        this.bucketName = "jarno-messaging-bucket";
    }

    public void processImage(String fileKey){
        System.out.printf("Processing file %s%n", fileKey);

        ResponseInputStream<GetObjectResponse> fileInputStream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(format("uploads/%s", fileKey))
                .build(), ResponseTransformer.toInputStream());

        try {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(format("processed/%s", fileKey))
                .build(), RequestBody.fromInputStream(fileInputStream, fileInputStream.available()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
