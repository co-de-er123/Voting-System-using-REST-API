# **Voting System REST API**

## **Features Implemented**

### **Candidate Registration**
- Register new candidates with an initial vote count of 0.
- Duplicate candidate names are prevented.
- Name validation and normalization for consistent handling.

### **Vote Management**
- Cast votes for registered candidates.
- Thread-safe vote counting to handle concurrent requests.
- Real-time vote count updates after each vote.
- Invalid candidate validation to prevent voting for unregistered candidates.

### **Vote Querying**
- Get individual candidate vote counts.
- List all candidates with their current vote counts.
- Determine the winning candidate based on the highest number of votes.

### **Concurrent Operation Support**
- Thread-safe data structures (`ConcurrentHashMap`) for managing vote data.
- Atomic operations for vote counting.
- Handles concurrent access without data corruption.

### **Input Validation**
- Null and empty name checking for proper validation.
- Case-insensitive name matching for consistent querying.
- Proper error responses for invalid operations.

---

## **API Usage Guide**

### **Base URL**

http://localhost:8080


### **Endpoints**

#### **Register a Candidate**
- **POST** `/entercandidate?name={candidateName}`
- Example: `http://localhost:8080/entercandidate?name=ajay`
- **Response**: `"Candidate ajay registered successfully"`

#### **Cast a Vote**
- **POST** `/castvote?name={candidateName}`
- Example: `http://localhost:8080/castvote?name=ajay`
- **Response**: `"Vote cast successfully. Current count: 1"`

#### **Get Vote Count**
- **GET** `/countvote?name={candidateName}`
- Example: `http://localhost:8080/countvote?name=ajay`
- **Response**: `1`

#### **List All Votes**
- **GET** `/listvote`
- Example: `http://localhost:8080/listvote`
- **Response**: `[{"name":"ajay","votes":1}]`

#### **Get Winner**
- **GET** `/getwinner`
- Example: `http://localhost:8080/getwinner`
- **Response**: `"ajay with 1 votes"`

---

## **Setup Instructions**

### **Prerequisites**
- Java 11 or higher
- Maven 3.6 or higher

### **Build and Run**

1. Clone the repository:

    ```bash
    git clone [repository-url]
    ```

2. Navigate to the project directory:

    ```bash
    cd voting-system
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

---

## **Testing**

To run unit tests:
mvn test



## **Error Handling
All endpoints return appropriate HTTP status codes:
400 Bad Request for invalid inputs (e.g., invalid candidate name, empty name).
200 OK for successful operations.
Error messages are returned in the response body to provide more information about the issue.
