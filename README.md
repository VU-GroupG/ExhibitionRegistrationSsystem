# ExhibitionRegistrationSsystem
# Victoria University Exhibition Registration System

## Project Overview

The Exhibition Registration System is a Java-based desktop application designed for Victoria University's Faculty of Science and Technology to manage their annual Innovation and Technology Exhibition. The system provides a comprehensive GUI interface for registering, managing, and tracking exhibition participants.

## System Features

### Core Functionality
- **Participant Registration**: Complete registration form with validation
- **CRUD Operations**: Create, Read, Update, Delete participant records
- **Image Management**: Upload and display project prototype images
- **Database Integration**: Seamless integration with Microsoft Access database
- **Input Validation**: Comprehensive validation for data integrity
- **Search Functionality**: Quick participant lookup by Registration ID

### Technical Components
- **GUI Framework**: Java Swing with professional layout
- **Database**: Microsoft Access (VUE_Exhibition.accdb)
- **JDBC Driver**: UCanAccess for Access database connectivity
- **Image Handling**: JFileChooser for file selection and display
- **Security**: PreparedStatements for SQL injection prevention

## System Architecture

### Database Schema

```sql
CREATE TABLE Participants (
    RegistrationID VARCHAR(20) PRIMARY KEY,
    StudentName VARCHAR(100) NOT NULL,
    Faculty VARCHAR(100) NOT NULL,
    ProjectTitle VARCHAR(200) NOT NULL,
    ContactNumber VARCHAR(20) NOT NULL,
    EmailAddress VARCHAR(100) NOT NULL,
    ImagePath VARCHAR(500),
    DateRegistered DATETIME DEFAULT NOW()
)
```

### Class Structure

**Main Application Class**: `ExhibitionRegistrationSystem`
- Extends JFrame for GUI functionality
- Implements database connectivity and CRUD operations
- Handles image processing and display
- Manages input validation and error handling

**Database Setup Class**: `DatabaseSetup`
- Creates database table structure
- Populates initial sample data
- Provides utility methods for database testing

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)** - Version 8 or higher
2. **UCanAccess JDBC Driver** - For Access database connectivity
3. **Microsoft Access Database** - VUE_Exhibition.accdb file

### Installation Steps

1. **Download Required Libraries**:
   ```
   - ucanaccess-5.0.1.jar
   - commons-lang3-3.8.1.jar
   - commons-logging-1.2.jar
   - hsqldb-2.5.0.jar
   - jackcess-3.0.1.jar
   ```

2. **Setup Database**:
   - Create or place VUE_Exhibition.accdb in the project root directory
   - Run DatabaseSetup.java to create tables and sample data

3. **Compile and Run**:
   ```bash
   javac -cp ".:ucanaccess-5.0.1.jar:commons-lang3-3.8.1.jar:commons-logging-1.2.jar:hsqldb-2.5.0.jar:jackcess-3.0.1.jar" ExhibitionRegistrationSystem.java
   
   java -cp ".:ucanaccess-5.0.1.jar:commons-lang3-3.8.1.jar:commons-logging-1.2.jar:hsqldb-2.5.0.jar:jackcess-3.0.1.jar" ExhibitionRegistrationSystem
   ```

## Input Validation Implementation

### Validation Rules

1. **Registration ID Validation**:
   ```java
   // Alphanumeric characters only
   if (!regId.matches("^[A-Za-z0-9]+$")) {
       showValidationError("Registration ID should contain only letters and numbers.");
       return false;
   }
   ```

2. **Contact Number Validation**:
   ```java
   // 10-15 digits, optional + prefix
   if (!contact.matches("^[+]?[0-9]{10,15}$")) {
       showValidationError("Contact Number should contain 10-15 digits (+ allowed at start).");
       return false;
   }
   ```

3. **Email Validation**:
   ```java
   // Standard email format validation
   String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
   if (!Pattern.matches(emailRegex, email)) {
       showValidationError("Please enter a valid email address.");
       return false;
   }
   ```

4. **Empty Field Validation**:
   - All required fields checked for empty or whitespace-only content
   - Faculty selection validated (cannot be "Select Faculty")
   - User-friendly error messages with field focus

## Database Operations

### Create (Register Participant)
```java
String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, " +
            "ContactNumber, EmailAddress, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    // Set parameters and execute
    pstmt.setString(1, txtRegistrationId.getText().trim());
    // ... additional parameters
    int result = pstmt.executeUpdate();
}
```

### Read (Search Participant)
```java
String sql = "SELECT * FROM Participants WHERE RegistrationID = ?";

try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    pstmt.setString(1, regId);
    ResultSet rs = pstmt.executeQuery();
    // Process results and populate form
}
```

### Update (Modify Participant)
```java
String sql = "UPDATE Participants SET StudentName = ?, Faculty = ?, ProjectTitle = ?, " +
            "ContactNumber = ?, EmailAddress = ?, ImagePath = ? WHERE RegistrationID = ?";
```

### Delete (Remove Participant)
```java
String sql = "DELETE FROM Participants WHERE RegistrationID = ?";
// Includes confirmation dialog for user safety
```

## Image Handling

### File Selection
```java
JFileChooser fileChooser = new JFileChooser();
FileNameExtensionFilter filter = new FileNameExtensionFilter(
    "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
fileChooser.setFileFilter(filter);
```

### Image Display
```java
private void displayImage(String imagePath) {
    try {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        lblImageDisplay.setIcon(new ImageIcon(image));
    } catch (Exception e) {
        lblImageDisplay.setText("Image Load Error");
    }
}
```

## Error Handling

### Database Error Handling
- **Connection Errors**: Graceful handling with user notification
- **SQL Exceptions**: Specific error messages for duplicate entries
- **Data Integrity**: PreparedStatements prevent SQL injection

### File Handling Errors
- **Image Loading**: Try-catch blocks with fallback display
- **File Access**: Validation for file existence and permissions

### Input Validation Errors
- **Real-time Validation**: Immediate feedback on invalid input
- **Field Focus**: Automatic focus on problematic fields
- **Clear Error Messages**: User-friendly validation messages

## Security Features

1. **SQL Injection Prevention**: All database operations use PreparedStatements
2. **Input Sanitization**: Trim and validate all user inputs
3. **File Type Restriction**: Image file type filtering for uploads
4. **Confirmation Dialogs**: Delete operations require user confirmation

## Data Consistency

### GUI-Database Synchronization
- **Immediate Updates**: Changes reflected immediately in database
- **Transaction Handling**: Proper commit/rollback for database operations
- **Error Recovery**: Failed operations don't corrupt existing data

### Form State Management
- **Clear Function**: Resets all fields to default state
- **Search Results**: Populate form with exact database values
- **Update Operations**: Validate existence before modification

## Sample Data

The system includes 5 sample participants:

1. **REG001** - Alice Johnson (Computer Science) - AI-Powered Study Assistant
2. **REG002** - Bob Smith (Information Technology) - Smart Campus Navigation System
3. **REG003** - Carol Brown (Software Engineering) - Student Performance Analytics Platform
4. **REG004** - David Wilson (Electrical Engineering) - IoT-Based Energy Management System
5. **REG005** - Emma Davis (Applied Sciences) - Environmental Monitoring Drone

## GUI Design Features

### Layout Management
- **GridBagLayout**: Flexible and professional form layout
- **BorderLayout**: Organized panel structure
- **FlowLayout**: Intuitive button arrangement

### Visual Design
- **Professional Color Scheme**: University branding colors
- **Consistent Typography**: Arial font family throughout
- **Visual Feedback**: Status messages and error dialogs
- **Image Preview**: Real-time image display functionality

## Troubleshooting

### Common Issues

1. **Database Connection Failed**:
   - Verify VUE_Exhibition.accdb file location
   - Check UCanAccess JAR files in classpath
   - Ensure database file permissions

2. **Image Not Displaying**:
   - Verify supported image formats (JPG, PNG, GIF, BMP)
   - Check file path accessibility
   - Ensure image file is not corrupted

3. **Validation Errors**:
   - Check email format (must include @ and domain)
   - Verify contact number (10-15 digits)
   - Ensure Registration ID is alphanumeric

## Future Enhancements

1. **Advanced Search**: Multi-field search capabilities
2. **Report Generation**: PDF reports of participants
3. **Data Export**: Excel/CSV export functionality
4. **Backup System**: Automated database backup
5. **User Authentication**: Login system for administrators
6. **Audit Trail**: Track all database modifications

## Conclusion

The Exhibition Registration System successfully addresses Victoria University's need for a digital solution to manage exhibition participants. The system provides a robust, user-friendly interface with comprehensive validation, secure database operations, and professional presentation suitable for university administrative use.
