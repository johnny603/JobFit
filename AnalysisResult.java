package com.jobfit.model;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
    private double matchScore;
    private List<String> strengths = new ArrayList<>();
    private List<String> missingKeywords = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();
    
    public double getMatchScore() {
        return matchScore;
    }
    
    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }
    
    public List<String> getStrengths() {
        return strengths;
    }
    
    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }
    
    public List<String> getMissingKeywords() {
        return missingKeywords;
    }
    
    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }
    
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
