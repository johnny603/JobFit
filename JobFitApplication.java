package com.jobfit;

import com.jobfit.analyzer.JobFitAnalyzer;
import com.jobfit.model.AnalysisResult;
import com.jobfit.parser.DocumentParser;
import com.jobfit.parser.ParserFactory;
import com.jobfit.recommender.JobRecommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobFitApplication {
    private static final Logger logger = LoggerFactory.getLogger(JobFitApplication.class);

    public static void main(String[] args) {
        logger.info("Starting JobFit Application");
        
        if (args.length < 2) {
            logger.error("Insufficient arguments provided");
            System.out.println("Usage: java -jar jobfit.jar resume.pdf job-description.txt");
            System.exit(1);
        }
        
        String resumePath = args[0];
        String jobDescriptionPath = args[1];
        
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
            
            // Display results
            displayResults(result);
            
            // Fetch job listings from API
            List<JobRecommender.JobRecommendation> jobListings = fetchJobListingsFromAPI();
            
            // Recommend jobs
            JobRecommender recommender = new JobRecommender();
            recommender.recommendJobs(result, jobListings);
            
        } catch (IOException e) {
            logger.error("Error processing files: {}", e.getMessage());
            System.out.println("Error processing files: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        logger.info("JobFit Analysis completed");
    }
    
    private static void displayResults(AnalysisResult result) {
        System.out.println("\n===== JOBFIT ANALYSIS RESULTS =====");
        System.out.println("Match Score: " + result.getMatchScore() + "%");
        
        System.out.println("\n----- STRENGTHS -----");
        result.getStrengths().forEach(strength -> System.out.println("✅ " + strength));
        
        System.out.println("\n----- IMPROVEMENT AREAS -----");
        result.getMissingKeywords().forEach(keyword -> System.out.println("❌ Missing: " + keyword));
        
        System.out.println("\n----- SUGGESTIONS -----");
        result.getSuggestions().forEach(suggestion -> System.out.println("💡 " + suggestion));
        
        System.out.println("\n================================");
    }
    
    private static List<JobRecommender.JobRecommendation> fetchJobListingsFromAPI() {
        // Placeholder for actual API call implementation
        return List.of();
    }
}
