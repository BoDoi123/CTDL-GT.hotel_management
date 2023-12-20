package view;

import lombok.Getter;
import lombok.Setter;

import controller.RoomController;
import models.Bill;
import models.Customer;
import models.Room;
import models.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BillInformationView extends javax.swing.JFrame {
    private int roomID;
    private Room room;
    private RoomController roomController;
    private JSpinner dateSpinner;
    private final int billID = room.getBillID();

    public BillInformationView(int roomID) {
        initComponents(roomID, billID);
    }

    private void initComponents(int roomID, int billID) {
        roomController = new RoomController();
        JLabel billLabel = new JLabel();
        JLabel billIdLabel = new JLabel();
        JLabel roomIdText = new JLabel();
        JLabel roomIdLabel = new JLabel();
        JLabel billIdText = new JLabel();
        JLabel fullNameLabel = new JLabel();
        JLabel fullNameText = new JLabel();
        JLabel customerIdLabel = new JLabel();
        JLabel customerIdText = new JLabel();
        JLabel dateLabel = new JLabel();
        dateSpinner = new JSpinner();
        JLabel dateLabel1 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable roomServiceTable = new JTable();
        JLabel serviceLabel = new JLabel();
        JLabel numberDayText = new JLabel();
        JLabel numberDaysLabel1 = new JLabel();
        JLabel jLabel1 = new JLabel();
        JLabel numberDaysLabel2 = new JLabel();
        JLabel serviceCostText = new JLabel();
        JLabel roomIdLabel1 = new JLabel();
        JLabel roomIdText1 = new JLabel();
        JButton submitButton = new JButton();
        JLabel priceBillText = new JLabel();

        Bill bill = roomController.getRoomDAO().getBillDAO().getBillById(billID);
        Customer customer = roomController.getRoomDAO().getCustomerDAO().getCustomerByID(bill.getRenterID());
        room = roomController.getRoomDAO().getRoomByID(roomID);

        billLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        billLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        billLabel.setText("THÔNG TIN HÓA ĐƠN");

        billIdLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        billIdLabel.setText("Mã hóa đơn:");

        roomIdText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        roomIdText.setText(String.valueOf(roomID));

        roomIdLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        roomIdLabel.setText("Mã phòng:");

        billIdText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        billIdText.setText(String.valueOf(billID));

        fullNameLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        fullNameLabel.setText("Tên khách hàng:");

        fullNameText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        fullNameText.setText(customer.getName());

        customerIdLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        customerIdLabel.setText("Mã khách hàng:");

        customerIdText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        customerIdText.setText(String.valueOf(customer.getId()));

        dateLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        dateLabel.setText("Ngày thanh toán:");

        LocalDate date = LocalDate.now();
        Date defaultDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateSpinner.setModel(new javax.swing.SpinnerDateModel(defaultDate, null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        JComponent editor = dateSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
            defaultEditor.getTextField().setEditable(false);
        }

        dateSpinner.setEnabled(false);

        dateLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        dateLabel1.setText("Tổng Tiền    =");

        DefaultTableModel roomServiceModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomServiceTable.setModel(roomServiceModel);
        roomServiceTable.setCellSelectionEnabled(false);
        roomServiceModel.addColumn("service_name");
        roomServiceModel.addColumn("cost");

        List<Service> serviceList = room.getServices();
        for (Service service : serviceList) {
            roomServiceModel.addRow(new Object[]{service.getName(), service.getCost()});
        }
        jScrollPane1.setViewportView(roomServiceTable);

        serviceLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        serviceLabel.setText("Dịch vụ đã dùng");

        numberDayText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        numberDayText.setText(String.valueOf(room.getSimplePriceCalculator().getNumberOfDays()));

        numberDaysLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        numberDaysLabel1.setText("Số ngày ở:");

        jLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");

        numberDaysLabel2.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        numberDaysLabel2.setText("Tổng tiền dịch vụ và tiền phòng:");

        serviceCostText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        int costRoom = room.getSimplePriceCalculator().getPricePerDay();
        for (Service service : serviceList) {
            costRoom += service.getCost();
        }
        serviceCostText.setText(String.valueOf(costRoom));

        roomIdLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        roomIdLabel1.setText("Tiền phòng / ngày:");

        roomIdText1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        roomIdText1.setText(String.valueOf(room.getSimplePriceCalculator().getPricePerDay()));

        submitButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        submitButton.setText("Thanh toán");
        submitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        submitButton.addActionListener(this::submitButtonActionPerformed);

        priceBillText.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        priceBillText.setText(String.valueOf(bill.getPrice()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fullNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(customerIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(billIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dateLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(serviceLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(priceBillText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(fullNameText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                .addGap(1, 1, 1)
                                                                                .addComponent(customerIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(87, 87, 87)
                                                                                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(6, 6, 6)
                                                                                .addComponent(billIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(roomIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(roomIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(roomIdLabel1)))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(roomIdText1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(numberDaysLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(numberDayText, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(numberDaysLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(serviceCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(billIdText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(billIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(roomIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                                                .addComponent(roomIdText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(roomIdText1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(roomIdLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fullNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fullNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(customerIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(customerIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(dateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(serviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(dateLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(numberDaysLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(numberDayText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(numberDaysLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(serviceCostText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(priceBillText, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(58, 58, 58))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28))))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Thanh toán thành công \n Trả phòng", "Thông báo", JOptionPane.PLAIN_MESSAGE);
        room.checkout();
        roomController.getRoomDAO().checkOutRoom(room);
        this.dispose();
    }
}
