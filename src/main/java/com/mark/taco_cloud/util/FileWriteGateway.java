package com.mark.taco_cloud.util;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriteGateway {

    void writeToFile(
            @Header(FileHeaders.FILENAME) String filename, String data
    );

}
