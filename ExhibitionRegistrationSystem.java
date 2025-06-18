/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.exhibitionregistrationsystem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.regex.Pattern;

/**
 * Exhibition Registration System
 * Victoria University Faculty of Science and Technology
 * Annual Innovation and Technology Exhibition Management System
 * 
 * @author SSENIONGO TREVOR
 */
public class ExhibitionRegistrationSystem extends JFrame {
    
    // Database connection details
    private static final String DB_URL = "jdbc:ucanaccess://database/VUE_Exhibition.accdb;newdatabaseversion=V2010";
    private Connection connection;
    
    // GUI Components
    private JTextField txtRegistrationId;
    private JTextField txtStudentName;
    private JComboBox<String> cmbFaculty;
    private JTextField txtProjectTitle;
    private JTextField txtContactNumber;
    private JTextField txtEmailAddress;
    private JLabel lblImageDisplay;
    private JTextField txtImagePath;
    private JButton btnChooseImage;
    
    // Action Buttons
    private JButton btnRegister;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnExit;
    
    // Image handling
    private String selectedImagePath = "";
    
    public ExhibitionRegistrationSystem() {
        initializeDatabase();
        initializeGUI();
        setupEventHandlers();
    }
    
    /**
     * Initialize database connection
     */
    private void initializeDatabase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Initialize the GUI components and layout
     */
    private void initializeGUI() {
        setTitle("Victoria University - Exhibition Registration System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel with title
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 51, 102));
        JLabel titleLabel = new JLabel("Innovation & Technology Exhibition Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Window properties
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Create the form panel with input fields
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
    
    /**
     * Setup event handlers for all buttons
     */
    private void setupEventHandlers() {
        btnRegister.addActionListener(e -> registerParticipant());
        btnSearch.addActionListener(e -> searchParticipant());
        btnUpdate.addActionListener(e -> updateParticipant());
        btnDelete.addActionListener(e -> deleteParticipant());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> exitApplication());
        btnChooseImage.addActionListener(e -> chooseImage());
    }
    
    /**
     * Register a new participant
     */
    private void registerParticipant() {
        if (!validateInput()) return;
        
        String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, " +
                    "ContactNumber, EmailAddress, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, txtRegistrationId.getText().trim());
            pstmt.setString(2, txtStudentName.getText().trim());
            pstmt.setString(3, cmbFaculty.getSelectedItem().toString());
            pstmt.setString(4, txtProjectTitle.getText().trim());
            pstmt.setString(5, txtContactNumber.getText().trim());
            pstmt.setString(6, txtEmailAddress.getText().trim());
            pstmt.setString(7, selectedImagePath);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Participant registered successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate")) {
                JOptionPane.showMessageDialog(this, 
                    "Registration ID already exists. Please use a different ID.",
                    "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error registering participant: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Search for a participant by Registration ID
     */
    private void searchParticipant() {
        String regId = txtRegistrationId.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter Registration ID to search.",
                "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String sql = "SELECT * FROM Participants WHERE RegistrationID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, regId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                txtStudentName.setText(rs.getString("StudentName"));
                cmbFaculty.setSelectedItem(rs.getString("Faculty"));
                txtProjectTitle.setText(rs.getString("ProjectTitle"));
                txtContactNumber.setText(rs.getString("ContactNumber"));
                txtEmailAddress.setText(rs.getString("EmailAddress"));
                
                String imagePath = rs.getString("ImagePath");
                if (imagePath != null && !imagePath.isEmpty()) {
                    selectedImagePath = imagePath;
                    txtImagePath.setText(imagePath);
                    displayImage(imagePath);
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Participant found and data loaded!",
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No participant found with Registration ID: " + regId,
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error searching participant: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update participant details
     */
    private void updateParticipant() {
        if (!validateInput()) return;
        
        String sql = "UPDATE Participants SET StudentName = ?, Faculty = ?, ProjectTitle = ?, " +
                    "ContactNumber = ?, EmailAddress = ?, ImagePath = ? WHERE RegistrationID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, txtStudentName.getText().trim());
            pstmt.setString(2, cmbFaculty.getSelectedItem().toString());
            pstmt.setString(3, txtProjectTitle.getText().trim());
            pstmt.setString(4, txtContactNumber.getText().trim());
            pstmt.setString(5, txtEmailAddress.getText().trim());
            pstmt.setString(6, selectedImagePath);
            pstmt.setString(7, txtRegistrationId.getText().trim());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Participant details updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No participant found with the given Registration ID.",
                    "Update Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error updating participant: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete a participant
     */
    private void deleteParticipant() {
        String regId = txtRegistrationId.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter Registration ID to delete.",
                "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete participant with ID: " + regId + "?",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM Participants WHERE RegistrationID = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, regId);
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Participant deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No participant found with the given Registration ID.",
                        "Delete Failed", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error deleting participant: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Clear all form fields
     */
    private void clearForm() {
        txtRegistrationId.setText("");
        txtStudentName.setText("");
        cmbFaculty.setSelectedIndex(0);
        txtProjectTitle.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        txtImagePath.setText("");
        selectedImagePath = "";
        lblImageDisplay.setIcon(null);
        lblImageDisplay.setText("No Image Selected");
    }
    
    /**
     * Choose and display image file
     */
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Project Image");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            txtImagePath.setText(selectedFile.getName());
            displayImage(selectedImagePath);
        }
    }
    
    /**
     * Display selected image in the label
     */
    private void displayImage(String imagePath) {
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblImageDisplay.setIcon(new ImageIcon(image));
            lblImageDisplay.setText("");
        } catch (Exception e) {
            lblImageDisplay.setIcon(null);
            lblImageDisplay.setText("Image Load Error");
        }
    }
    
    /**
     * Validate all input fields
     */
    private boolean validateInput() {
        // Check for empty fields
        if (txtRegistrationId.getText().trim().isEmpty()) {
            showValidationError("Registration ID is required.");
            txtRegistrationId.requestFocus();
            return false;
        }
        
        if (txtStudentName.getText().trim().isEmpty()) {
            showValidationError("Student Name is required.");
            txtStudentName.requestFocus();
            return false;
        }
        
        if (cmbFaculty.getSelectedIndex() == 0) {
            showValidationError("Please select a Faculty.");
            cmbFaculty.requestFocus();
            return false;
        }
        
        if (txtProjectTitle.getText().trim().isEmpty()) {
            showValidationError("Project Title is required.");
            txtProjectTitle.requestFocus();
            return false;
        }
        
        if (txtContactNumber.getText().trim().isEmpty()) {
            showValidationError("Contact Number is required.");
            txtContactNumber.requestFocus();
            return false;
        }
        
        if (txtEmailAddress.getText().trim().isEmpty()) {
            showValidationError("Email Address is required.");
            txtEmailAddress.requestFocus();
            return false;
        }
        
        // Validate Registration ID format (assuming alphanumeric)
        String regId = txtRegistrationId.getText().trim();
        if (!regId.matches("^[A-Za-z0-9]+$")) {
            showValidationError("Registration ID should contain only letters and numbers.");
            txtRegistrationId.requestFocus();
            return false;
        }
        
        // Validate contact number format
        String contact = txtContactNumber.getText().trim();
        if (!contact.matches("^[+]?[0-9]{10,15}$")) {
            showValidationError("Contact Number should contain 10-15 digits (+ allowed at start).");
            txtContactNumber.requestFocus();
            return false;
        }
        
        // Validate email format
        String email = txtEmailAddress.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            showValidationError("Please enter a valid email address.");
            txtEmailAddress.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Show validation error message
     */
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, 
            "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Exit the application
     */
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to exit the application?",
            "Confirm Exit", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
            System.exit(0);
        }
    }
    
    /**
     * Main method to start the application
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExhibitionRegistrationSystem().setVisible(true);
        });
    }
}
