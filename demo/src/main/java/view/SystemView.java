package view;

import controller.RoomController;
import models.Customer;
import models.Employee;
import models.Room;
import models.Service;
import dao.EmployeeDAO;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SystemView extends javax.swing.JFrame {
    private EmployeeDAO employeeDAO;
    private DefaultTableModel roomModel;
    private DefaultTableModel serviceModel;
    private RoomController roomController;
    private javax.swing.JCheckBox maleCheckbox;
    private javax.swing.JCheckBox femaleCheckbox;
    private javax.swing.JFormattedTextField identificationNumberField;
    private javax.swing.JSpinner birthDaySpinner;
    private javax.swing.JSpinner departureDateSpinner;
    private javax.swing.JTextField fullNameTextField;
    private javax.swing.JTextField homeTownTextField;
    private javax.swing.JTable roomTable;
    private javax.swing.JTable serviceTable;
    private javax.swing.JLabel staffLabel;

    public SystemView(int userID) {
        initComponents();
        Employee employee = employeeDAO.getEmployeeByUserID(userID);
        staffLabel.setText("Nhân viên: " + employee.getName());
    }

    public SystemView() {
        initComponents();
    }


    private void initComponents() {
        roomController = new RoomController();
        employeeDAO = new EmployeeDAO();
        JPanel abilityPanel = new JPanel();
        JButton homeButton = new JButton();
        JButton roomsButton = new JButton();
        JButton staffButton = new JButton();
        JButton customerButton = new JButton();
        JButton settingButton = new JButton();
        JButton logoutButton = new JButton();
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel checkInLabel = new JLabel();
        JLabel checkoutLabel = new JLabel();
        staffLabel = new JLabel();
        JFormattedTextField checkInTimeField = new JFormattedTextField();
        JFormattedTextField checkoutTimeField = new JFormattedTextField();
        JPanel jPanel1 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        roomTable = new JTable();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        fullNameTextField = new JTextField();
        maleCheckbox = new JCheckBox();
        femaleCheckbox = new JCheckBox();
        birthDaySpinner = new JSpinner();
        identificationNumberField = new JFormattedTextField();
        homeTownTextField = new JTextField();
        JLabel jLabel9 = new JLabel();
        departureDateSpinner = new JSpinner();
        JLabel jLabel8 = new JLabel();
        JScrollPane jScrollPane2 = new JScrollPane();
        serviceTable = new JTable();
        JButton rentRoomButton = new JButton();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenu jMenu2 = new JMenu();
        JButton refreshButtonRoom = new JButton();
        JButton refreshButtonService = new JButton();
        JButton addServiceButton = new JButton();
        JButton addRoomButton = new JButton();
        JPopupMenu servicePopupMenu = new JPopupMenu();
        JMenuItem deleteServiceMenuItem = new JMenuItem();
        JMenuItem editServiceMenuItem = new JMenuItem();

        deleteServiceMenuItem.setText("Delete Service");
        servicePopupMenu.add(deleteServiceMenuItem);
        deleteServiceMenuItem.addActionListener(this::deleteServiceMenuItemActionPerformed);

        editServiceMenuItem.setText("Edit Service");
        servicePopupMenu.add(editServiceMenuItem);
        editServiceMenuItem.addActionListener(this::editServiceMenuItemActionPerformed);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ KHÁCH SẠN");

        abilityPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 13)); // NOI18N
        homeButton.setIcon(new ImageIcon(getPath("src/main/resources/static/7310961_snow_home_christmas_xmas_house_icon.png"))); // NOI18N
        homeButton.setText("HOME");
        homeButton.setHorizontalAlignment(SwingConstants.LEFT);

        roomsButton.setFont(new Font("Times New Roman", Font.BOLD, 12)); // NOI18N
        roomsButton.setIcon(new ImageIcon(getPath("src/main/resources/static/4171360_bed_couple_day_love_lover_icon.png"))); // NOI18N
        roomsButton.setText("QUẢN LÝ PHÒNG");
        roomsButton.setHorizontalAlignment(SwingConstants.LEFT);
        roomsButton.setPreferredSize(new Dimension(122, 55));

        staffButton.setFont(new Font("Times New Roman", Font.BOLD, 12)); // NOI18N
        staffButton.setIcon(new ImageIcon(getPath("src/main/resources/static/6491361_baggage_bellhop_hotel_service_waiter_icon.png"))); // NOI18N
        staffButton.setText("NHÂN VIÊN");
        staffButton.setHorizontalAlignment(SwingConstants.LEFT);
        staffButton.setPreferredSize(new Dimension(122, 55));

        customerButton.setFont(new Font("Times New Roman", Font.BOLD, 12)); // NOI18N
        customerButton.setIcon(new ImageIcon(getPath("src/main/resources/static/4714992_avatar_man_people_person_profile_icon.png"))); // NOI18N
        customerButton.setText("KHÁCH HÀNG");
        customerButton.setHorizontalAlignment(SwingConstants.LEFT);
        customerButton.setPreferredSize(new Dimension(122, 55));

        settingButton.setFont(new Font("Times New Roman", Font.BOLD, 12)); // NOI18N
        settingButton.setIcon(new ImageIcon(getPath("src/main/resources/static/3844474_gear_setting_settings_wheel_icon.png"))); // NOI18N
        settingButton.setText("CÀI ĐẶT");
        settingButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingButton.setPreferredSize(new Dimension(122, 55));

        logoutButton.setFont(new Font("Times New Roman", Font.BOLD, 12)); // NOI18N
        logoutButton.setIcon(new ImageIcon(getPath("src/main/resources/static/10132175_logout_line_icon.png"))); // NOI18N
        logoutButton.setText("ĐĂNG XUẤT");
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setPreferredSize(new Dimension(122, 55));
        logoutButton.addActionListener(this::logoutButtonActionPerformed);

        GroupLayout abiltyPanelLayout = new GroupLayout(abilityPanel);
        abilityPanel.setLayout(abiltyPanelLayout);
        abiltyPanelLayout.setHorizontalGroup(
                abiltyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(abiltyPanelLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(abiltyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(homeButton, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                        .addComponent(roomsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(staffButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(customerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(settingButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        abiltyPanelLayout.setVerticalGroup(
                abiltyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(abiltyPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(homeButton)
                                .addGap(18, 18, 18)
                                .addComponent(roomsButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(staffButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(customerButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(settingButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16)); // NOI18N
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("QUẢN LÝ KHÁCH SẠN");
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        checkInLabel.setFont(new Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        checkInLabel.setText("Giờ check-in:");

        checkoutLabel.setFont(new Font("Times New Roman", Font.BOLD, 14)); // NOI18N
        checkoutLabel.setText("Giờ check-out:");

        staffLabel.setFont(new Font("Times New Roman", Font.BOLD, 14)); // NOI18N

        checkInTimeField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getTimeInstance(DateFormat.SHORT))));
        checkInTimeField.setText("8:00 AM");

        checkoutTimeField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getTimeInstance(DateFormat.SHORT))));
        checkoutTimeField.setText("2:30 PM");

        GroupLayout titlePanelLayout = new GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
                titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(checkInLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(checkoutLabel, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(checkInTimeField, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkoutTimeField, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(staffLabel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(31, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
                titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(checkInLabel)
                                        .addComponent(checkInTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(checkoutLabel)
                                        .addComponent(checkoutTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(staffLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
        );

        jLabel2.setFont(new Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        jLabel2.setText("Phòng trống (Chọn phòng):");

        jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        jLabel1.setText("Thông tin khách hàng");

        jLabel3.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel3.setText("Họ và tên:");

        jLabel4.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel4.setText("Giới tính:");

        jLabel5.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel5.setText("Ngày sinh:");

        jLabel6.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel6.setText("CMND/CCCD:");

        jLabel7.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel7.setText("Quê quán:");

        fullNameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15)); // NOI18N

        maleCheckbox.setText("Male");

        femaleCheckbox.setText("Female");

        ItemListener itemListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == maleCheckbox) {
                    femaleCheckbox.setSelected(false);
                } else if (e.getItem() == femaleCheckbox) {
                    maleCheckbox.setSelected(false);
                }
            }
        };
        femaleCheckbox.addItemListener(itemListener);
        maleCheckbox.addItemListener(itemListener);

        birthDaySpinner.setModel(new SpinnerDateModel());
        birthDaySpinner.setEditor(new JSpinner.DateEditor(birthDaySpinner, "dd/MM/yyyy"));

        identificationNumberField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#"))));
        identificationNumberField.addPropertyChangeListener(this::identificationNumberPropertyChange);

        homeTownTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15)); // NOI18N

        jLabel9.setFont(new Font("Times New Roman", Font.BOLD, 15)); // NOI18N
        jLabel9.setText("Ngày trả dự kiến:");

        departureDateSpinner.setModel(new SpinnerDateModel());
        departureDateSpinner.setEditor(new JSpinner.DateEditor(departureDateSpinner, "dd/MM/yyyy"));
        departureDateSpinner.addPropertyChangeListener(this::departureDatePropertyChange);

        jLabel8.setFont(new Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        jLabel8.setText("Danh sách dịch vụ (Tích chọn):");

        roomModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable.setModel(roomModel);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomModel.addColumn("id");
        roomModel.addColumn("statement");
        roomModel.addColumn("price/day");

        List<Room> rooms = roomController.getAllRoomWithStateFalse();
        for (Room room : rooms) {
            roomModel.addRow(new Object[]{room.getId(), "Phòng trống", room.getPrice()});
        }
        jScrollPane1.setViewportView(roomTable);

        serviceModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        serviceTable.setModel(serviceModel);
        serviceTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        serviceModel.addColumn("service_name");
        serviceModel.addColumn("cost");

        List<Service> services = roomController.getRoomDAO().getServiceDAO().getAllServices();
        for (Service service : services) {
            serviceModel.addRow(new Object[]{service.getName(), service.getCost()});
        }
        jScrollPane2.setViewportView(serviceTable);
        serviceTable.setComponentPopupMenu(servicePopupMenu);

        rentRoomButton.setFont(new Font("Times New Roman", Font.BOLD, 18)); // NOI18N
        rentRoomButton.setText("Bắt đầu thuê");
        rentRoomButton.addActionListener(this::rentRoomActionPerformed);

        refreshButtonRoom.setText("Refresh");
        refreshButtonRoom.addActionListener(this::refreshButtonRoomActionPerformed);

        refreshButtonService.setText("Refresh");
        refreshButtonService.addActionListener(this::refreshButtonServiceActionPerformed);

        addServiceButton.setText("AddService");
        addServiceButton.addActionListener(this::addServiceButtonActionPerformed);

        addRoomButton.setText("AddRoom");
        addRoomButton.addActionListener(this::addRoomButtonActionPerformed);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                                                                .addGap(34, 34, 34)
                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(homeTownTextField)
                                                                        .addComponent(identificationNumberField)
                                                                        .addComponent(departureDateSpinner)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(68, 68, 68)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                .addComponent(maleCheckbox, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(30, 30, 30)
                                                                                                .addComponent(femaleCheckbox, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                                                                                        .addComponent(fullNameTextField, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(birthDaySpinner, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(rentRoomButton, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addServiceButton))
                                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButtonService))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(addRoomButton))
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButtonRoom)))
                                .addContainerGap(441, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addRoomButton))
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(fullNameTextField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(maleCheckbox)
                                                        .addComponent(femaleCheckbox))
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(birthDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(identificationNumberField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(homeTownTextField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(refreshButtonRoom)
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(departureDateSpinner, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addServiceButton))
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(rentRoomButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addGap(113, 113, 113))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(refreshButtonService)
                                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(titlePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(abilityPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(abilityPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void refreshButtonRoomActionPerformed(java.awt.event.ActionEvent evt) {
        roomModel.setRowCount(0);

        List<Room> rooms = roomController.getAllRoomWithStateFalse();
        for (Room room : rooms) {
            roomModel.addRow(new Object[]{room.getId(), "Phòng trống", room.getPrice()});
        }
    }

    private void refreshButtonServiceActionPerformed(java.awt.event.ActionEvent evt) {
        serviceModel.setRowCount(0);

        List<Service> services = roomController.getRoomDAO().getServiceDAO().getAllServices();
        for (Service service : services) {
            serviceModel.addRow(new Object[]{service.getName(), service.getCost()});
        }
    }

    private void addRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Room room = new Room();
        roomController.getRoomDAO().addRoom(room);

        JOptionPane.showMessageDialog(this, "Thêm phòng thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
    }

    private void addServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        AddServiceView addServiceView = new AddServiceView();

        addServiceView.setVisible(true);
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LoginView loginView = new LoginView();

        loginView.setVisible(true);

        this.dispose();
    }
    
    private void deleteServiceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        int row = serviceTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ không?");

            if (confirm == JOptionPane.YES_OPTION) {
                String serviceName = String.valueOf(serviceTable.getValueAt(row, 0));

                roomController.getRoomDAO().getServiceDAO().deleteService(serviceName);
                JOptionPane.showMessageDialog(this, "Đã xóa dịch vụ thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private void editServiceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        int row = serviceTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ trước", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            String nameService = String.valueOf(serviceTable.getValueAt(row, 0));
            int costService = Integer.parseInt(String.valueOf(serviceTable.getValueAt(row, 1)));

            new EditServiceView(nameService, costService).setVisible(true);
        }
    }

    private void rentRoomActionPerformed(java.awt.event.ActionEvent evt) {
        LocalDate rentDate = LocalDate.now();
        Date date = (Date) departureDateSpinner.getValue();
        LocalDate departureDate = convertToLocalDate(date);

        Date date1 = (Date) birthDaySpinner.getValue();
        LocalDate birthday =  convertToLocalDate(date1);
        String hometown = String.valueOf(homeTownTextField.getText());

        String nameCustomer = String.valueOf(fullNameTextField.getText());
        String identification = String.valueOf(identificationNumberField.getText());

        int[] selectedRow = serviceTable.getSelectedRows();
        int row = roomTable.getSelectedRow();

        if (nameCustomer == null || birthday == null || identification == null || hometown == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống dữ liệu", "Thuê phòng thất bại", JOptionPane.WARNING_MESSAGE);
        } else if (rentDate.isAfter(departureDate)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phòng phải sau ngày thuê", "Thuê phòng thất bại", JOptionPane.PLAIN_MESSAGE);
        }

        // Chọn phòng thuê
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng trống", "Thuê phòng thất bại", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int roomID = Integer.parseInt(String.valueOf(roomTable.getValueAt(row, 0)));
        Room room = roomController.getRoomDAO().getRoomByID(roomID);

        Customer customer = roomController.getRoomDAO().getCustomerDAO().getCustomerByIdentification(identification);

        // Nếu là khách hàng quen
        if (customer != null) {
            room.rentRoom(customer, rentDate, departureDate);

            // Chọn danh sách dịch vụ
            for (int i : selectedRow) {
                String serviceName = String.valueOf(serviceTable.getValueAt(i, 0));

                room.addService(roomController.getRoomDAO().getServiceDAO().getServiceByName(serviceName));
            }

            roomController.getRoomDAO().rentRoom(room);
            JOptionPane.showMessageDialog(this, "Thuê phòng thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        // Khách hàng mới
        if (maleCheckbox.isSelected()) {
            customer = new Customer(nameCustomer, Customer.Gender.Male, birthday, identification, hometown);
        } else if (femaleCheckbox.isSelected()) {
            customer = new Customer(nameCustomer, Customer.Gender.Female, birthday, identification, hometown);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính", "Thuê phòng thất bại", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        assert customer != null;
        roomController.getRoomDAO().getCustomerDAO().addCustomer(customer);

        // Bắt đầu thuê phòng
        room.rentRoom(customer, rentDate, departureDate);

        // Chọn danh sách dịch vụ
        for (int i : selectedRow) {
            String serviceName = String.valueOf(serviceTable.getValueAt(i, 0));

            room.addService(roomController.getRoomDAO().getServiceDAO().getServiceByName(serviceName));
        }

        roomController.getRoomDAO().rentRoom(room);
        JOptionPane.showMessageDialog(this, "Thuê phòng thành công", "Thông báo", JOptionPane.PLAIN_MESSAGE);
    }

    private void identificationNumberPropertyChange(java.beans.PropertyChangeEvent evt) {
        String identification = String.valueOf(identificationNumberField.getText());

        Customer customer = roomController.getRoomDAO().getCustomerDAO().getCustomerByIdentification(identification);
        if (customer != null) {
            JOptionPane.showMessageDialog(this, "Khách hàng đã có trong dữ liệu của khách sạn \n Chỉ cần nhập CMND/CCCD là được.", "Thông báo", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void departureDatePropertyChange(java.beans.PropertyChangeEvent evt) {
        Date date = (Date) departureDateSpinner.getValue();

        LocalDate departureDate = convertToLocalDate(date);
        LocalDate rentDate = LocalDate.now();

        if (departureDate.isBefore(rentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phải sau ngày sau thuê", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private LocalDate convertToLocalDate(Date utilDate) {
        Instant instant = utilDate.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String getPath(String path) {
        File file = new File(path);

        return file.getAbsolutePath();
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                 IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SystemView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new SystemView();
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}
