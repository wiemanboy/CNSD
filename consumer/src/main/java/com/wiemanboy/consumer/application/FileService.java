package com.wiemanboy.consumer.application;


import org.springframework.stereotype.Service;

@Service
public class FileService {

    public void processImage(String fileKey){

        System.out.printf("Processing file %s%n", fileKey);
    }
}
