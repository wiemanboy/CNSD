package com.wiemanboy.consumer.application.messaging;

import com.wiemanboy.consumer.application.FileService;
import com.wiemanboy.consumer.application.messaging.dto.DownloadDto;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageConsumer {

    private final FileService fileService;

    public SqsMessageConsumer(FileService fileService) {
        this.fileService = fileService;
    }

    @SqsListener("DownloadQueue")
    public void consumeDownloadMessage(Message<DownloadDto> message) {
        fileService.processImage(message.getPayload().fileKey());
    }
}
