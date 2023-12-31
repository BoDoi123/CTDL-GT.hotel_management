package view;

import lombok.Getter;
import lombok.Setter;

import controller.RoomController;
import models.Service;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class AddServiceView extends JFrame {
    private RoomController roomController;
    private JFormattedTextField costServiceField;
    private JTextField nameServiceField;

    public AddServiceView() {
        initComponents();
    }

    private void initComponents() {
        roomController = new RoomController();
        JLabel jLabel1 = new JLabel();
        JLabel nameService = new JLabel();
        nameServiceField = new JTextField();
        JLabel costService = new JLabel();
        costServiceField = new JFormattedTextField();
        JButton addServiceButton = new JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("THÊM DỊCH VỤ MỚI");

        nameService.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        nameService.setHorizontalAlignment(SwingConstants.CENTER);
        nameService.setText("TÊN DỊCH VỤ:");

        nameServiceField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // NOI18N

        costService.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        costService.setHorizontalAlignment(SwingConstants.CENTER);
        costService.setText("GIÁ DỊCH VỤ:");

        costServiceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        costServiceField.setPreferredSize(new java.awt.Dimension(64, 23));

        addServiceButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        addServiceButton.setText("Thêm dịch vụ");
        addServiceButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addServiceButton.addActionListener(this::addServiceButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(nameService, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(costService, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(addServiceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(nameServiceField)
                                                                .addComponent(costServiceField, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                                                .addGap(6, 6, 6)))
                                .addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameService, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameServiceField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(costService, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(costServiceField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addComponent(addServiceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void addServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nameService = String.valueOf(nameServiceField.getText());
        int costService = Integer.parseInt(costServiceField.getText());

        Service service = roomController.getRoomDAO().getServiceDAO().getServiceByName(nameService);
        if (costService < 0 || nameService == null) {
            JOptionPane.showMessageDialog(this, "Tên hoặc giá của dịch vụ không đúng hoặc không bỏ trống", "Thêm dịch vụ thất bại", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (service != null) {
            JOptionPane.showMessageDialog(this, "Dịch vụ này đã tồn tại", "Thêm dịch vụ thất bại", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Service service1 = new Service(nameService, costService);

        roomController.getRoomDAO().getServiceDAO().addService(service1);

        JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
        this.dispose();
    }
}