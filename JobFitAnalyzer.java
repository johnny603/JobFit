package com.jobfit.analyzer;

import com.jobfit.model.AnalysisResult;
import org.apache.opennlp.tools.tokenize.SimpleTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JobFitAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(JobFitAnalyzer.class);
    private final SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    
    // Common skill keywords
    private static final Set<String> COMMON_SKILLS = new HashSet<>(Arrays.asList(
            "java", "python", "javascript", "html", "css", "react", "angular", "vue", 
            "node", "express", "spring", "hibernate", "sql", "nosql", "mongodb", 
            "postgresql", "oracle", "aws", "azure", "gcp", "docker", "kubernetes",
            "ci/cd", "jenkins", "git", "agile", "scrum", "leadership", "management",
            "communication", "problem-solving", "teamwork", "collaboration",
            "java", "python", "javascript", "html", "css", "react", "angular", "vue", 
            "node", "express", "spring", "hibernate", "sql", "nosql", "mongodb", 
            "postgresql", "oracle", "aws", "azure", "gcp", "docker", "kubernetes",
            "ci/cd", "jenkins", "git", "agile", "scrum", "leadership", "management",
            "communication", "problem-solving", "teamwork", "collaboration",
            "java", "python", "javascript", "html", "css", "react", "angular", "vue", 
            "node", "express", "spring", "hibernate", "sql", "nosql", "mongodb", 
            "postgresql", "oracle", "aws", "azure", "gcp", "docker", "kubernetes",
            "ci/cd", "jenkins", "git", "agile", "scrum", "leadership", "management",
            "communication", "problem-solving", "teamwork", "collaboration"
    ));
    
    public AnalysisResult analyzeJobFit(String resumeContent, String jobDescContent) {
        logger.info("Starting job fit analysis");
        
        // Detect language and translate if necessary
        String detectedLanguage = detectLanguage(resumeContent);
        if (!detectedLanguage.equals("en")) {
            resumeContent = translateContent(resumeContent, detectedLanguage, "en");
        }
        
        // Extract information from resume and job description
        Set<String> resumeSkills = extractSkills(resumeContent);
        Set<String> jobSkills = extractSkills(jobDescContent);
        
        // Calculate match score
        Set<String> matchingSkills = new HashSet<>(resumeSkills);
        matchingSkills.retainAll(jobSkills);
        
        double matchScore = calculateMatchScore(matchingSkills.size(), jobSkills.size());
        
        // Find missing keywords
        Set<String> missingKeywords = new HashSet<>(jobSkills);
        missingKeywords.removeAll(resumeSkills);
        
        // Generate suggestions
        List<String> suggestions = generateSuggestions(resumeContent, jobDescContent, missingKeywords);
        
        // Identify strengths
        List<String> strengths = identifyStrengths(resumeContent, jobDescContent, matchingSkills);
        
        // Generate real-time feedback
        List<String> realTimeFeedback = generateRealTimeFeedback(resumeContent);
        
        AnalysisResult result = new AnalysisResult();
        result.setMatchScore(matchScore);
        result.setStrengths(strengths);
        result.setMissingKeywords(new ArrayList<>(missingKeywords));
        result.setSuggestions(suggestions);
        result.setRealTimeFeedback(realTimeFeedback);
        
        logger.info("Job fit analysis completed with match score: {}%", String.format("%.1f", matchScore));
        return result;
    }
    
    private Set<String> extractSkills(String content) {
        String[] tokens = tokenizer.tokenize(content.toLowerCase());
        
        Set<String> extractedSkills = new HashSet<>();
        
        // Extract single-word skills
        for (String token : tokens) {
            if (COMMON_SKILLS.contains(token)) {
                extractedSkills.add(token);
            }
        }
        
        // Extract multi-word skills
        for (String skill : COMMON_SKILLS) {
            if (skill.contains(" ") && content.toLowerCase().contains(skill)) {
                extractedSkills.add(skill);
            }
        }
        
        // Extract years of experience
        Pattern expPattern = Pattern.compile("(\\d+)\\+?\\s+years(?:\\s+of)?\\s+experience", Pattern.CASE_INSENSITIVE);
        Matcher matcher = expPattern.matcher(content);
        if (matcher.find()) {
            extractedSkills.add(matcher.group(0).toLowerCase());
        }
        
        return extractedSkills;
    }
    
    private double calculateMatchScore(int matchingCount, int jobRequirementsCount) {
        if (jobRequirementsCount == 0) return 0.0;
        return (double) matchingCount / jobRequirementsCount * 100;
    }
    
    private List<String> generateSuggestions(String resumeContent, String jobDescContent, Set<String> missingKeywords) {
        List<String> suggestions = new ArrayList<>();
        
        // Suggest adding missing keywords
        if (!missingKeywords.isEmpty()) {
            suggestions.add("Consider adding these missing keywords to your resume: " + 
                    missingKeywords.stream().limit(5).collect(Collectors.joining(", ")) + 
                    (missingKeywords.size() > 5 ? ", and others." : "."));
        }
        
        // Check for resume sections
        if (!resumeContent.toLowerCase().contains("education")) {
            suggestions.add("Add an Education section to highlight your academic background.");
        }
        
        if (!resumeContent.toLowerCase().contains("experience") && !resumeContent.toLowerCase().contains("employment")) {
            suggestions.add("Include a Professional Experience section detailing your work history.");
        }
        
        if (!resumeContent.toLowerCase().contains("skills") && !resumeContent.toLowerCase().contains("abilities")) {
            suggestions.add("Create a dedicated Skills section to highlight your technical and soft skills.");
        }
        
        // Check for quantifiable achievements
        if (!Pattern.compile("increased|improved|reduced|saved|achieved|delivered", Pattern.CASE_INSENSITIVE)
                .matcher(resumeContent).find()) {
            suggestions.add("Add quantifiable achievements to demonstrate your impact (e.g., 'Increased efficiency by 20%').");
        }
        
        return suggestions;
    }
    
    private List<String> identifyStrengths(String resumeContent, String jobDescContent, Set<String> matchingSkills) {
        List<String> strengths = new ArrayList<>();
        
        // Highlight matching skills
        if (!matchingSkills.isEmpty()) {
            strengths.add("Your resume matches " + matchingSkills.size() + " key skills required for this position.");
        }
        
        // Check for education alignment
        Pattern eduPattern = Pattern.compile("(bachelor|master|phd|degree|diploma)", Pattern.CASE_INSENSITIVE);
        Matcher resumeEduMatcher = eduPattern.matcher(resumeContent);
        Matcher jobEduMatcher = eduPattern.matcher(jobDescContent);
        
        if (resumeEduMatcher.find() && jobEduMatcher.find()) {
            strengths.add("Your educational background appears to align with the job requirements.");
        }
        
        // Check for experience alignment
        Pattern expPattern = Pattern.compile("(\\d+)\\+?\\s+years", Pattern.CASE_INSENSITIVE);
        Matcher resumeExpMatcher = expPattern.matcher(resumeContent);
        Matcher jobExpMatcher = expPattern.matcher(jobDescContent);
        
        if (resumeExpMatcher.find() && jobExpMatcher.find()) {
            int resumeYears = Integer.parseInt(resumeExpMatcher.group(1));
            int jobYears = Integer.parseInt(jobExpMatcher.group(1));
            
            if (resumeYears >= jobYears) {
                strengths.add("You meet or exceed the required years of experience for this position.");
            }
        }
        
        return strengths;
    }
    
    private List<String> generateRealTimeFeedback(String resumeContent) {
        List<String> feedback = new ArrayList<>();
        
        // Check for common resume issues and provide feedback
        if (resumeContent.length() < 500) {
            feedback.add("Your resume appears to be too short. Consider adding more details about your experience and skills.");
        }
        
        if (!resumeContent.toLowerCase().contains("summary")) {
            feedback.add("Add a summary section to provide an overview of your qualifications and career goals.");
        }
        
        if (!resumeContent.toLowerCase().contains("contact")) {
            feedback.add("Ensure your contact information is included and up-to-date.");
        }
        
        if (Pattern.compile("\\b(i|me|my|mine)\\b", Pattern.CASE_INSENSITIVE).matcher(resumeContent).find()) {
            feedback.add("Avoid using first-person pronouns (I, me, my) in your resume.");
        }
        
        return feedback;
    }
    
    private String detectLanguage(String content) {
        // Placeholder for language detection logic
        return "en";
    }
    
    private String translateContent(String content, String sourceLang, String targetLang) {
        // Placeholder for translation logic
        return content;
    }
}
