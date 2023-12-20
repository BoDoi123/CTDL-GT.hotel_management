package view;

import controller.RoomController;
import models.Customer;
import models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoomManageView extends javax.swing.JFrame {
    private DateTimeFormatter formatter;
    private RoomController roomController;
    private DefaultTableModel roomModel;
    private javax.swing.JMenuItem checkMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable roomTable;
    private javax.swing.JPopupMenu roomTablePopupMenu;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;

    public RoomManageView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        roomTablePopupMenu = new javax.swing.JPopupMenu();
        checkMenuItem = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();

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

//        searchTextField.addPropertyChangeListener(this::searchTextFieldStateChanged);

        searchLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        searchLabel.setText("Search");

        searchTextField.addPropertyChangeListener(this::searchTextFieldStateChanged);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                                                .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void searchTextFieldStateChanged(java.beans.PropertyChangeEvent evt) {
        String text = searchTextField.getText();
        if (text.isBlank()) {
            return;
        }

        if (isInteger(text)) {
            roomModel.setRowCount(0);
            int roomId = Integer.parseInt(text);
            Room room = roomController.getRoomDAO().getRoomByID(roomId);
            if (room == null) {
                return;
            }

            roomModel.addRow(new Object[]{room.getId(), room.getCustomer().getName(), room.getCustomer().getId(), room.getRentDate().format(formatter), room.getDepartureDate().format(formatter), room.getPrice()});
        } else {
            roomModel.setRowCount(0);
            List<Customer> customers = roomController.getRoomDAO().getCustomerDAO().getCustomerByName(text);
            if (customers.isEmpty()) {
                return;
            }

            for (Customer customer : customers) {
                List<Room> rooms = roomController.getRoomDAO().getRoomByCustomerID(customer.getId());

                for (Room room : rooms) {
                    roomModel.addRow(new Object[]{room.getId(), room.getCustomer().getName(), room.getCustomer().getId(), room.getRentDate().format(formatter), room.getDepartureDate().format(formatter), room.getPrice()});
                }
            }
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomManageView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new RoomManageView().setVisible(true));
    }
}
