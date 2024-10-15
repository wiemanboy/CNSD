package com.wiemanboy.producer.application.messaging;

import com.wiemanboy.producer.application.messaging.dto.DownloadDto;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageProducer implements MessageProducer {

    private final QueueMessagingTemplate messagingTemplate;

    public SqsMessageProducer(QueueMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void fileDownload(String fileKey) {
        messagingTemplate.convertAndSend("DownloadQueue", new DownloadDto(fileKey));
    }
}
