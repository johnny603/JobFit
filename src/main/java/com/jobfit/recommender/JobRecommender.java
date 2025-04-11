package com.jobfit.recommender;

import com.jobfit.model.AnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class provides job recommendations based on the analysis result.
 * Note: In a real application, this would connect to job listing APIs or databases.
 * This is a simplified version for demonstration purposes.
 */
public class JobRecommender {
    private static final Logger logger = LoggerFactory.getLogger(JobRecommender.class);
    private final Random random = new Random();
    
    // Mock job titles for demonstration
    private static final String[] JOB_TITLES = {
            "Software Engineer", "Java Developer", "Full Stack Developer",
            "Backend Developer", "Frontend Developer", "DevOps Engineer",
            "Cloud Architect", "Data Engineer", "Product Manager",
            "QA Engineer", "Technical Writer", "UI/UX Designer"
    };
    
    // Mock companies for demonstration
    private static final String[] COMPANIES = {
            "TechCorp", "InnovateSoft", "CodeMasters",
            "DataFlow Systems", "CloudNine Solutions", "AlgorithmWorks",
            "NextGen Technology", "Binary Solutions", "Digital Dynamics",
            "Quantum Computing", "Future Systems", "Infinite Software"
    };
    
    public void recommendJobs(AnalysisResult result) {
        logger.info("Generating job recommendations based on analysis");
        
        List<JobRecommendation> recommendations = generateRecommendations(result);
        displayRecommendations(recommendations);
    }
    
    private List<JobRecommendation> generateRecommendations(AnalysisResult analysisResult) {
        List<JobRecommendation> recommendations = new ArrayList<>();
        try {
            List<Map<String, Object>> jobListings = fetchJobListingsFromAPI(analysisResult);
            for (Map<String, Object> job : jobListings) {
                JobRecommendation recommendation = new JobRecommendation();
                recommendation.setTitle((String) job.get("title"));
                recommendation.setCompany((String) job.get("company"));
                recommendation.setUrl((String) job.get("url"));
                recommendation.setLocation((String) job.get("location"));
                recommendation.setMatchScore(getAdjustedMatchScore(analysisResult, job));
                recommendations.add(recommendation);
            }
        } catch (Exception e) {
            // Fallback to mock data if API call fails
            recommendations.add(new JobRecommendation("Software Engineer", "Tech Corp", 0.95, "https://example.com/job1"));
            recommendations.add(new JobRecommendation("Data Scientist", "Data Inc", 0.85, "https://example.com/job2"));
            recommendations.add(new JobRecommendation("Product Manager", "Product Co", 0.75, "https://example.com/job3"));
        }
        return recommendations;
    }
    
    private List<Map<String, Object>> fetchJobListingsFromAPI(AnalysisResult analysisResult) {
        List<Map<String, Object>> recommendations = new ArrayList<>();
        try {
            String apiUrl = "https://api.example.com/joblistings"; // Replace with actual API URL
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
            
            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, Object> job = new HashMap<>();
                job.put("title", jsonObject.getString("title"));
                job.put("company", jsonObject.getString("company"));
                job.put("location", jsonObject.getString("location"));
                job.put("url", jsonObject.getString("url"));
                job.put("matchScore", jsonObject.getDouble("matchScore"));
                recommendations.add(job);
            }
        } catch (Exception e) {
            logger.error("Error fetching job listings from API: {}", e.getMessage());
        }
        return recommendations;
    }
    
    private String getRandomLocation() {
        String[] locations = {"New York, NY", "San Francisco, CA", "Austin, TX", "Seattle, WA", 
                             "Chicago, IL", "Boston, MA", "Denver, CO", "Atlanta, GA", "Remote"};
        return locations[random.nextInt(locations.length)];
    }
    
    private double getAdjustedMatchScore(AnalysisResult analysisResult, Map<String, Object> job) {
        // Vary the match score slightly for each recommendation
        double adjustment = (random.nextDouble() * 20) - 10; // -10 to +10
        double adjustedScore = analysisResult.getMatchScore() + adjustment;
        return Math.min(100, Math.max(50, adjustedScore)); // Keep between 50 and 100
    }
    
    private void displayRecommendations(List<JobRecommendation> recommendations) {
        System.out.println("\n===== JOB RECOMMENDATIONS =====");
        
        for (int i = 0; i < recommendations.size(); i++) {
            JobRecommendation job = recommendations.get(i);
            System.out.println("\n" + (i + 1) + ". " + job.getTitle());
            System.out.println("   Company: " + job.getCompany());
            System.out.println("   Location: " + job.getLocation());
            System.out.println("   Match Score: " + String.format("%.1f", job.getMatchScore()) + "%");
        }
        
        System.out.println("\nNote: These recommendations are generated based on your resume analysis.");
        System.out.println("In a complete implementation, these would be real job listings from job boards.");
    }
    
    // Inner class for job recommendations
    public static class JobRecommendation {
        private String title;
        private String company;
        private double matchScore;
        private String url;
        private String location;

        public JobRecommendation() {
            // Default constructor
        }

        public JobRecommendation(String title, String company, double matchScore, String url) {
            this.title = title;
            this.company = company;
            this.matchScore = matchScore;
            this.url = url;
        }

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }
        public double getMatchScore() { return matchScore; }
        public void setMatchScore(double matchScore) { this.matchScore = matchScore; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }
}
