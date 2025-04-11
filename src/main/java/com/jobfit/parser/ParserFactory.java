package com.jobfit.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ParserFactory {
    private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);
    
    public static DocumentParser getParser(File file) {
        String fileName = file.getName().toLowerCase();
        
        if (fileName.endsWith(".pdf")) {
            logger.info("Creating PDF parser for file: {}", file.getName());
            return new PdfParser();
        } else if (fileName.endsWith(".docx")) {
            logger.info("Creating DOCX parser for file: {}", file.getName());
            return new DocxParser();
        } else if (fileName.endsWith(".txt")) {
            logger.info("Creating text parser for file: {}", file.getName());
            return new TextParser();
        } else {
            logger.error("Unsupported file format: {}", file.getName());
            throw new UnsupportedOperationException("Unsupported file format: " + fileName);
        }
    }
}
