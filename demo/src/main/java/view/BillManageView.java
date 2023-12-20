package view;

import lombok.Setter;
import lombok.Getter;

import controller.RoomController;
import models.Bill;
import models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BillManageView extends javax.swing.JFrame {
    private javax.swing.JTable billTable;
    private DateTimeFormatter formatter;
    private RoomController roomController;
    private DefaultTableModel billModel;
    private BillInformationView billInformationView;
    private int roomID;

    public BillManageView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        javax.swing.JLabel billLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        javax.swing.JButton refreshButton = new javax.swing.JButton();
        javax.swing.JButton searchButton = new javax.swing.JButton();
        JPopupMenu billTablePopupMenu = new JPopupMenu();
        JMenuItem checkMenuItem = new JMenuItem();

        checkMenuItem.setText("Check Information");
        billTablePopupMenu.add(checkMenuItem);
        checkMenuItem.addActionListener(this::checkMenuItemActionPerformed);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        billLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        billLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        billLabel.setText("DANH SÁCH HÓA ĐƠN ĐÃ THANH TOÁN");

        billModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        billTable.setModel(billModel);
        billTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        billModel.addColumn("mã hóa đơn");
        billModel.addColumn("tên khách hàng");
        billModel.addColumn("mã khách hàng");
        billModel.addColumn("mã phòng");
        billModel.addColumn("ngày thanh toán");
        billModel.addColumn("Giá tiền");

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Bill> billList = roomController.getRoomDAO().getBillDAO().getAllFinishedBills();
        for (Bill bill : billList) {
            billInformationView = new BillInformationView(bill.getId());
            Date date = (Date) billInformationView.getDateSpinner().getValue();
            LocalDate localDate = convertToLocalDate(date);

            billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), localDate.format(formatter), bill.getPrice()});
        }
        jScrollPane1.setViewportView(billTable);
        billTable.setComponentPopupMenu(billTablePopupMenu);

        refreshButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        searchButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        searchButton.setText("Search");
        searchButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchButton.addActionListener(this::searchButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(41, 41, 41)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(billLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 26, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    private void checkMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        int row = billTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomID = Integer.parseInt(String.valueOf(billTable.getValueAt(row, 3)));

        billInformationView = new BillInformationView(roomID);
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        billModel.setRowCount(0);

        List<Bill> billList = roomController.getRoomDAO().getBillDAO().getAllFinishedBills();
        for (Bill bill : billList) {
            billInformationView = new BillInformationView(bill.getId());
            Date date = (Date) billInformationView.getDateSpinner().getValue();
            LocalDate localDate = convertToLocalDate(date);

            billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), localDate.format(formatter), bill.getPrice()});
        }
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(this, "Nhập tên khách hàng", "Thông báo");
        billModel.setRowCount(0);

        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getCustomerByName(name);
        for (Customer custmer : customers) {
            List<Bill> bills = roomController.getRoomDAO().getBillDAO().getBillsByCstomerID(custmer.getId());

            for (Bill bill : bills) {
                billInformationView = new BillInformationView(bill.getId());
                Date date = (Date) billInformationView.getDateSpinner().getValue();
                LocalDate localDate = convertToLocalDate(date);

                billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), localDate.format(formatter), bill.getPrice()});
            }
        }
    }

    private LocalDate convertToLocalDate(Date utilDate) {
        Instant instant = utilDate.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
