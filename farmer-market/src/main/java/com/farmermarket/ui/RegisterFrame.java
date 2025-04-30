package com.farmermarket.ui;

import com.farmermarket.dao.UserDAO;
import com.farmermarket.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JComboBox<String> userTypeComboBox;
    private JButton registerButton;
    private JButton backButton;
    
    private UserDAO userDAO;
    
    public RegisterFrame() {
        // Initialize DAO
        userDAO = new UserDAO();
        
        // Set up the frame
        setTitle("Farmer Market - Register");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components
        initComponents();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Register New Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);
        
        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);
        
        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(confirmPasswordLabel, gbc);
        
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(confirmPasswordField, gbc);
        
        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(fullNameLabel, gbc);
        
        fullNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(fullNameField, gbc);
        
        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(emailLabel, gbc);
        
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(emailField, gbc);
        
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(phoneLabel, gbc);
        
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(phoneField, gbc);
        
        // Address
        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(addressLabel, gbc);
        
        addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(addressScrollPane, gbc);
        
        // User Type
        JLabel userTypeLabel = new JLabel("Register as:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(userTypeLabel, gbc);
        
        userTypeComboBox = new JComboBox<>(new String[]{"Farmer", "Customer"});
        gbc.gridx = 1;
        gbc.gridy = 8;
        mainPanel.add(userTypeComboBox, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);
        
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        // Set content pane
        setContentPane(mainPanel);
    }
    
    private void registerUser() {
        // Get input values
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressArea.getText();
        boolean isFarmer = userTypeComboBox.getSelectedIndex() == 0;
        
        // Validate input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
            fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", 
                                         "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", 
                                         "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.", 
                                         "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create user object
        User user = new User(
            username,
            password,
            fullName,
            email,
            phone,
            address,
            isFarmer ? User.UserType.FARMER : User.UserType.CUSTOMER
        );
        
        // Register user
        boolean success = userDAO.registerUser(user);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", 
                                         "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Go back to login screen
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", 
                                         "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
