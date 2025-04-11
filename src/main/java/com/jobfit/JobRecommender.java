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
    
    private List<JobRecommendation> generateRecommendations(AnalysisResult result) {
        List<JobRecommendation> recommendations = new ArrayList<>();
        
        // Fetch real job listings from API
        List<JobRecommendation> apiRecommendations = fetchJobListingsFromAPI();
        
        // If API recommendations are available, use them
        if (!apiRecommendations.isEmpty()) {
            recommendations.addAll(apiRecommendations);
        } else {
            // Otherwise, generate some random recommendations
            int numRecommendations = Math.min(5, 3 + (int)(result.getMatchScore() / 20));
            
            for (int i = 0; i < numRecommendations; i++) {
                JobRecommendation recommendation = new JobRecommendation();
                recommendation.setTitle(JOB_TITLES[random.nextInt(JOB_TITLES.length)]);
                recommendation.setCompany(COMPANIES[random.nextInt(COMPANIES.length)]);
                recommendation.setLocation(getRandomLocation());
                recommendation.setMatchScore(getAdjustedMatchScore(result.getMatchScore()));
                recommendations.add(recommendation);
            }
        }
        
        // Sort by match score
        recommendations.sort((r1, r2) -> Double.compare(r2.getMatchScore(), r1.getMatchScore()));
        
        return recommendations;
    }
    
    private List<JobRecommendation> fetchJobListingsFromAPI() {
        List<JobRecommendation> recommendations = new ArrayList<>();
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
                JobRecommendation recommendation = new JobRecommendation();
                recommendation.setTitle(jsonObject.getString("title"));
                recommendation.setCompany(jsonObject.getString("company"));
                recommendation.setLocation(jsonObject.getString("location"));
                recommendation.setMatchScore(jsonObject.getDouble("matchScore"));
                recommendations.add(recommendation);
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
    
    private double getAdjustedMatchScore(double baseScore) {
        // Vary the match score slightly for each recommendation
        double adjustment = (random.nextDouble() * 20) - 10; // -10 to +10
        double adjustedScore = baseScore + adjustment;
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
    
    private static class JobRecommendation {
        private String title;
        private String company;
        private String location;
        private double matchScore;
        
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getCompany() {
            return company;
        }
        
        public void setCompany(String company) {
            this.company = company;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public double getMatchScore() {
            return matchScore;
        }
        
        public void setMatchScore(double matchScore) {
            this.matchScore = matchScore;
        }
    }
}
