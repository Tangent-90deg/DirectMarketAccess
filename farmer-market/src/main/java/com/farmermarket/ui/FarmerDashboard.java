package com.farmermarket.ui;

import com.farmermarket.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FarmerDashboard extends JFrame {
    private User currentUser;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public FarmerDashboard(User user) {
        this.currentUser = user;
        
        // Set up the frame
        setTitle("Farmer Dashboard - " + user.getFullName());
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
        tabbedPane.addTab("My Products", createProductsPanel());
        tabbedPane.addTab("Orders", createOrdersPanel());
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
    
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add product button and preview button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(e -> {
            // Open add product dialog (would be implemented in a real application)
            JOptionPane.showMessageDialog(this, "Add Product functionality would be implemented here", 
                                         "Add Product", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(addProductButton);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        // Products table
        String[] columnNames = {"ID", "Name", "Description", "Price", "Quantity", "Category", "Actions"};
        Object[][] data = {
            {"1", "Organic Tomatoes", "Fresh organic tomatoes", "$2.99/lb", "50 lbs", "Vegetables", "Preview/Edit/Delete"},
            {"2", "Free-range Eggs", "Farm fresh eggs", "$4.99/dozen", "20 dozen", "Dairy", "Preview/Edit/Delete"},
            {"3", "Honey", "Raw unfiltered honey", "$8.99/jar", "15 jars", "Other", "Preview/Edit/Delete"}
        };
        
        // Create a custom table model to handle the action buttons
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only make the Actions column editable
            }
        };
        
        // Add a custom renderer and editor for the Actions column
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), this));
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Orders table
        String[] columnNames = {"Order ID", "Customer", "Date", "Items", "Total", "Status", "Actions"};
        Object[][] data = {
            {"1001", "John Doe", "2023-04-10", "3 items", "$25.99", "Pending", "View/Update"},
            {"1002", "Jane Smith", "2023-04-09", "2 items", "$15.50", "Shipped", "View"},
            {"1003", "Bob Johnson", "2023-04-08", "5 items", "$42.75", "Delivered", "View"}
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
    
    // Custom renderer for the button column
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton previewButton;
        private JButton editButton;
        private JButton deleteButton;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            
            previewButton = new JButton("Preview");
            previewButton.setMargin(new Insets(0, 2, 0, 2));
            previewButton.setFont(new Font("Arial", Font.PLAIN, 10));
            
            editButton = new JButton("Edit");
            editButton.setMargin(new Insets(0, 2, 0, 2));
            editButton.setFont(new Font("Arial", Font.PLAIN, 10));
            
            deleteButton = new JButton("Delete");
            deleteButton.setMargin(new Insets(0, 2, 0, 2));
            deleteButton.setFont(new Font("Arial", Font.PLAIN, 10));
            
            add(previewButton);
            add(editButton);
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    // Custom editor for the button column
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton previewButton;
        private JButton editButton;
        private JButton deleteButton;
        private String[] productData;
        private JFrame parentFrame;
        
        public ButtonEditor(JCheckBox checkBox, JFrame parentFrame) {
            super(checkBox);
            this.parentFrame = parentFrame;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
            
            previewButton = new JButton("Preview");
            previewButton.setMargin(new Insets(0, 2, 0, 2));
            previewButton.setFont(new Font("Arial", Font.PLAIN, 10));
            previewButton.addActionListener(e -> {
                fireEditingStopped();
                showProductPreview();
            });
            
            editButton = new JButton("Edit");
            editButton.setMargin(new Insets(0, 2, 0, 2));
            editButton.setFont(new Font("Arial", Font.PLAIN, 10));
            editButton.addActionListener(e -> {
                fireEditingStopped();
                JOptionPane.showMessageDialog(parentFrame, "Edit functionality would be implemented here", 
                                             "Edit Product", JOptionPane.INFORMATION_MESSAGE);
            });
            
            deleteButton = new JButton("Delete");
            deleteButton.setMargin(new Insets(0, 2, 0, 2));
            deleteButton.setFont(new Font("Arial", Font.PLAIN, 10));
            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                int confirm = JOptionPane.showConfirmDialog(parentFrame, 
                    "Are you sure you want to delete this product?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(parentFrame, "Delete functionality would be implemented here", 
                                                 "Delete Product", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            
            panel.add(previewButton);
            panel.add(editButton);
            panel.add(deleteButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            productData = new String[6];
            for (int i = 0; i < 6; i++) {
                productData[i] = table.getValueAt(row, i).toString();
            }
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "Preview/Edit/Delete";
        }
        
        private void showProductPreview() {
            // Extract product data from the table row
            String name = productData[1];
            String description = productData[2];
            String price = productData[3];
            String quantity = productData[4];
            String category = productData[5];
            
            // Show preview dialog
            ProductPreviewDialog dialog = new ProductPreviewDialog(
                parentFrame, 
                name, 
                description, 
                price, 
                currentUser.getFullName(), // Farmer name
                category, 
                quantity // Using quantity as availability
            );
            dialog.setVisible(true);
        }
    }
}
