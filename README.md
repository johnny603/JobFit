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
   java -jar target/jobfit-jar-with-dependencies.jar (does not work)
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

### Prerequisites
- Java 11 or higher installed
- Maven installed
- Git (for cloning the repository)

### Step 1: Clone and Navigate
```bash
git clone https://github.com/your-username/jobfit.git
cd jobfit
```

### Step 2: Build the Application
```bash
mvn clean package
```
This will create an executable JAR file in the `target` directory.

### Step 3: Run the Application
```bash
java -jar target/jobfit-0.1.0-SNAPSHOT.jar (now leads to error page but localhost works)
```

### Step 4: Access the Application
Once the application is running, you can access it through your web browser:
- Open your web browser
- Navigate to `http://localhost:8080`

### Available Endpoints
The application provides the following REST endpoints:
- `POST /api/analyze` - Analyze job fit between resume and job description
  - Parameters: 
    - `resume`: Path to resume file
    - `jobDescription`: Path to job description file
- `GET /api/recommendations` - Get job recommendations based on match score
  - Parameters:
    - `matchScore`: Match score value

### Troubleshooting
If you encounter any issues:
1. Ensure Java 11+ is installed and properly configured
2. Verify Maven is installed and in your PATH
3. Check that port 8080 is not in use by another application
4. Review the application logs for any error messages

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
