package view;

import lombok.Setter;
import lombok.Getter;

import controller.RoomController;
import models.Bill;
import models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class BillManageView extends javax.swing.JFrame {
    private JTable billTable;
    private RoomController roomController;
    private DefaultTableModel billModel;
    private int roomID;
    private JLabel allPricesText;

    public BillManageView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        JLabel billLabel = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        billTable = new JTable();
        JButton refreshButton = new JButton();
        JButton searchButton = new JButton();
        JPopupMenu billTablePopupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem();
        JLabel allPricesLabel = new JLabel();
        allPricesText = new JLabel();

        deleteMenuItem.setText("Delete Bill");
        billTablePopupMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(this::deleteBillMenuItemActionPerformed);

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
        billModel.addColumn("Giá tiền");

        List<Bill> billList = roomController.getRoomDAO().getBillDAO().getAllFinishedBills();
        int price = 0;
        if (!billList.isEmpty()) {
            for (Bill bill : billList) {
                billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), bill.getPrice()});
                price += bill.getPrice();
            }
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

        allPricesLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        allPricesLabel.setText("Tổng doanh thu:");

        allPricesText.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        allPricesText.setText(String.valueOf(price));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(allPricesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(allPricesText, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(41, 41, 41)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(allPricesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(allPricesText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void deleteBillMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        int row = billTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int billID = Integer.parseInt(String.valueOf(billTable.getValueAt(row, 0)));

        billModel.setRowCount(0);

        List<Bill> billList = roomController.getRoomDAO().getBillDAO().getAllFinishedBills();
        billList.removeIf(bill -> bill.getId() == billID);

        int price = 0;
        if (!billList.isEmpty()) {
            for (Bill bill : billList) {
                billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), bill.getPrice()});
                price += bill.getPrice();
            }
        }
        allPricesText.setText(String.valueOf(price));
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        billModel.setRowCount(0);

        List<Bill> billList = roomController.getRoomDAO().getBillDAO().getAllFinishedBills();
        int price = 0;
        if (!billList.isEmpty()) {
            for (Bill bill : billList) {
                billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), bill.getPrice()});
                price += bill.getPrice();
            }
        }
        allPricesText.setText(String.valueOf(price));
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(this, "Nhập tên khách hàng", "");
        billModel.setRowCount(0);

        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getCustomerByName(name);
        for (Customer customer : customers) {
            List<Bill> bills = roomController.getRoomDAO().getBillDAO().getBillsByCustomerID(customer.getId());

            for (Bill bill : bills) {
                billModel.addRow(new Object[]{bill.getId(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getName(), roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID()).getId(), bill.getRoomID(), bill.getPrice()});
            }
        }
    }
}
