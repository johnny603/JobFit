# JobFit: AI-Powered Job Matching App (in progress)

**JobFit** is an AI-powered application that helps job seekers find the best job opportunities based on their skills, experience, and preferences. It analyzes resumes, matches them with job descriptions, and provides insights to improve job fit.

## Features
- **Smart Job Matching**: Uses AI to compare resumes with job descriptions and suggest the best matches.
- **Resume Analysis**: Extracts skills, qualifications, and experience from resumes.
- **Keyword Optimization**: Highlights missing keywords to improve resume effectiveness.
- **Real-Time Feedback**: Provides suggestions to enhance resume content and formatting.
- **Automated Job Recommendations**: Offers job listings tailored to the user's profile.
- **Multi-Language Resume Support**: Supports resumes in multiple languages and translates them for analysis.
- **Recruiter Dashboard**: Provides a dashboard for recruiters to manage job postings and view candidate matches.

## Installation
### Prerequisites
- Java 11+
- Maven (for dependency management)

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/jobfit.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd jobfit
   ```
3. **Install dependencies**:
   ```bash
   mvn install
   ```
4. **Build the application**:
   ```bash
   mvn clean package
   ```
5. **Run the application**:
   ```bash
   java -jar target/jobfit-jar-with-dependencies.jar
   ```

## Usage
To match a resume with a job description, run:
```bash
java -jar jobfit.jar resume.pdf job-description.txt
```
The app will output:
- A match score between the resume and job description.
- Suggestions for improving the resume.
- Recommended job listings.

## Tech Stack
- **Java**: Core development language.
- **Apache POI**: Handles `.docx` file parsing.
- **PdfBox**: Processes `.pdf` files.
- **OpenNLP**: Performs Natural Language Processing (NLP) for resume analysis.
- **Spring Boot**: For API integration and web application deployment.
- **DetectLanguage**: Detects the language of the resume.
- **Google Cloud Translate**: Translates resumes to English for analysis.

## Roadmap
### **Phase 1: Basic Matching
- [X] Implement basic resume parsing.
- [X] Extract skills and qualifications.
- [X] Match resumes with job descriptions.

### **Phase 2: Optimization Features**
- [X] Suggest missing keywords.
- [X] Provide real-time resume feedback.
- [X] Improve matching accuracy.

### **Phase 3: Advanced Job Matching**
- [X] Integrate job listing APIs.
- [X] Implement a scoring system for job fit.
- [X] Add UI for job seekers.

### **Phase 4: Scaling & Enhancements**
- [X] Support multi-language resumes.
- [X] Enable recruiter dashboard.
- [X] Deploy as a web application.

### **Possible Phase 5: Integrate new API
- [ ] Use APIJobs, an API that scraps all jobs in the world!

## Contributing
Contributions are welcome! Follow these steps:
1. **Fork the repository**.
2. **Create a new branch**:
   ```bash
   git checkout -b feature-name
   ```
3. **Make changes and commit**:
   ```bash
   git commit -am "Add new feature"
   ```
4. **Push your branch**:
   ```bash
   git push origin feature-name
   ```
5. **Create a pull request**.

## License
This project is licensed under the **Apache 2.0 License**.

---

### **🚀 Get Started Today and Find Your Perfect Job Fit!**

## Setting up Job Listing API Integration
To set up the job listing API integration, follow these steps:

1. **Obtain API Key**: Register on the job listing API provider's website and obtain an API key.
2. **Configure API Key**: Add the API key to your environment variables or configuration file.
3. **Update API URL**: Ensure the API URL in the code points to the correct endpoint.
4. **Run the Application**: Execute the application to fetch and display job listings from the API.

## Deploying as a Web Application
To deploy JobFit as a web application, follow these steps:

1. **Build the application**:
   ```bash
   mvn clean package
   ```
2. **Run the application**:
   ```bash
   java -jar target/jobfit-jar-with-dependencies.jar (does not work)
   ```
3. **Access the web application**:
   Open your web browser and navigate to `http://localhost:8080`.

## Multi-Language Resume Support
JobFit now supports resumes in multiple languages. The application will automatically detect the language of the resume and translate it to English for analysis. Currently, the following languages are supported:
- English
- Spanish
- French
- German

## Recruiter Dashboard
JobFit includes a recruiter dashboard that allows recruiters to manage job postings and view candidate matches. To access the recruiter dashboard, follow these steps:

1. **Log in to the web application**.
2. **Navigate to the recruiter dashboard**.
3. **Manage job postings and view candidate matches**.
