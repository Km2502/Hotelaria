/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ui;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import model.Customer;
import model.Room;
import model.Employee;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.DateComponentFormatter;

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
        JMenuItem deleteCustomer = new JMenuItem("Excluir Cliente");
        deleteCustomer.addActionListener(e -> showDeleteCustomerForm());
        customerMenu.add(deleteCustomer);
        menuBar.add(customerMenu);

        JMenu reservationMenu = new JMenu("Reservas");
        JMenuItem newReservation = new JMenuItem("Nova Reserva");
        newReservation.addActionListener(e -> showReservationForm());
        reservationMenu.add(newReservation);
        JMenuItem cancelReservation = new JMenuItem("Cancelar Reserva");
        cancelReservation.addActionListener(e -> showCancelReservationForm());
        reservationMenu.add(cancelReservation);
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
        customerFrame.setSize(300, 200);
        customerFrame.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Telefone:");
        JTextField phoneField = new JTextField();

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(customerFrame, "Cliente adicionado com sucesso!");
                customerFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(customerFrame, "Erro ao adicionar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        customerFrame.add(nameLabel);
        customerFrame.add(nameField);
        customerFrame.add(emailLabel);
        customerFrame.add(emailField);
        customerFrame.add(phoneLabel);
        customerFrame.add(phoneField);
        customerFrame.add(new JLabel());
        customerFrame.add(saveButton);

        customerFrame.setVisible(true);
    }

    private void showDeleteCustomerForm() {
        JFrame deleteCustomerFrame = new JFrame("Excluir Cliente");
        deleteCustomerFrame.setSize(300, 150);
        deleteCustomerFrame.setLayout(new GridLayout(2, 2));

        JLabel customerLabel = new JLabel("Cliente:");
        JComboBox<Customer> customerComboBox = new JComboBox<>();
        loadCustomers(customerComboBox);

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
            if (selectedCustomer != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11")) {
                    // Excluir todas as reservas associadas ao cliente
                    try (PreparedStatement pstmtReservations = conn.prepareStatement("DELETE FROM reservations WHERE customer_id = ?")) {
                        pstmtReservations.setString(1, selectedCustomer.getId());
                        pstmtReservations.executeUpdate();
                    }

                    // Excluir o cliente
                    try (PreparedStatement pstmtCustomer = conn.prepareStatement("DELETE FROM customers WHERE id = ?")) {
                        pstmtCustomer.setString(1, selectedCustomer.getId());
                        pstmtCustomer.executeUpdate();
                        JOptionPane.showMessageDialog(deleteCustomerFrame, "Cliente excluído com sucesso!");
                        deleteCustomerFrame.dispose();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(deleteCustomerFrame, "Erro ao excluir cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteCustomerFrame.add(customerLabel);
        deleteCustomerFrame.add(customerComboBox);
        deleteCustomerFrame.add(new JLabel());
        deleteCustomerFrame.add(deleteButton);

        deleteCustomerFrame.setVisible(true);
    }

    private void showReservationForm() {
        JFrame reservationFrame = new JFrame("Nova Reserva");
        reservationFrame.setSize(400, 300);
        reservationFrame.setLayout(new GridLayout(5, 2));

        JLabel customerLabel = new JLabel("Cliente:");
        JComboBox<Customer> customerComboBox = new JComboBox<>();
        loadCustomers(customerComboBox);

        JLabel dateLabel = new JLabel("Data:");
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        JLabel roomLabel = new JLabel("Quarto:");
        JComboBox<Room> roomComboBox = new JComboBox<>();
        loadRooms(roomComboBox);

        JLabel employeeLabel = new JLabel("Funcionário:");
        JComboBox<Employee> employeeComboBox = new JComboBox<>();
        loadEmployees(employeeComboBox);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            Room selectedRoom = (Room) roomComboBox.getSelectedItem();
            Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();

            if (selectedCustomer == null || selectedDate == null || selectedRoom == null || selectedEmployee == null) {
                JOptionPane.showMessageDialog(reservationFrame, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedDate.before(new java.util.Date())) {
                JOptionPane.showMessageDialog(reservationFrame, "Não é possível fazer uma reserva para uma data no passado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
                 PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM reservations WHERE reservation_date = ? AND room_number = ?");
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reservations (customer_id, reservation_date, room_number, employee_id) VALUES (?, ?, ?, ?)")) {

                checkStmt.setDate(1, new java.sql.Date(selectedDate.getTime()));
                checkStmt.setInt(2, selectedRoom.getRoomNumber());
                ResultSet rs = checkStmt.executeQuery();
                rs.next();

                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(reservationFrame, "O quarto já está reservado para essa data!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pstmt.setString(1, selectedCustomer.getId());
                pstmt.setDate(2, new java.sql.Date(selectedDate.getTime()));
                pstmt.setInt(3, selectedRoom.getRoomNumber());
                pstmt.setString(4, selectedEmployee.getId());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(reservationFrame, "Reserva adicionada com sucesso!");
                reservationFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(reservationFrame, "Erro ao adicionar reserva: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        reservationFrame.add(customerLabel);
        reservationFrame.add(customerComboBox);
        reservationFrame.add(dateLabel);
        reservationFrame.add(datePicker);
        reservationFrame.add(roomLabel);
        reservationFrame.add(roomComboBox);
        reservationFrame.add(employeeLabel);
        reservationFrame.add(employeeComboBox);
        reservationFrame.add(new JLabel());
        reservationFrame.add(saveButton);

        reservationFrame.setVisible(true);
    }

    private void showCancelReservationForm() {
        JFrame cancelReservationFrame = new JFrame("Cancelar Reserva");
        cancelReservationFrame.setSize(300, 150);
        cancelReservationFrame.setLayout(new GridLayout(2, 2));

        JLabel reservationLabel = new JLabel("Reserva:");
        JComboBox<String> reservationComboBox = new JComboBox<>();
        loadReservations(reservationComboBox);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            String selectedReservation = (String) reservationComboBox.getSelectedItem();
            if (selectedReservation != null) {
                String reservationId = selectedReservation.split(" - ")[0]; // Extrair o ID da reserva
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM reservations WHERE id = ?")) {
                    pstmt.setString(1, reservationId);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(cancelReservationFrame, "Reserva cancelada com sucesso!");
                    cancelReservationFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(cancelReservationFrame, "Erro ao cancelar reserva: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelReservationFrame.add(reservationLabel);
        cancelReservationFrame.add(reservationComboBox);
        cancelReservationFrame.add(new JLabel());
        cancelReservationFrame.add(cancelButton);

        cancelReservationFrame.setVisible(true);
    }

    private void showAvailabilityForm() {
        JFrame availabilityFrame = new JFrame("Verificar Disponibilidade");
        availabilityFrame.setSize(400, 300);
        availabilityFrame.setLayout(new BorderLayout());

        JCalendar calendar = new JCalendar();
        calendar.addPropertyChangeListener("calendar", evt -> updateAvailability(calendar));

        availabilityFrame.add(calendar, BorderLayout.CENTER);
        availabilityFrame.setVisible(true);
    }

    private void updateAvailability(JCalendar calendar) {
        Date selectedDate = calendar.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);

        if (selectedDate.before(new java.util.Date())) {
            JOptionPane.showMessageDialog(null, "Data no passado, quartos indisponíveis.");
            return;
        }

        StringBuilder availableRooms = new StringBuilder("Quartos disponíveis em " + formattedDate + ":\n");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT room_number FROM rooms WHERE room_number NOT IN (" +
                             "SELECT room_number FROM reservations WHERE reservation_date = ?)")) {
            pstmt.setString(1, formattedDate);
            ResultSet rs = pstmt.executeQuery();

            boolean hasRooms = false;
            while (rs.next()) {
                availableRooms.append("Quarto ").append(rs.getInt("room_number")).append(", ");
                hasRooms = true;
            }

            if (!hasRooms) {
                availableRooms.append("Nenhum quarto disponível.");
            } else {
                availableRooms.setLength(availableRooms.length() - 2); // Remove the last comma and space
            }
            JOptionPane.showMessageDialog(null, availableRooms.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar disponibilidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomers(JComboBox<Customer> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, name, email, phone FROM customers")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(new Customer(rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private void loadRooms(JComboBox<Room> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("SELECT room_number, is_available FROM rooms")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(new Room(rs.getInt("room_number"), rs.getBoolean("is_available")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar quartos: " + ex.getMessage());
        }
    }

    private void loadEmployees(JComboBox<Employee> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, name, email FROM employees")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(new Employee(rs.getString("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar funcionários: " + ex.getMessage());
        }
    }

    private void loadReservations(JComboBox<String> comboBox) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management", "root", "magnoliacasa11");
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT r.id, c.name, r.reservation_date, r.room_number " +
                     "FROM reservations r JOIN customers c ON r.customer_id = c.id")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(rs.getString("id") + " - " + rs.getString("name") + " - " + rs.getDate("reservation_date") + " - Quarto " + rs.getInt("room_number"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar reservas: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelManagementSystem().setVisible(true);
        });
    }
}











































