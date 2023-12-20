package view;

import controller.UserController;
import dao.UserDAO;
import dao.EmployeeDAO;
import models.Employee;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class LoginView extends javax.swing.JFrame {
    private final EmployeeDAO employeeDAO;
    private final UserController userController;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField accountField;
    private javax.swing.JCheckBox visibleCheckBox;

    public LoginView() {
        initComponents();
        UserDAO userDAO = new UserDAO();
        userController = new UserController(userDAO);
        employeeDAO = new EmployeeDAO();
    }

    public LoginView(UserController userController) {
        initComponents();
        this.userController = userController;
        employeeDAO = new EmployeeDAO();
    }

    private void initComponents() {

        JLabel titleLabel = new JLabel();
        JLabel accountLabel = new JLabel();
        accountField = new javax.swing.JTextField();
        JLabel passwordLabel = new javax.swing.JLabel();
        visibleCheckBox = new JCheckBox();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton();
        JButton forgetPasswordButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐĂNG NHẬP HỆ THÔNG");
        setBackground(new java.awt.Color(51, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18));

        titleLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("ĐĂNG NHẬP HỆ THỐNG");

        accountLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
        accountLabel.setText("TÊN ĐĂNG NHẬP");

        accountField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14));

        passwordLabel.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
        passwordLabel.setText("MẬT KHẨU");

        visibleCheckBox.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
        visibleCheckBox.setText("Hiển thị mật khẩu");
        visibleCheckBox.setToolTipText("");
        visibleCheckBox.addActionListener(this::visibleCheckBoxActionPerformed);

        passwordField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14));

        loginButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16));
        loginButton.setText("ĐĂNG NHẬP");
        loginButton.setBorder(null);
        loginButton.addActionListener(this::loginButtonActionPerformed);

        forgetPasswordButton.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
        forgetPasswordButton.setText("Quên mật khẩu");
        forgetPasswordButton.addActionListener(this::forgetPasswordButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(accountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(accountField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(visibleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(forgetPasswordButton, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                                        .addComponent(passwordField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(accountLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accountField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(visibleCheckBox)
                                        .addComponent(forgetPasswordButton))
                                .addGap(27, 27, 27)
                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    // Hiển thị mật khẩu
    private void visibleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        if (visibleCheckBox.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('*');
        }
    }

    // Đăng nhập vào hệ thống
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        createUserAndEmployee();
        String account = String.valueOf(accountField.getText());
        String password = String.valueOf(passwordField.getPassword());

        if (userController.loginUser(account, password)) {
            JOptionPane.showMessageDialog(this, "Bắt đầu làm việc", "Đăng nhập thành công", JOptionPane.PLAIN_MESSAGE);

            SystemView systemView = new SystemView(userController.getUserDAO().getIDByUsername(account));
            systemView.setVisible(true);
            systemView.setExtendedState(JFrame.MAXIMIZED_BOTH);

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu sai", "Đăng nhập thất bại", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Thay đổi mật khẩu khi quên mật khẩu
    private void forgetPasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ChangePasswordView changePasswordView = new ChangePasswordView(userController);

        changePasswordView.setVisible(true);

        this.dispose();
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
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LoginView().setVisible(true));
    }

    private void createUserAndEmployee() {
        User user = new User("user1", "123456", User.Role.Manager);
        userController.getUserDAO().addUser(user);
        Employee employee = new Employee(user, "Chu Long", "Ha Noi", "1234567", LocalDate.of(2002, 05, 22), Employee.Gender.Male, 10000000);
        employeeDAO.addEmployee(employee);
    }
}
