package com.farmermarket.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Dialog to display a detailed preview of a product
 */
public class ProductPreviewDialog extends JDialog {
    
    private String productName;
    private String description;
    private String price;
    private String farmer;
    private String category;
    private String availability;
    private BufferedImage productImage;
    
    /**
     * Constructor for the product preview dialog
     * 
     * @param parent The parent frame
     * @param productName Name of the product
     * @param description Product description
     * @param price Product price
     * @param farmer Farmer name
     * @param category Product category
     * @param availability Product availability
     */
    public ProductPreviewDialog(Frame parent, String productName, String description, 
                               String price, String farmer, String category, 
                               String availability) {
        super(parent, "Product Preview", true);
        
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.farmer = farmer;
        this.category = category;
        this.availability = availability;
        
        // Create a placeholder image
        this.productImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = productImage.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, 199, 199);
        g.drawString("Product Image", 60, 100);
        g.dispose();
        
        initComponents();
        
        // Set dialog properties
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    /**
     * Constructor with image
     */
    public ProductPreviewDialog(Frame parent, String productName, String description, 
                               String price, String farmer, String category, 
                               String availability, BufferedImage productImage) {
        this(parent, productName, description, price, farmer, category, availability);
        if (productImage != null) {
            this.productImage = productImage;
        }
    }
    
    private void initComponents() {
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Image panel on the left
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(productImage));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.WEST);
        
        // Details panel on the right
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        
        // Product name
        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(nameLabel);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Price
        JLabel priceLabel = new JLabel("Price: " + price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(priceLabel);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Farmer
        JLabel farmerLabel = new JLabel("Sold by: " + farmer);
        farmerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(farmerLabel);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Category
        JLabel categoryLabel = new JLabel("Category: " + category);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(categoryLabel);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Availability
        JLabel availabilityLabel = new JLabel("Availability: " + availability);
        availabilityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(availabilityLabel);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Description
        JLabel descTitleLabel = new JLabel("Description:");
        descTitleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        descTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(descTitleLabel);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setBackground(detailsPanel.getBackground());
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(descArea);
        
        detailsPanel.add(Box.createVerticalGlue());
        
        // Add to cart button (for customer view)
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(addToCartButton);
        
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Close button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set content pane
        setContentPane(mainPanel);
    }
}
