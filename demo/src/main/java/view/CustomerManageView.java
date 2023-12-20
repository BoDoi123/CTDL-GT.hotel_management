package view;

import controller.RoomController;
import models.Customer;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerManageView extends javax.swing.JFrame {
    private DateTimeFormatter formatter;
    private RoomController roomController;
    private DefaultTableModel customerModel;

    public CustomerManageView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable customerTable = new JTable();
        JLabel jLabel1 = new JLabel();
        JButton refreshButton = new JButton();
        JButton searchButton = new JButton();

        customerModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable.setModel(customerModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        customerModel.addColumn("mã khách hàng");
        customerModel.addColumn("tên khách hàng");
        customerModel.addColumn("ngày sinh");
        customerModel.addColumn("CMND/CCCD");

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getAllCustomers();
        for (Customer customer : customers) {
            customerModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getBirthday().format(formatter), customer.getIdentification()});
        }
        jScrollPane1.setViewportView(customerTable);

        jLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH KHÁCH HÀNG");

        refreshButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        searchButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        searchButton.setText("Search");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 34, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(refreshButton)
                                                        .addComponent(searchButton)))
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(this, "Nhập tên khách hàng", "");
        customerModel.setRowCount(0);

        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getCustomerByName(name);
        for (Customer customer : customers) {
            customerModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getBirthday().format(formatter), customer.getIdentification()});
        }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        customerModel.setRowCount(0);

        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getAllCustomers();
        for (Customer customer : customers) {
            customerModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getBirthday().format(formatter), customer.getIdentification()});
        }
    }
}
