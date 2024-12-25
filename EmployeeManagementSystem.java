/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package employeemanagementsystem;

/**
 *
 * @author ASUS
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeeManagementSystem {

    public static class Employee {
        int id;
        String name;
        double baseSalary;
        int workHours;
        int leavesTaken;

        public Employee(int id, String name, double baseSalary, double monthlySalary) {
            this.id = id;
            this.name = name;
            this.baseSalary = baseSalary;
            this.workHours = 0;
            this.leavesTaken = 0;
        }

        public double calculateSalary() {
            double bonus = workHours > 160 ? (baseSalary * 0.1) : 0;
            double deductions = leavesTaken * 50;
            return baseSalary + bonus - deductions;
        }

        public String performanceReport() {
            if (workHours >= 180) {
                return "Very Good";
            } else if (workHours >= 160) {
                return "Good";
            } else if (workHours >= 140) {
                return "Average";
            } else {
                return "Needs Improvement";
            }
        }
    }

    private final ArrayList<Employee> employees = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField salaryField = new JTextField(15);
    private final JTextField monthlySalaryField = new JTextField(15);
    private final JTextField workHoursField = new JTextField(15);
    private final JTextField leavesField = new JTextField(15);

    public EmployeeManagementSystem() {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Employee Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Times new roman", Font.BOLD, 25));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        JTable employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(25);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setGridColor(new Color(200, 200, 200));
        employeeTable.setSelectionBackground(new Color(173, 216, 230));

        tableModel.addColumn("Employee ID");
        tableModel.addColumn("Employee Name");
        tableModel.addColumn("Base Salary");
        tableModel.addColumn("Monthly Salary");
        tableModel.addColumn("Work Hours");
        tableModel.addColumn("Leaves Taken");
        tableModel.addColumn("Calculated Salary");
        tableModel.addColumn("Performance");

        JScrollPane tablePane = new JScrollPane(employeeTable);
        tablePane.setBorder(BorderFactory.createTitledBorder("Employee Records"));
        frame.add(tablePane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Employee Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Base Salary:"), gbc);
        gbc.gridx = 1;
        formPanel.add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Monthly Salary:"), gbc);
        gbc.gridx = 1;
        formPanel.add(monthlySalaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Work Hours:"), gbc);
        gbc.gridx = 1;
        formPanel.add(workHoursField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Leaves Taken:"), gbc);
        gbc.gridx = 1;
        formPanel.add(leavesField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton addButton = new JButton("Add Employee Record");
        JButton updateButton = new JButton("Update Records");
        JButton calculateButton = new JButton("Calculate Salaries");
        JButton reportButton = new JButton("Generate Report");

        styleButton(addButton);
        styleButton(updateButton);
        styleButton(calculateButton);
        styleButton(reportButton);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(reportButton);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        frame.add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (employees.stream().anyMatch(emp -> emp.id == id)) {
                    JOptionPane.showMessageDialog(frame, "Employee ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String name = nameField.getText();
                double baseSalary = Double.parseDouble(salaryField.getText());
                double monthlySalary = Double.parseDouble(monthlySalaryField.getText());
                employees.add(new Employee(id, name, baseSalary, monthlySalary));
                updateTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int workHours = Integer.parseInt(workHoursField.getText());
                int leaves = Integer.parseInt(leavesField.getText());

                for (Employee emp : employees) {
                    if (emp.id == id) {
                        emp.workHours = workHours;
                        emp.leavesTaken = leaves;
                        emp.calculateSalary();
                        updateTable();
                        clearFields();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Employee Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for work hours and leaves.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An unexpected error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        calculateButton.addActionListener(e -> {
            updateTable();
            JOptionPane.showMessageDialog(frame, "Salaries Calculated Successfully.");
        });

        reportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder("Employee Performance Report:\n\n");
            int totalEmployees = employees.size();
            double totalSalaries = 0;
            int excellentCount = 0;

            for (Employee emp : employees) {
                report.append(String.format("ID: %d, Name: %s, Performance: %s, Work Hours: %d, Calculated Salary: %.2f\n", 
                        emp.id, emp.name, emp.performanceReport(), emp.workHours, emp.calculateSalary()));
                totalSalaries += emp.calculateSalary();
                if (emp.performanceReport().equals("Excellent")) {
                    excellentCount++;
                }
            }

            report.append("\nSummary:\n");
            report.append("Total Employees: ").append(totalEmployees).append("\n");
            report.append("Total Salaries: ").append(totalSalaries).append("\n");
            report.append("Excellent Performers: ").append(excellentCount).append("\n");

            JOptionPane.showMessageDialog(frame, report.toString(), "Performance Report", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setVisible(true);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Employee emp : employees) {
            tableModel.addRow(new Object[]{
                    emp.id, emp.name, emp.baseSalary,
                    emp.workHours, emp.leavesTaken, emp.calculateSalary(), emp.performanceReport()
            });
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        salaryField.setText("");
        workHoursField.setText("");
        leavesField.setText("");
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManagementSystem::new);
    }
}
