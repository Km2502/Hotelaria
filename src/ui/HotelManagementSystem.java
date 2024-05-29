/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Customer;
import model.Room;

public class HotelManagementSystem extends JFrame {

    public HotelManagementSystem() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sistema de Gerenciamento de Hotel");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu customerMenu = new JMenu("Clientes");
        JMenuItem addCustomer = new JMenuItem("Adicionar Cliente");
        addCustomer.addActionListener(e -> showCustomerForm());
        customerMenu.add(addCustomer);
        menuBar.add(customerMenu);

        JMenu reservationMenu = new JMenu("Reservas");
        JMenuItem newReservation = new JMenuItem("Nova Reserva");
        newReservation.addActionListener(e -> showReservationForm());
        reservationMenu.add(newReservation);
        menuBar.add(reservationMenu);

        JMenu roomMenu = new JMenu("Quartos");
        JMenuItem checkAvailability = new JMenuItem("Verificar Disponibilidade");
        checkAvailability.addActionListener(e -> showAvailabilityForm());
        roomMenu.add(checkAvailability);
        menuBar.add(roomMenu);

        setLayout(new BorderLayout());
        add(new JLabel("Bem-vindo ao Sistema de Gerenciamento de Hotel", SwingConstants.CENTER), BorderLayout.CENTER);
    }

    private void showCustomerForm() {
        JFrame customerFrame = new JFrame("Adicionar Cliente");
        customerFrame.setSize(300, 150);
        customerFrame.setLayout(new GridLayout(3, 2));
        customerFrame.add(new JLabel("Nome:"));
        JTextField nameField = new JTextField();
        customerFrame.add(nameField);
        customerFrame.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        customerFrame.add(emailField);
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> saveCustomer(nameField.getText(), emailField.getText()));
        customerFrame.add(saveButton);
        customerFrame.setVisible(true);
    }

    private void saveCustomer(String name, String email) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Customers (name, email) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente: " + ex.getMessage());
        }
    }

    private void showReservationForm() {
        JFrame reservationFrame = new JFrame("Nova Reserva");
        reservationFrame.setSize(350, 300);
        reservationFrame.setLayout(new GridLayout(6, 2));
        reservationFrame.add(new JLabel("Cliente:"));
        JComboBox<Customer> customerBox = new JComboBox<>();
        loadCustomers(customerBox);
        reservationFrame.add(customerBox);
        reservationFrame.add(new JLabel("Quarto:"));
        JComboBox<Room> roomBox = new JComboBox<>();
        loadRooms(roomBox);
        reservationFrame.add(roomBox);
        reservationFrame.add(new JLabel("Data (dd/MM/yyyy):"));
        JTextField dateField = new JTextField();
        reservationFrame.add(dateField);
        JButton saveButton = new JButton("Salvar Reserva");
        saveButton.addActionListener(e -> {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse(dateField.getText()); // Parse the date from the field
                saveReservation(
                    ((Customer) customerBox.getSelectedItem()).getId(),
                    ((Room) roomBox.getSelectedItem()).getRoomNumber(),
                    new SimpleDateFormat("yyyy-MM-dd").format(date) // Convert to SQL date format
                );
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(reservationFrame, "Formato de data inválido. Use 'dd/MM/yyyy'", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        reservationFrame.add(saveButton);
        reservationFrame.setVisible(true);
    }

    private void saveReservation(String customerId, int roomNumber, String dateStr) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Reservations (customer_id, room_number, reservation_date) VALUES (?, ?, ?)")) {
            pstmt.setString(1, customerId);
            pstmt.setInt(2, roomNumber);
            pstmt.setDate(3, java.sql.Date.valueOf(dateStr)); // Convert string to SQL date directly
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Reserva salva com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar reserva: " + ex.getMessage());
        }
    }

    private void showAvailabilityForm() {
        JDialog availabilityDialog = new JDialog(this, "Verificar Disponibilidade", true);
        availabilityDialog.setLayout(new BorderLayout());
        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Selecione a Data (dd/MM/yyyy):"));
        JTextField dateField = new JTextField(10);
        datePanel.add(dateField);
        JButton checkButton = new JButton("Verificar");
        checkButton.addActionListener(e -> {
            String dateString = dateField.getText();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = formatter.parse(dateString);
                checkRoomAvailability(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use 'dd/MM/yyyy'", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        datePanel.add(checkButton);
        availabilityDialog.add(datePanel, BorderLayout.CENTER);
        availabilityDialog.pack();
        availabilityDialog.setLocationRelativeTo(this);
        availabilityDialog.setVisible(true);
    }

    private void checkRoomAvailability(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        String query = "SELECT room_number FROM Rooms WHERE room_number NOT IN (" +
                       "SELECT room_number FROM Reservations WHERE reservation_date = ?) AND is_available = TRUE";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, formattedDate);
        ResultSet rs = pstmt.executeQuery();

        StringBuilder availableRooms = new StringBuilder("Quartos disponíveis para a data: ");
        boolean hasRooms = false;
        while (rs.next()) {
            hasRooms = true;
            availableRooms.append(rs.getInt("room_number")).append(", ");
        }
        if (!hasRooms) {
            availableRooms.append("Nenhum quarto disponível.");
        } else {
            availableRooms.setLength(availableRooms.length() - 2); // Remove the last comma and space
        }
        JOptionPane.showMessageDialog(this, availableRooms.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar disponibilidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomers(JComboBox<Customer> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, name, email FROM Customers")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(new Customer(rs.getString("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private void loadRooms(JComboBox<Room> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("SELECT room_number, is_available FROM Rooms WHERE is_available = TRUE")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(new Room(rs.getInt("room_number"), rs.getBoolean("is_available")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar quartos: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelManagementSystem().setVisible(true);
        });
    }
}













