package com.jobfit.parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

// Interface for document parsers
public interface DocumentParser {
    String parseDocument(File file) throws IOException;
}

// PDF Parser implementation
class PdfParser implements DocumentParser {
    private static final Logger logger = LoggerFactory.getLogger(PdfParser.class);
    
    @Override
    public String parseDocument(File file) throws IOException {
        logger.info("Parsing PDF file: {}", file.getName());
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            logger.error("Error parsing PDF file: {}", e.getMessage());
            throw e;
        }
    }
}

// DOCX Parser implementation
class DocxParser implements DocumentParser {
    private static final Logger logger = LoggerFactory.getLogger(DocxParser.class);
    
    @Override
    public String parseDocument(File file) throws IOException {
        logger.info("Parsing DOCX file: {}", file.getName());
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            
            return extractor.getText();
        } catch (IOException e) {
            logger.error("Error parsing DOCX file: {}", e.getMessage());
            throw e;
        }
    }
}

// Text file Parser implementation
class TextParser implements DocumentParser {
    private static final Logger logger = LoggerFactory.getLogger(TextParser.class);
    
    @Override
    public String parseDocument(File file) throws IOException {
        logger.info("Parsing text file: {}", file.getName());
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            logger.error("Error parsing text file: {}", e.getMessage());
            throw e;
        }
    }
}
