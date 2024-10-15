package com.wiemanboy.consumer.application.messaging;

import com.wiemanboy.consumer.application.FileService;
import com.wiemanboy.consumer.application.messaging.dto.DownloadDto;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageConsumer {

    private final FileService fileService;

    public SqsMessageConsumer(FileService fileService) {
        this.fileService = fileService;
    }

    @SqsListener("DownloadQueue")
    public void consumeDownloadMessage(DownloadDto downloadDto) {
        fileService.processImage(downloadDto.fileKey());
    }
}
