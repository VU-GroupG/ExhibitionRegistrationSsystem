package com.mycompany.exhibitionregistrationsystem;

import java.sql.*;

/**
 * Database Setup Utility for Exhibition Registration System
 * This class creates the database table and populates it with sample data
 * 
 * @author SSENIONGO TREVOR
 */
public class DatabaseSetup {
    
   private static final String DB_URL = "jdbc:ucanaccess://database/VUE_Exhibition.accdb;newdatabaseversion=V2010";
    public static void main(String[] args) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection(DB_URL);
            
            System.out.println("Connected to database successfully!");
            
            // Create table
            createTable(connection);
            
            // Insert sample data
            insertSampleData(connection);
            
            connection.close();
            System.out.println("Database setup completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Database setup failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create the Participants table
     */
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = """
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
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'Participants' created successfully!");
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Table 'Participants' already exists.");
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Insert sample data into the Participants table
     */
    private static void insertSampleData(Connection connection) throws SQLException {
        String insertSQL = """
            INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, 
                                    ContactNumber, EmailAddress, ImagePath) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        
        // Sample data
        Object[][] sampleData = {
            {"REG001", "Ssentongo Trevor", "Computer Science", "AI-Powered Study Assistant", "+256701234567", "alice.johnson@vu.ac.ug", ""},
            {"REG002", "Katongole Denis", "Information Technology", "Smart Campus Navigation System", "+256702345678", "bob.smith@vu.ac.ug", ""},
            {"REG003", "Katushabe Margret", "Software Engineering", "Student Performance Analytics Platform", "+256703456789", "carol.brown@vu.ac.ug", ""},
            {"REG004", "Higenyi Tobias", "Electrical Engineering", "IoT-Based Energy Management System", "+256704567890", "david.wilson@vu.ac.ug", ""},
            {"REG005", "Taremwa JohnBosco", "Applied Sciences", "Environmental Monitoring Drone", "+256705678901", "emma.davis@vu.ac.ug", ""}
        };
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            for (Object[] row : sampleData) {
                for (int i = 0; i < row.length; i++) {
                    pstmt.setString(i + 1, row[i].toString());
                }
                try {
                    pstmt.executeUpdate();
                    System.out.println("Inserted participant: " + row[1]);
                } catch (SQLException e) {
                    if (e.getMessage().contains("duplicate")) {
                        System.out.println("Participant " + row[0] + " already exists, skipping...");
                    } else {
                        throw e;
                    }
                }
            }
        }
        
        System.out.println("Sample data insertion completed!");
    }
}
