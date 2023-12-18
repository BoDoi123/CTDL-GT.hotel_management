package view;

import models.Service;
import dao.ServiceDAO;

import javax.swing.*;
import java.awt.*;

public class EditServiceView extends javax.swing.JFrame {
    private ServiceDAO serviceDAO;
    private javax.swing.JFormattedTextField costServiceField;
    private javax.swing.JTextField nameServiceField;

    public EditServiceView(String nameService, int costService) {
        initComponents();
        nameServiceField.setText(nameService);
        nameServiceField.setEditable(false);
        costServiceField.setText(String.valueOf(costService));
    }

    private void initComponents() {
        serviceDAO = new ServiceDAO();
        JLabel editLabel = new JLabel();
        JLabel nameServiceLabel = new JLabel();
        nameServiceField = new javax.swing.JTextField();
        JLabel costServiceLabel1 = new JLabel();
        costServiceField = new javax.swing.JFormattedTextField();
        JButton submitButton = new JButton();

        editLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        editLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editLabel.setText("SỬA ĐỔI GIÁ DỊCH VỤ");

        nameServiceLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        nameServiceLabel.setText("TÊN DỊCH VỤ:");

        costServiceLabel1.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        costServiceLabel1.setText("GIÁ DỊCH VỤ:");

        costServiceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        costServiceField.setPreferredSize(new java.awt.Dimension(64, 30));

        submitButton.setText("THAY ĐỔI GIÁ DỊCH VỤ");
        submitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        submitButton.addActionListener(this::submitButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(nameServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(nameServiceField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(costServiceLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(costServiceField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameServiceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(costServiceLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(costServiceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nameService = String.valueOf(nameServiceField.getText());
        int newCostService = Integer.parseInt(costServiceField.getText());

        if (newCostService < 0 || nameService == null) {
            JOptionPane.showMessageDialog(this, "Giá của dịch vụ không bỏ trống", "Sửa dịch vụ thất bại", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Service service = new Service(nameService, newCostService);
        serviceDAO.updateService(service);

        JOptionPane.showMessageDialog(this, "Thay đổi thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
        this.dispose();
    }
}
