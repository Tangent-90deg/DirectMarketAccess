package com.farmermarket.ui;

import com.farmermarket.dao.UserDAO;
import com.farmermarket.model.User;
import com.farmermarket.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel farmerLoginPanel;
    private JPanel customerLoginPanel;
    private JTextField farmerUsernameField;
    private JPasswordField farmerPasswordField;
    private JTextField customerUsernameField;
    private JPasswordField customerPasswordField;
    private JButton farmerLoginButton;
    private JButton customerLoginButton;
    private JButton registerButton;
    
    private UserDAO userDAO;
    
    public LoginFrame() {
        // Initialize database
        DatabaseConnection.initializeDatabase();
        
        // Initialize DAO
        userDAO = new UserDAO();
        
        // Set up the frame
        setTitle("Farmer Market - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components
        initComponents();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create farmer login panel
        farmerLoginPanel = createLoginPanel(true);
        tabbedPane.addTab("Farmer Login", farmerLoginPanel);
        
        // Create customer login panel
        customerLoginPanel = createLoginPanel(false);
        tabbedPane.addTab("Customer Login", customerLoginPanel);
        
        // Add tabbed pane to main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create register panel
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButton = new JButton("New User? Register Here");
        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
            dispose();
        });
        registerPanel.add(registerButton);
        
        // Add register panel to main panel
        mainPanel.add(registerPanel, BorderLayout.SOUTH);
        
        // Set content pane
        setContentPane(mainPanel);
    }
    
    private JPanel createLoginPanel(boolean isFarmer) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameLabel, gbc);
        
        // Username field
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(passwordLabel, gbc);
        
        // Password field
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);
        
        // Login button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(loginButton, gbc);
        
        // Store references to components
        if (isFarmer) {
            farmerUsernameField = usernameField;
            farmerPasswordField = passwordField;
            farmerLoginButton = loginButton;
            
            // Add action listener to farmer login button
            farmerLoginButton.addActionListener(e -> loginUser(true));
        } else {
            customerUsernameField = usernameField;
            customerPasswordField = passwordField;
            customerLoginButton = loginButton;
            
            // Add action listener to customer login button
            customerLoginButton.addActionListener(e -> loginUser(false));
        }
        
        return panel;
    }
    
    private void loginUser(boolean isFarmer) {
        String username = isFarmer ? farmerUsernameField.getText() : customerUsernameField.getText();
        String password = isFarmer ? new String(farmerPasswordField.getPassword()) : 
                                    new String(customerPasswordField.getPassword());
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", 
                                         "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Authenticate user
        User user = userDAO.authenticateUser(username, password);
        
        if (user != null) {
            // Check if user type matches
            boolean isUserFarmer = user.getUserType() == User.UserType.FARMER;
            
            if (isUserFarmer == isFarmer) {
                // Login successful
                JOptionPane.showMessageDialog(this, "Login successful!", 
                                             "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Open appropriate dashboard
                if (isFarmer) {
                    FarmerDashboard dashboard = new FarmerDashboard(user);
                    dashboard.setVisible(true);
                } else {
                    CustomerDashboard dashboard = new CustomerDashboard(user);
                    dashboard.setVisible(true);
                }
                
                // Close login frame
                dispose();
            } else {
                // Wrong user type
                String message = isFarmer ? 
                                "This account is not registered as a farmer" : 
                                "This account is not registered as a customer";
                JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Authentication failed
            JOptionPane.showMessageDialog(this, "Invalid username or password", 
                                         "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
