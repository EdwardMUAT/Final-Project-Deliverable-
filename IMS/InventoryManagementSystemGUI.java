import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagementSystemGUI {
    private static List<Car> carInventory = new ArrayList<>();
    private static JLabel totalValueLabel;
    private static JLabel imageLabel;

    public static void main(String[] args) {
        // Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a JFrame (window)
        JFrame frame = new JFrame("Inventory Management System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panels
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Set layout for main panels
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerPanel.setLayout(new GridLayout(1, 1));
        bottomPanel.setLayout(new GridLayout(4, 1));

        // Set custom background color for the frame
        Color backgroundColor = new Color(220, 220, 220); // Light gray
        frame.getContentPane().setBackground(backgroundColor);

        // Add components to top panel
        JLabel titleLabel = new JLabel("Inventory Image Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(titleLabel);

        // Add example inventory to center panel
        JPanel carInventoryPanel = new JPanel();
        carInventoryPanel.setBorder(BorderFactory.createTitledBorder("Car Inventory"));
        imageLabel = new JLabel();
        carInventoryPanel.add(imageLabel);
        JTextArea carInventoryTextArea = new JTextArea(20, 40);
        carInventoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(carInventoryTextArea);
        carInventoryPanel.add(scrollPane);
        centerPanel.add(carInventoryPanel);

        // Add components to bottom panel
        JPanel actionPanel = new JPanel();
        JTextField brandTextField = new JTextField(10);
        JTextField priceTextField = new JTextField(10);
        JTextField quantityTextField = new JTextField(5);
        JButton addButton = new JButton("Add Cars");
        JButton removeButton = new JButton("Remove Cars");
        actionPanel.add(new JLabel("Brand:"));
        actionPanel.add(brandTextField);
        actionPanel.add(new JLabel("Price:"));
        actionPanel.add(priceTextField);
        actionPanel.add(new JLabel("Quantity:"));
        actionPanel.add(quantityTextField);
        actionPanel.add(addButton);
        actionPanel.add(removeButton);

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brand = brandTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                addCars(brand, price, quantity);
                updateInventoryTextArea(carInventoryTextArea);
                updateTotalValue();
                displayCarImage(brand);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantity = Integer.parseInt(quantityTextField.getText());
                removeCars(quantity);
                updateInventoryTextArea(carInventoryTextArea);
                updateTotalValue();
            }
        });

        bottomPanel.add(actionPanel);

        // Add total value panel to bottom panel
        JPanel totalValuePanel = new JPanel();
        totalValueLabel = new JLabel("Total Value: $0.00");
        totalValuePanel.add(totalValueLabel);
        bottomPanel.add(totalValuePanel);

        // Add main panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Method to add cars to the inventory
    private static void addCars(String brand, double price, int quantity) {
        for (int i = 0; i < quantity; i++) {
            carInventory.add(new Car(brand, price));
        }
    }

    // Method to remove cars from the inventory
    private static void removeCars(int quantity) {
        if (quantity > carInventory.size()) {
            JOptionPane.showMessageDialog(null, "Not enough cars in inventory to remove.");
        } else {
            for (int i = 0; i < quantity; i++) {
                carInventory.remove(0); // Remove the first car
            }
        }
    }

    // Method to update the car inventory text area
    private static void updateInventoryTextArea(JTextArea textArea) {
        textArea.setText(""); // Clear existing text
        for (Car car : carInventory) {
            textArea.append("Brand: " + car.getBrand() + ", Price: $" + car.getPrice() + "\n");
        }
    }

    // Method to calculate the total value of the inventory
    private static double calculateTotalValue() {
        double totalValue = 0.0;
        for (Car car : carInventory) {
            totalValue += car.getPrice();
        }
        return totalValue;
    }

    // Method to update the total value label
    private static void updateTotalValue() {
        double totalValue = calculateTotalValue();
        totalValueLabel.setText("Total Value: $" + String.format("%.2f", totalValue));
    }

    // Method to display car image based on brand
    private static void displayCarImage(String brand) {
        // Mapping between car brands and image file paths
        Map<String, String> brandImageMap = new HashMap<>();
        brandImageMap.put("2024 Toyota Camry", "toyota.jpg");
        brandImageMap.put("2024 Honda Civic", "honda.jpg");
        brandImageMap.put("2024 Kia Telluride", "kia.jpg");
        brandImageMap.put("2024 Dodge Charger", "dodge.jpg");
        // Add more brands and image paths as needed

        // Retrieve the image file path for the entered brand
        String imagePath = brandImageMap.get(brand);

        // Load and display the image
        if (imagePath != null) {
            ImageIcon icon = new ImageIcon(imagePath);
            imageLabel.setIcon(icon);
        } else {
            // Handle case when brand image is not found
            JOptionPane.showMessageDialog(null, "Image for brand " + brand + " not found.");
        }
    }

    // Car class to represent individual cars
    static class Car {
        private String brand;
        private double price;

        public Car(String brand, double price) {
            this.brand = brand;
            this.price = price;
        }

        public String getBrand() {
            return brand;
        }

        public double getPrice() {
            return price;
        }
    }
}
