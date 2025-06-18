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

//validation and security
/**
 * Validate all input fields
 * @author Member 4 - Validation & Security Specialist
 */
private boolean validateInput() {
    // Check for empty Registration ID
    if (txtRegistrationId.getText().trim().isEmpty()) {
        showValidationError("Registration ID is required.");
        txtRegistrationId.requestFocus();
        return false;
    }
    
    // Check for empty Student Name
    if (txtStudentName.getText().trim().isEmpty()) {
        showValidationError("Student Name is required.");
        txtStudentName.requestFocus();
        return false;
    }
    
    // Check Faculty selection
    if (cmbFaculty.getSelectedIndex() == 0) {
        showValidationError("Please select a Faculty.");
        cmbFaculty.requestFocus();
        return false;
    }
    
    // Check for empty Project Title
    if (txtProjectTitle.getText().trim().isEmpty()) {
        showValidationError("Project Title is required.");
        txtProjectTitle.requestFocus();
        return false;
    }
    
    // Check for empty Contact Number
    if (txtContactNumber.getText().trim().isEmpty()) {
        showValidationError("Contact Number is required.");
        txtContactNumber.requestFocus();
        return false;
    }
    
    // Check for empty Email Address
    if (txtEmailAddress.getText().trim().isEmpty()) {
        showValidationError("Email Address is required.");
        txtEmailAddress.requestFocus();
        return false;
    }
    
    // Validate Registration ID format (alphanumeric only)
    String regId = txtRegistrationId.getText().trim();
    if (!regId.matches("^[A-Za-z0-9]+$")) {
        showValidationError("Registration ID should contain only letters and numbers.\n" +
                          "Valid examples: REG001, ST123, PROJ2024\n" +
                          "Invalid examples: REG-001, REG 001, REG@001");
        txtRegistrationId.requestFocus();
        return false;
    }
    
    // Validate contact number format (10-15 digits, optional + prefix)
    String contact = txtContactNumber.getText().trim();
    if (!contact.matches("^[+]?[0-9]{10,15}$")) {
        showValidationError("Contact Number should contain 10-15 digits (+ allowed at start).\n" +
                          "Valid examples: +256701234567, 0701234567, 256701234567\n" +
                          "Invalid examples: 123, abc123, +256-701-234-567");
        txtContactNumber.requestFocus();
        return false;
    }
    
    // Validate email format
    String email = txtEmailAddress.getText().trim();
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    if (!Pattern.matches(emailRegex, email)) {
        showValidationError("Please enter a valid email address.\n" +
                          "Valid examples: student@vu.ac.ug, john.doe@gmail.com\n" +
                          "Invalid examples: student@, @vu.ac.ug, studentvu.ac.ug");
        txtEmailAddress.requestFocus();
        return false;
    }
    
    // Validate Student Name (should not contain numbers or special characters)
    String studentName = txtStudentName.getText().trim();
    if (!studentName.matches("^[A-Za-z\\s]+$")) {
        showValidationError("Student Name should contain only letters and spaces.\n" +
                          "Valid examples: John Doe, Alice Johnson\n" +
                          "Invalid examples: John123, Alice@Smith");
        txtStudentName.requestFocus();
        return false;
    }
    
    // Validate Project Title (should not be too short)
    String projectTitle = txtProjectTitle.getText().trim();
    if (projectTitle.length() < 5) {
        showValidationError("Project Title should be at least 5 characters long.");
        txtProjectTitle.requestFocus();
        return false;
    }
    
    // All validations passed
    return true;
}

/**
 * Show validation error message with detailed information
 * @author Member 4 - Validation & Security Specialist
 */
private void showValidationError(String message) {
    JOptionPane.showMessageDialog(this, 
        message, 
        "Validation Error", 
        JOptionPane.ERROR_MESSAGE);
}

/**
 * Additional validation helper method for Registration ID uniqueness
 * @author Member 4 - Validation & Security Specialist
 */
private boolean isRegistrationIdUnique(String registrationId) {
    String sql = "SELECT COUNT(*) FROM Participants WHERE RegistrationID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, registrationId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) == 0; // Returns true if count is 0 (unique)
        }
    } catch (SQLException e) {
        System.err.println("Error checking Registration ID uniqueness: " + e.getMessage());
    }
    return false;
}

/**
 * Additional security method to sanitize input
 * @author Member 4 - Validation & Security Specialist
 */
private String sanitizeInput(String input) {
    if (input == null) return "";
    // Remove leading/trailing whitespace and potential SQL injection characters
    return input.trim().replaceAll("[';\"\\\\]", "");
}

//GUI
/**
 * Create the form panel with input fields
 * @author Member 2 - GUI Designer
 */
private JPanel createFormPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Participant Registration Form",
        TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    
    // Registration ID
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Registration ID:"), gbc);
    gbc.gridx = 1;
    txtRegistrationId = new JTextField(20);
    panel.add(txtRegistrationId, gbc);
    
    // Student Name
    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Student Name:"), gbc);
    gbc.gridx = 1;
    txtStudentName = new JTextField(20);
    panel.add(txtStudentName, gbc);
    
    // Faculty
    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Faculty:"), gbc);
    gbc.gridx = 1;
    String[] faculties = {
        "Select Faculty",
        "Computer Science",
        "Information Technology",
        "Software Engineering",
        "Electrical Engineering",
        "Civil Engineering",
        "Mechanical Engineering",
        "Applied Sciences"
    };
    cmbFaculty = new JComboBox<>(faculties);
    panel.add(cmbFaculty, gbc);
    
    // Project Title
    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(new JLabel("Project Title:"), gbc);
    gbc.gridx = 1;
    txtProjectTitle = new JTextField(20);
    panel.add(txtProjectTitle, gbc);
    
    // Contact Number
    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(new JLabel("Contact Number:"), gbc);
    gbc.gridx = 1;
    txtContactNumber = new JTextField(20);
    panel.add(txtContactNumber, gbc);
    
    // Email Address
    gbc.gridx = 0; gbc.gridy = 5;
    panel.add(new JLabel("Email Address:"), gbc);
    gbc.gridx = 1;
    txtEmailAddress = new JTextField(20);
    panel.add(txtEmailAddress, gbc);
    
    // Image Selection
    gbc.gridx = 0; gbc.gridy = 6;
    panel.add(new JLabel("Project Image:"), gbc);
    gbc.gridx = 1;
    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    txtImagePath = new JTextField(15);
    txtImagePath.setEditable(false);
    btnChooseImage = new JButton("Browse");
    imagePanel.add(txtImagePath);
    imagePanel.add(btnChooseImage);
    panel.add(imagePanel, gbc);
    
    // Image Display
    gbc.gridx = 0; gbc.gridy = 7;
    panel.add(new JLabel("Image Preview:"), gbc);
    gbc.gridx = 1;
    lblImageDisplay = new JLabel();
    lblImageDisplay.setPreferredSize(new Dimension(150, 150));
    lblImageDisplay.setBorder(BorderFactory.createEtchedBorder());
    lblImageDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    lblImageDisplay.setText("No Image Selected");
    panel.add(lblImageDisplay, gbc);
    
    return panel;
}

/**
 * Create the button panel
 * @author Member 2 - GUI Designer
 */
private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout());
    panel.setBorder(BorderFactory.createTitledBorder("Actions"));
    
    btnRegister = new JButton("Register");
    btnSearch = new JButton("Search");
    btnUpdate = new JButton("Update");
    btnDelete = new JButton("Delete");
    btnClear = new JButton("Clear");
    btnExit = new JButton("Exit");
    
    // Style buttons
    Color buttonColor = new Color(0, 102, 204);
    Font buttonFont = new Font("Arial", Font.BOLD, 12);
    
    JButton[] buttons = {btnRegister, btnSearch, btnUpdate, btnDelete, btnClear, btnExit};
    for (JButton btn : buttons) {
        btn.setBackground(buttonColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(buttonFont);
        btn.setPreferredSize(new Dimension(100, 30));
        panel.add(btn);
    }
    
    btnExit.setBackground(new Color(204, 0, 0));
    
    return panel;
}

