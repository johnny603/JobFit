package com.jobfit.analyzer;

import com.jobfit.model.AnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobFitAnalyzerTest {
    
    private JobFitAnalyzer analyzer;
    
    @BeforeEach
    public void setUp() {
        analyzer = new JobFitAnalyzer();
    }
    
    @Test
    public void testAnalyzeJobFit_PerfectMatch() {
        // Given
        String resume = "Experienced Java Developer with 5 years of experience in Spring, Hibernate, and AWS.";
        String jobDesc = "Looking for a Java Developer with experience in Spring and AWS.";
        
        // When
        AnalysisResult result = analyzer.analyzeJobFit(resume, jobDesc);
        
        // Then
        assertTrue(result.getMatchScore() > 90.0, "Perfect match should have a high score");
        assertFalse(result.getStrengths().isEmpty(), "Should identify strengths");
        assertTrue(result.getMissingKeywords().isEmpty(), "Should not have missing keywords");
    }
    
    @Test
    public void testAnalyzeJobFit_PartialMatch() {
        // Given
        String resume = "Frontend Developer with React and CSS experience.";
        String jobDesc = "Frontend Developer needed with React, Angular, and Vue experience.";
        
        // When
        AnalysisResult result = analyzer.analyzeJobFit(resume, jobDesc);
        
        // Then
        assertTrue(result.getMatchScore() > 30 && result.getMatchScore() < 70, 
                "Partial match should have a moderate score");
        assertFalse(result.getMissingKeywords().isEmpty(), "Should have missing keywords");
        assertTrue(result.getMissingKeywords().contains("angular") || 
                result.getMissingKeywords().contains("vue"), 
                "Should identify missing skills");
    }
    
    @Test
    public void testAnalyzeJobFit_NoMatch() {
        // Given
        String resume = "Marketing Manager with social media expertise.";
        String jobDesc = "Java Developer with 5+ years of experience in Spring Boot and AWS.";
        
        // When
        AnalysisResult result = analyzer.analyzeJobFit(resume, jobDesc);
        
        // Then
        assertTrue(result.getMatchScore() < 30.0, "No match should have a low score");
        assertFalse(result.getSuggestions().isEmpty(), "Should provide suggestions");
    }
}
