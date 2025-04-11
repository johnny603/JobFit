package com.jobfit;

import com.jobfit.analyzer.JobFitAnalyzer;
import com.jobfit.model.AnalysisResult;
import com.jobfit.parser.DocumentParser;
import com.jobfit.parser.ParserFactory;
import com.jobfit.recommender.JobRecommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class JobFitApplication {
    private static final Logger logger = LoggerFactory.getLogger(JobFitApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JobFitApplication.class, args);
    }

    @PostMapping("/analyze")
    public AnalysisResult analyzeJobFit(@RequestParam("resume") MultipartFile resume,
                                       @RequestParam("jobDescription") MultipartFile jobDescription) {
        logger.info("Starting JobFit Analysis");

        try {
            // Create temporary files
            Path resumePath = Files.createTempFile("resume_", getFileExtension(resume.getOriginalFilename()));
            Path jobDescPath = Files.createTempFile("jobdesc_", getFileExtension(jobDescription.getOriginalFilename()));
            
            // Transfer uploaded files to temporary files
            resume.transferTo(resumePath);
            jobDescription.transferTo(jobDescPath);

            // Parse resume
            File resumeFile = resumePath.toFile();
            DocumentParser resumeParser = ParserFactory.getParser(resumeFile);
            String resumeContent = resumeParser.parseDocument(resumeFile);

            // Parse job description
            File jobDescFile = jobDescPath.toFile();
            DocumentParser jobDescParser = ParserFactory.getParser(jobDescFile);
            String jobDescContent = jobDescParser.parseDocument(jobDescFile);

            // Analyze match
            JobFitAnalyzer analyzer = new JobFitAnalyzer();
            AnalysisResult result = analyzer.analyzeJobFit(resumeContent, jobDescContent);

            // Clean up temporary files
            Files.deleteIfExists(resumePath);
            Files.deleteIfExists(jobDescPath);

            logger.info("JobFit Analysis completed");
            return result;

        } catch (IOException e) {
            logger.error("Error processing files: {}", e.getMessage());
            throw new RuntimeException("Error processing files: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            throw new RuntimeException("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/recommendations")
    public List<JobRecommender.JobRecommendation> getJobRecommendations(@RequestParam("matchScore") double matchScore) {
        logger.info("Fetching job recommendations");
        JobRecommender recommender = new JobRecommender();
        AnalysisResult result = new AnalysisResult();
        result.setMatchScore(matchScore);
        recommender.recommendJobs(result);
        return List.of(); // TODO: Return actual recommendations
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return ".tmp";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
