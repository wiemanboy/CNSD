package com.wiemanboy.producer.application;

import com.amazonaws.services.s3.AmazonS3;
import com.wiemanboy.producer.application.messaging.MessageProducer;
import com.wiemanboy.producer.application.messaging.SqsMessageProducer;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class FileService {

    private final MessageProducer messageProducer;
    private final AmazonS3 amazonS3;

    public FileService(SqsMessageProducer messageProducer, AmazonS3 amazonS3) {
        this.messageProducer = messageProducer;
        this.amazonS3 = amazonS3;
    }

    public void fileDownload(InputStream file) {
        String fileKey = format("uploads/%s", UUID.randomUUID());
        amazonS3.putObject("jarno-messaging-bucket", fileKey, file, null);
        messageProducer.fileDownload(fileKey);
    }
}
