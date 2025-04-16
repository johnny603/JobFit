package com.jobfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadExceptionAdvice.class);

    // Inject this properties into the multipart ones.
    @Autowired
    private MultipartProperties multipartProperties;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        String message = exc.getMessage();
        String fileSizeMB = "0"; // default value

        if (message != null) {
            Pattern pattern = Pattern.compile("\\((\\d+)\\)");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                try {
                    long fileSizeBytes = Long.parseLong(matcher.group(1));
                    fileSizeMB = String.format("%.1f", (double) fileSizeBytes / (1024 * 1024));
                } catch (NumberFormatException e) {
                    logger.warn("There was an error parsing the size of the message: {}", message, e);
                    fileSizeMB = "error";
                }
            } else {
                logger.warn("There was no size of the file in the message: {}", message);
            }
        }

        logger.error("You can not upload this file. The maximum file size permitted is 10.0MB (You tried to upload {} MB). Maybe you could reduce the size of the file by erasing blank lines.", fileSizeMB);
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("You can not upload this file. The maximum file size permitted is 10.0MB (You tried to upload " + fileSizeMB + " MB) \n Maybe you could reduce the size of the file erasing blank lines");
    }
}
