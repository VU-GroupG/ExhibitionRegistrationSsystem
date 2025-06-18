package com.vu.exhibition;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.regex.Pattern;

/**
 * Victoria University Exhibition Registration System
 * Group Project - 5 Members
 * 
 * @author [Team Member Names]
 * Member 1: Database & Coordination - Ssentongo Trevor]
 * Member 2: GUI Design & Layout - [Busingye Colline]
 * Member 3: CRUD Operations - [Katongole Denis]
 * Member 4: Validation & Security - [Mugamba Enock]
 * Member 5: File Handling & Testing - [Katushabe Margret]
 */
public class ExhibitionRegistrationSystem extends JFrame {
    
    // Database connection details - Member 1 responsibility
    private static final String DB_URL = "jdbc:ucanaccess://database/VUE_Exhibition.accdb;newdatabaseversion=V2010";
    private Connection connection;
    
    // GUI Components - Member 2 responsibility
    private JTextField txtRegistrationId;
    private JTextField txtStudentName;
    private JComboBox<String> cmbFaculty;
    private JTextField txtProjectTitle;
    private JTextField txtContactNumber;
    private JTextField txtEmailAddress;
    private JLabel lblImageDisplay;
    private JTextField txtImagePath;
    private JButton btnChooseImage;
    
    // Action Buttons - Member 2 responsibility
    private JButton btnRegister;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnExit;
    
    // Image handling - Member 5 responsibility
    private String selectedImagePath = "";
    
    public ExhibitionRegistrationSystem() {
        initializeDatabase();  // Member 1
        initializeGUI();       // Member 2
        setupEventHandlers();  // All members
    }
    
    /**
     * Initialize database connection
     * @author Member 1 - Database Architect
     */
    private void initializeDatabase() {
         try {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        connection = DriverManager.getConnection(DB_URL);
        System.out.println("Database connected successfully!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Database connection failed: " + e.getMessage(),
            "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize the GUI components and layout
     * @author Member 2 - GUI Designer
     */
    private void initializeGUI() {
        // TODO: Member 2 - Implement GUI layout
        // - Set up main window properties
        // - Create title panel
        // - Add form panel and button panel
        // - Apply university branding
        
        setTitle("Victoria University - Exhibition Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel with title
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();  // Member 2 to implement
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();    // Member 2 to implement
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel(); // Member 2 to implement
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Window properties
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Create title panel with university branding
     * @author Member 2 - GUI Designer
     */
    private JPanel createTitlePanel() {
        // TODO: Member 2 - Create professional title panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 51, 102)); // University blue
        JLabel titleLabel = new JLabel("Innovation & Technology Exhibition Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Create the form panel with input fields
     * @author Member 2 - GUI Designer
     */
    private JPanel createFormPanel() {
        // TODO: Member 2 - Implement professional form layout
        // - Use GridBagLayout for proper alignment
        // - Create all input fields
        // - Add proper labels and spacing
        // - Implement image preview area
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Participant Registration Form",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
        
        // TODO: Add all form fields here
        // GridBagConstraints for professional layout
        
        return panel;
    }
    
    /**
     * Create the button panel
     * @author Member 2 - GUI Designer
     */
    private JPanel createButtonPanel() {
        // TODO: Member 2 - Create styled button panel
        // - Professional button styling
        // - Proper spacing and alignment
        // - Hover effects and colors
        
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        
        // Initialize all buttons
        btnRegister = new JButton("Register");
        btnSearch = new JButton("Search");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");
        
        // TODO: Add styling and add buttons to panel
        
        return panel;
    }
    
    /**
     * Setup event handlers for all buttons
     * @author All Members - Each implements their assigned methods
     */
    private void setupEventHandlers() {
        btnRegister.addActionListener(e -> registerParticipant());  // Member 3
        btnSearch.addActionListener(e -> searchParticipant());      // Member 3
        btnUpdate.addActionListener(e -> updateParticipant());      // Member 3
        btnDelete.addActionListener(e -> deleteParticipant());      // Member 3
        btnClear.addActionListener(e -> clearForm());               // Member 5
        btnExit.addActionListener(e -> exitApplication());          // Member 5
        btnChooseImage.addActionListener(e -> chooseImage());       // Member 5
    }
    
    /**
     * Register a new participant
     * @author Member 3 - CRUD Operations Developer
     */
    private void registerParticipant() {
        // TODO: Member 3 - Implement participant registration
        // - Validate input first (call Member 4's validation)
        // - Create PreparedStatement for INSERT
        // - Handle SQL exceptions
        // - Show success/error messages
        // - Clear form on success
        
        if (!validateInput()) return; // Member 4's validation
        
        String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, " +
                    "ContactNumber, EmailAddress, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // TODO: Implement database insertion
    }
    
    /**
     * Search for a participant by Registration ID
     * @author Member 3 - CRUD Operations Developer
     */
    private void searchParticipant() {
        // TODO: Member 3 - Implement participant search
        // - Get Registration ID from form
        // - Create PreparedStatement for SELECT
        // - Populate form with results
        // - Handle "not found" cases
        
    }
    
    /**
     * Update participant details
     * @author Member 3 - CRUD Operations Developer
     */
    private void updateParticipant() {
        // TODO: Member 3 - Implement participant update
        // - Validate input first
        // - Create PreparedStatement for UPDATE
        // - Handle cases where participant doesn't exist
        
    }
    
    /**
     * Delete a participant
     * @author Member 3 - CRUD Operations Developer
     */
    private void deleteParticipant() {
        // TODO: Member 3 - Implement participant deletion
        // - Show confirmation dialog
        // - Create PreparedStatement for DELETE
        // - Clear form after successful deletion
        
    }
    
    /**
     * Validate all input fields
     * @author Member 4 - Validation & Security Specialist
     */
    private boolean validateInput() {
        // TODO: Member 4 - Implement comprehensive validation
        // - Check for empty required fields
        // - Validate Registration ID format (alphanumeric)
        // - Validate email format with regex
        // - Validate contact number format
        // - Show specific error messages for each validation failure
        // - Return true if all validation passes
        
        return true; // Placeholder
    }
    
    /**
     * Show validation error message
     * @author Member 4 - Validation & Security Specialist
     */
    private void showValidationError(String message) {
        // TODO: Member 4 - Implement user-friendly error display
        JOptionPane.showMessageDialog(this, message, 
            "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Choose and display image file
     * @author Member 5 - File Handling & Testing Coordinator
     */
    private void chooseImage() {
        // TODO: Member 5 - Implement image file selection
        // - Create JFileChooser with image filters
        // - Handle file selection
        // - Update image path and display preview
        
    }
    
    /**
     * Display selected image in the label
     * @author Member 5 - File Handling & Testing Coordinator
     */
    private void displayImage(String imagePath) {
        // TODO: Member 5 - Implement image display
        // - Load image from file path
        // - Scale image to fit preview area
        // - Handle image loading errors gracefully
        
    }
    
    /**
     * Clear all form fields
     * @author Member 5 - File Handling & Testing Coordinator
     */
    private void clearForm() {
        // TODO: Member 5 - Implement form clearing
        // - Reset all text fields
        // - Reset combo box selection
        // - Clear image display
        // - Reset selected image path
        
    }
    
    /**
     * Exit the application
     * @author Member 5 - File Handling & Testing Coordinator
     */
    private void exitApplication() {
        // TODO: Member 5 - Implement graceful application exit
        // - Show confirmation dialog
        // - Close database connection properly
        // - Exit application
        
    }
    
    /**
     * Main method to start the application
     * @author All Members
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExhibitionRegistrationSystem().setVisible(true);
            }
        });
    }
}
