package view;

import controller.RoomController;
import models.Customer;
import models.Room;
import models.Service;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomInformationView extends javax.swing.JFrame {
    private DefaultTableModel roomServiceModel;
    private Room room;
    private DefaultTableModel serviceModel;
    private RoomController roomController;
    private javax.swing.JMenuItem addRoomServiceMenu;
    private javax.swing.JMenuItem deleteRoomServiceMenu;
    private javax.swing.JSpinner departureDateSpinner;
    private javax.swing.JLabel pricePredictText;
    private javax.swing.JTable roomServiceTable;
    private javax.swing.JTable serviceTable;

    public RoomInformationView(int roomID) {
        initComponents(roomID);
    }

    private void initComponents(int roomID) {
        roomController = new RoomController();
        JPopupMenu roomServicePopupMenu = new JPopupMenu();
        deleteRoomServiceMenu = new javax.swing.JMenuItem();
        JPopupMenu servicePopupMenu = new JPopupMenu();
        addRoomServiceMenu = new javax.swing.JMenuItem();
        JLabel titleLabel = new JLabel();
        JLabel jLabel1 = new JLabel();
        JLabel roomIDLabel = new JLabel();
        JLabel rentDateLabel = new JLabel();
        JLabel roomIDText = new JLabel();
        JLabel fullNameText = new JLabel();
        JLabel departureDateLabel = new JLabel();
        JSpinner rentDateSpinner = new JSpinner();
        departureDateSpinner = new javax.swing.JSpinner();
        JLabel identificationLabel = new JLabel();
        JLabel identificationNumberTextField = new JLabel();
        JLabel priceLabel = new JLabel();
        pricePredictText = new javax.swing.JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        roomServiceTable = new javax.swing.JTable();
        JScrollPane jScrollPane2 = new JScrollPane();
        serviceTable = new javax.swing.JTable();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JButton submitButton = new JButton();
        JButton checkOutButton = new JButton();
        JButton refreshButton = new JButton();

        room = roomController.getRoomDAO().getRoomByID(roomID);
        Customer customer = room.getCustomer();

        deleteRoomServiceMenu.setText("Delete Service");
        roomServicePopupMenu.add(deleteRoomServiceMenu);
        deleteRoomServiceMenu.addActionListener(this::deleteRoomServiceMenuActionPerformed);

        addRoomServiceMenu.setText("Add Service");
        servicePopupMenu.add(addRoomServiceMenu);
        addRoomServiceMenu.addActionListener(this::addServiceMenuActionPerformed);

        setTitle("Thông tìn phòng");

        titleLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("THÔNG TIN PHÒNG THUÊ");

        jLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        jLabel1.setText("TÊN NGƯỜI DÙNG:");

        roomIDLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        roomIDLabel.setText("MÃ PHÒNG:");

        rentDateLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        rentDateLabel.setText("NGÀY THUÊ:");

        roomIDText.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        roomIDText.setText(String.valueOf(roomID));

        fullNameText.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        fullNameText.setText(customer.getName());

        departureDateLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        departureDateLabel.setText("NGÀY TRẢ DỰ KIÊN: ");

        LocalDate rentDate = room.getRentDate();
        Date defaultDate = Date.from(rentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        rentDateSpinner.setModel(new SpinnerDateModel(defaultDate, null, null, Calendar.DAY_OF_MONTH));
        rentDateSpinner.setEditor(new JSpinner.DateEditor(rentDateSpinner, "dd/MM/yyyy"));

        // Vô hiệu hóa sự kiên từ bàn phím
        JComponent editor = rentDateSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
            defaultEditor.getTextField().setEditable(false);
        }

        // Vô hiệu hóa sự kiện từ chuôt
        rentDateSpinner.setEnabled(false);
        rentDateSpinner.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LocalDate departureDate = room.getDepartureDate();
        Date defaultDate1 = Date.from(departureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        departureDateSpinner.setModel(new SpinnerDateModel(defaultDate1, null, null, Calendar.DAY_OF_MONTH));
        departureDateSpinner.setEditor(new JSpinner.DateEditor(departureDateSpinner, "dd/MM/yyyy"));
        departureDateSpinner.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        departureDateSpinner.addChangeListener(this::departureDateSpinnerStateChanged);

        identificationLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        identificationLabel.setText("CMND/CCCD:");

        identificationNumberTextField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        identificationNumberTextField.setText(customer.getIdentification());

        priceLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        priceLabel.setText("GIÁ PHÒNG DỰ KIẾN:");

        pricePredictText.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        pricePredictText.setText(String.valueOf(room.getPrice()));

        roomServiceModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomServiceTable.setModel(roomServiceModel);
        roomServiceTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        roomServiceModel.addColumn("service_name");
        roomServiceModel.addColumn("cost");
        roomServiceModel.addColumn("process");

        List<Service> serviceList = roomController.getRoomDAO().getRoomServicesByProcessing(roomID);
        for (Service service : serviceList) {
            roomServiceModel.addRow(new Object[]{service.getName(), service.getCost(), "Processing"});
        }
        jScrollPane1.setViewportView(roomServiceTable);
        roomServiceTable.setComponentPopupMenu(roomServicePopupMenu);

        serviceModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        serviceTable.setModel(serviceModel);
        serviceTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        serviceModel.addColumn("service_name");
        serviceModel.addColumn("cost");

        List<Service> services = roomController.getRoomDAO().getServiceDAO().getAllServices();
        for (Service service : services) {
            serviceModel.addRow(new Object[]{service.getName(), service.getCost()});
        }
        jScrollPane2.setViewportView(serviceTable);
        serviceTable.setComponentPopupMenu(servicePopupMenu);

        jLabel2.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        jLabel2.setText("DANH SÁCH DỊCH VỤ PHÒNG ĐÃ ĐĂNG KÝ:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        jLabel3.setText("DANH SÁCH DỊCH VỤ KHÁCH SẠN:");

        submitButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        submitButton.setText("LƯU THAY ĐỔI");
        submitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        submitButton.addActionListener(this::submitButtonActionPerformed);

        checkOutButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        checkOutButton.setText("CHECK OUT");
        checkOutButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        checkOutButton.addActionListener(this::checkOutButtonActionPerformed);

        refreshButton.setText("refresh service");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(rentDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(roomIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(departureDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(rentDateSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                                                .addComponent(departureDateSpinner))
                                                                        .addComponent(pricePredictText, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(roomIDText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(fullNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(38, 38, 38)
                                                .addComponent(identificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(identificationNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(51, 51, 51))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButton)
                                                .addGap(22, 22, 22))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(24, 24, 24))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(checkOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(59, 59, 59))))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(250, 250, 250)
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(fullNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(identificationNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(identificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(roomIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                        .addComponent(roomIDText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rentDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rentDateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(departureDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(departureDateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                        .addComponent(pricePredictText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(refreshButton))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void deleteRoomServiceMenuActionPerformed(java.awt.event.ActionEvent evt) {
        int row = roomServiceTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            String nameService = String.valueOf(roomServiceTable.getValueAt(row, 0));
            List<Service> services = roomController.getRoomDAO().getRoomServicesByProcessing(room.getId());

            services.removeIf(service -> service.getName().equals(nameService));
            room.setServices(services);

            pricePredictText.setText(String.valueOf(room.getPrice()));

            roomServiceModel.setRowCount(0);
            for (Service service1 : services) {
                roomServiceModel.addRow(new Object[]{service1.getName(), service1.getCost(), "Processing"});
            }
        }
    }

    private void addServiceMenuActionPerformed(java.awt.event.ActionEvent evt) {
        int row = serviceTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            String nameService = String.valueOf(serviceTable.getValueAt(row, 0));
            int costService = Integer.parseInt(String.valueOf(serviceTable.getValueAt(row, 1)));
            Service service = new Service(nameService, costService);
            List<Service> services = roomController.getRoomDAO().getRoomServicesByProcessing(room.getId());

            for (Service service1 : services) {
                if (service1.getName().equals(nameService)) {
                    JOptionPane.showMessageDialog(this, "Đã có dịch vụ nảy", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            services.add(service);

            room.setServices(services);
            pricePredictText.setText(String.valueOf(room.getPrice()));

            roomServiceModel.addRow(new Object[]{nameService, costService, "Processing"});
        }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        serviceModel.setRowCount(0);

        List<Service> services = roomController.getRoomDAO().getServiceDAO().getAllServices();
        for (Service service : services) {
            serviceModel.addRow(new Object[]{service.getName(), service.getCost()});
        }
    }

    private void departureDateSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {
        Date date = (Date) departureDateSpinner.getValue();
        LocalDate departureDate = convertToLocalDate(date);

        if (departureDate.isBefore(room.getRentDate()) || departureDate.equals(room.getRentDate())) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải sau ngày thuê", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        room.updateDepartureDate(departureDate);
        pricePredictText.setText(String.valueOf(room.getPrice()));
    }

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Date date = (Date) departureDateSpinner.getValue();
        LocalDate departureDate = convertToLocalDate(date);
        LocalDate rentDate = room.getRentDate();
        if (departureDate.isBefore(rentDate) || departureDate.equals(rentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải sau ngày thuê", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        roomController.getRoomDAO().updateRoomServices(room);
        roomController.getRoomDAO().updateDepartureDate(room);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
        this.dispose();
    }

    private void checkOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Date date = (Date) departureDateSpinner.getValue();
        LocalDate departureDate = convertToLocalDate(date);
        LocalDate rentDate = room.getRentDate();
        if (departureDate.isBefore(rentDate) || departureDate.equals(rentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải sau ngày thuê", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        roomController.getRoomDAO().updateRoomServices(room);
        roomController.getRoomDAO().updateDepartureDate(room);

        new BillInformationView(room.getId()).setVisible(true);

        this.dispose();
    }

    private LocalDate convertToLocalDate(Date utilDate) {
        Instant instant = utilDate.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
