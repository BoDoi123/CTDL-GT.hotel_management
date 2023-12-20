package view;

import lombok.Setter;
import lombok.Getter;

import controller.RoomController;
import models.Customer;
import models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class RoomManageView extends javax.swing.JFrame {
    private DateTimeFormatter formatter;
    private RoomController roomController;
    private DefaultTableModel roomModel;
    private javax.swing.JTable roomTable;

    public RoomManageView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        JPopupMenu roomTablePopupMenu = new JPopupMenu();
        JMenuItem checkMenuItem = new JMenuItem();
        JScrollPane jScrollPane1 = new JScrollPane();
        roomTable = new javax.swing.JTable();
        JLabel jLabel1 = new JLabel();
        JButton refreshButton = new javax.swing.JButton();
        JButton searchButton = new javax.swing.JButton();

        checkMenuItem.setText("check information");
        roomTablePopupMenu.add(checkMenuItem);
        checkMenuItem.addActionListener(this::checkMenuItemActionPerformed);

        roomModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable.setModel(roomModel);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        roomModel.addColumn("mã phòng");
        roomModel.addColumn("tên người thuê");
        roomModel.addColumn("mã người thuê");
        roomModel.addColumn("ngày thuê");
        roomModel.addColumn("ngày trả dự kiến");
        roomModel.addColumn("giá dự kiến");

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Room> rooms = roomController.getAllRoomWithStateTrue();
        for (Room room : rooms) {
            roomModel.addRow(new Object[]{room.getId(), room.getCustomer().getName(), room.getCustomer().getId(), room.getRentDate().format(formatter), room.getDepartureDate().format(formatter), room.getPrice()});
        }
        jScrollPane1.setViewportView(roomTable);
        roomTable.setComponentPopupMenu(roomTablePopupMenu);

        jLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH PHÒNG ĐANG THUÊ");

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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(refreshButton)
                                                        .addComponent(searchButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void checkMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        int row = roomTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomID = Integer.parseInt(String.valueOf(roomTable.getValueAt(row, 0)));

        new RoomInformationView(roomID).setVisible(true);
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(this, "Nhập tên khách hàng", "Thông báo");
        roomModel.setRowCount(0);

        List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getCustomerByName(name);
        for (Customer custmer : customers) {
            List<Room> rooms = roomController.getRoomDAO().getRoomByCustomerID(custmer.getId());

            for (Room room : rooms) {
                roomModel.addRow(new Object[]{room.getId(), room.getCustomer().getName(), room.getCustomer().getId(), room.getRentDate().format(formatter), room.getDepartureDate().format(formatter), room.getPrice()});
            }
        }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        roomModel.setRowCount(0);

        List<Room> rooms = roomController.getAllRoomWithStateTrue();
        for (Room room : rooms) {
            roomModel.addRow(new Object[]{room.getId(), room.getCustomer().getName(), room.getCustomer().getId(), room.getRentDate().format(formatter), room.getDepartureDate().format(formatter), room.getPrice()});
        }
    }
}
