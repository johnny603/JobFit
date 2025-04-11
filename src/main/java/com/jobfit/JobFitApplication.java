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

import java.io.File;
import java.io.IOException;
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
    public AnalysisResult analyzeJobFit(@RequestParam("resume") String resumePath,
                                        @RequestParam("jobDescription") String jobDescriptionPath) {
        logger.info("Starting JobFit Analysis");

        try {
            // Parse resume
            File resumeFile = new File(resumePath);
            DocumentParser resumeParser = ParserFactory.getParser(resumeFile);
            String resumeContent = resumeParser.parseDocument(resumeFile);

            // Parse job description
            File jobDescFile = new File(jobDescriptionPath);
            DocumentParser jobDescParser = ParserFactory.getParser(jobDescFile);
            String jobDescContent = jobDescParser.parseDocument(jobDescFile);

            // Analyze match
            JobFitAnalyzer analyzer = new JobFitAnalyzer();
            AnalysisResult result = analyzer.analyzeJobFit(resumeContent, jobDescContent);

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

        // Placeholder for actual API call implementation
        return List.of();
    }
}
