package com.farmermarket.ui;

import com.farmermarket.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomerDashboard extends JFrame {
    private User currentUser;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public CustomerDashboard(User user) {
        this.currentUser = user;
        
        // Set up the frame
        setTitle("Customer Dashboard - " + user.getFullName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components
        initComponents();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Browse Products", createBrowsePanel());
        tabbedPane.addTab("My Cart", createCartPanel());
        tabbedPane.addTab("My Orders", createOrdersPanel());
        tabbedPane.addTab("Profile", createProfilePanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Set content pane
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFullName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel, BorderLayout.WEST);
        
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        });
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createBrowsePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(new JTextField(20));
        
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{
            "All Categories", "Vegetables", "Fruits", "Dairy", "Meat", "Other"
        });
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryComboBox);
        
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Products panel
        JPanel productsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sample products
        productsPanel.add(createProductCard("Organic Tomatoes", "Fresh organic tomatoes", "$2.99/lb", "Farmer John", "Vegetables", "In Stock"));
        productsPanel.add(createProductCard("Free-range Eggs", "Farm fresh eggs", "$4.99/dozen", "Smith Family Farm", "Dairy", "In Stock"));
        productsPanel.add(createProductCard("Honey", "Raw unfiltered honey", "$8.99/jar", "Bee Happy Apiaries", "Other", "Limited Stock"));
        productsPanel.add(createProductCard("Apples", "Crisp red apples", "$1.99/lb", "Green Valley Orchard", "Fruits", "In Stock"));
        productsPanel.add(createProductCard("Lettuce", "Organic green lettuce", "$2.50/head", "Fresh Greens Farm", "Vegetables", "In Stock"));
        productsPanel.add(createProductCard("Cheese", "Artisan goat cheese", "$6.99/8oz", "Mountain Dairy", "Dairy", "In Stock"));
        
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createProductCard(String name, String description, String price, String farmer, String category, String availability) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);
        
        // Product image (placeholder)
        JLabel imageLabel = new JLabel(new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(imageLabel);
        
        // Product name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);
        
        // Product description (shortened)
        String shortDesc = description.length() > 30 ? description.substring(0, 30) + "..." : description;
        JLabel descLabel = new JLabel(shortDesc);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(descLabel);
        
        // Product price
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);
        
        // Farmer name
        JLabel farmerLabel = new JLabel("by " + farmer);
        farmerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(farmerLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Preview button
        JButton previewButton = new JButton("Preview");
        previewButton.addActionListener(e -> {
            ProductPreviewDialog dialog = new ProductPreviewDialog(
                this, name, description, price, farmer, category, availability
            );
            dialog.setVisible(true);
        });
        buttonPanel.add(previewButton);
        
        // Add to cart button
        JButton addButton = new JButton("Add to Cart");
        buttonPanel.add(addButton);
        
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(buttonPanel);
        
        return card;
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Cart items table
        String[] columnNames = {"Product", "Farmer", "Price", "Quantity", "Total", "Actions"};
        Object[][] data = {
            {"Organic Tomatoes", "Farmer John", "$2.99/lb", "2 lbs", "$5.98", "Remove"},
            {"Free-range Eggs", "Smith Family Farm", "$4.99/dozen", "1 dozen", "$4.99", "Remove"}
        };
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Order summary panel
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        summaryPanel.add(new JLabel("Subtotal:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        summaryPanel.add(new JLabel("$10.97"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        summaryPanel.add(new JLabel("Delivery Fee:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        summaryPanel.add(new JLabel("$2.00"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        summaryPanel.add(new JLabel("Total:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel totalLabel = new JLabel("$12.97");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkoutButton = new JButton("Proceed to Checkout");
        summaryPanel.add(checkoutButton, gbc);
        
        panel.add(summaryPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Orders table
        String[] columnNames = {"Order ID", "Date", "Items", "Total", "Status", "Actions"};
        Object[][] data = {
            {"2001", "2023-04-05", "3 items", "$18.50", "Delivered", "View"},
            {"2002", "2023-04-01", "2 items", "$12.75", "Delivered", "View"},
            {"2003", "2023-03-28", "4 items", "$25.99", "Delivered", "View"}
        };
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Profile information
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(new JLabel(currentUser.getUsername()), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Full Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(new JLabel(currentUser.getFullName()), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(new JLabel(currentUser.getEmail()), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(new JLabel(currentUser.getPhone()), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Address:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(new JLabel(currentUser.getAddress()), gbc);
        
        // Edit profile button
        JButton editProfileButton = new JButton("Edit Profile");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(editProfileButton, gbc);
        
        return panel;
    }
}
