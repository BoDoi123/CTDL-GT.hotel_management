package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordView extends javax.swing.JFrame {
    private final UserController userController;
    private javax.swing.JPasswordField keyPassField;
    private javax.swing.JTextField accountField ;
    private javax.swing.JPasswordField newPasswordField;

    public ChangePasswordView(UserController userController) {
        initComponents();
        this.userController = userController;
    }

    private void initComponents() {
        JLabel titleLabel = new javax.swing.JLabel();
        JLabel keyLabel = new JLabel();
        keyPassField = new javax.swing.JPasswordField();
        JLabel accountLabel = new JLabel();
        JLabel passwordLabel = new JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        accountField = new javax.swing.JTextField();
        JButton changePasswordButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(490, 368));

        titleLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("THAY ĐỔI MẬT KHẨU");

        keyLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 15));
        keyLabel.setText("KEY PASS");

        keyPassField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 15));

        accountLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
        accountLabel.setText("TÊN ĐĂNG NHẬP");

        passwordLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        passwordLabel.setText("MẬT KHẨU MỚI");

        newPasswordField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N

        accountField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14)); // NOI18N

        changePasswordButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        changePasswordButton.setText("THAY ĐỔI MẬT KHẨU");
        changePasswordButton.setToolTipText("");
        changePasswordButton.setBorder(null);
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(keyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(keyPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(newPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(accountField, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                                                        .addComponent(changePasswordButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(68, 68, 68))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(accountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(keyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(keyPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(accountLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accountField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String keyPass = String.valueOf(keyPassField.getPassword());
        String username = String.valueOf(accountField.getText());
        String newPassword = String.valueOf(newPasswordField.getPassword());

        if (keyPass.equals("ManagerHotel")) {
            if (userController.changePassword(username, newPassword)) {
                JOptionPane.showMessageDialog(this, "Thành công", "Đổi mật khẩu thành công", JOptionPane.PLAIN_MESSAGE);

                LoginView loginView = new LoginView(userController);

                loginView.setVisible(true);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản sai", "Đổi mật khẩu thất bại", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Keypass không phù hợp", "Đổi mật khẩu thất bại", JOptionPane.WARNING_MESSAGE);
        }
    }
}
