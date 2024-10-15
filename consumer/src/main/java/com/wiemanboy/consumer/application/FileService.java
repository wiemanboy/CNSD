package com.wiemanboy.consumer.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class FileService {

    private final AmazonS3 amazonS3;

    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void processImage(String fileKey) {
        S3Object file = amazonS3.getObject("jarno-messaging-bucket", format("upload/%s", fileKey));
        System.out.printf("Processing file %s%n", fileKey);
        amazonS3.putObject("jarno-messaging-bucket", format("processed/%s", fileKey), file.getObjectContent(), null);
    }
}
