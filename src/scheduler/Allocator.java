package scheduler;

import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yibe1
 */
public class Allocator extends javax.swing.JFrame {

    private ArrayList<String> used = new ArrayList<>();//all assigned will be stored here. will be used to identify duplicates
    DefaultListModel tidMList = new DefaultListModel();
    DefaultListModel tidAList = new DefaultListModel();
    DefaultListModel tidNList = new DefaultListModel();
    DefaultListModel bidDList = new DefaultListModel();
    DefaultListModel bidNList = new DefaultListModel();
    DefaultListModel NList = new DefaultListModel();
    DefaultListModel EList = new DefaultListModel();
    ArrayList<String> pmselecteNames = new ArrayList<>();
    ArrayList<String> amselecteNames = new ArrayList<>();
    ArrayList<String> nmselecteNames = new ArrayList<>();

    int year;
    String mth;
    String wkday;

    private String rotation_frequency;
    private String rotationBase;
    private ArrayList<Object> Morning;
    private ArrayList<Object> Afternoon;
    private ArrayList<Object> Night;
    private ArrayList<Object> Day;
    private ArrayList<Object> Night2;
    private ArrayList<Object> Office_Hour;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final File file;
    private XSSFRow row;
    private XSSFCell cell;
    private XSSFCellStyle styleHeader, style;
    private XSSFFont font;
    private FileInputStream fileStream;
    private ArrayList<String> Week;
    private ArrayList<String> days;
    private ArrayList<String> WeekDays;
    private ArrayList<String> WeekNumber;
    private ArrayList<String> wks;
    private final ArrayList<Employe> empList;
    private boolean firstWk1 = true;
    private boolean firstWk2 = true;
    private boolean firstWk3 = true;
    private boolean firstWk4 = true;
    private boolean firstWk5 = true;
    private boolean firstWk6 = true;
    private String selectedShift;
    private String morningSelected;
    private String afterNoonSelected;
    private String nightSelected;
    private String daySelected;
    private String bidNightSelected;
    private int morningCount = 0;
    private int afternoonCount = 0;
    private int nightCount = 0;
    private int w1fd = 0;
    private int w1ld = 0;
    private int w2fd = 0;
    private int w2ld = 0;
    private int w3fd = 0;
    private int w3ld = 0;
    private int w4fd = 0;
    private int w4ld = 0;
    private int w5fd = 0;
    private int w5ld = 0;
    private int w6fd = 0;
    private int w6ld = 0;
    Queue<String> qtid;
    int morning = 0, afternoon = 0, night = 0;
    int jumper;//Rotation will based this jamper or it wont repeat this shift on next round

    /**
     * Creates new form allocator
     */
    public Allocator(int year, String month, String wkDay) throws IOException {
        initComponents();

        this.mth = month;
        this.year = year;
        this.wkday = wkDay;
        Repo repo = new Repo();
        empList = repo.getUser();
        DefaultListModel dList = new DefaultListModel();

        for (int i = 0; i < empList.size(); i++) {
            dList.addElement(empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname());
        }
        list.setModel(dList);

        ButtonGroup frequency = new ButtonGroup();
        frequency.add(weekly);
        frequency.add(everyNday);

        File dir2 = new File("Schedule\\" + year + "\\");
        if (!dir2.exists()) {
            dir2.mkdirs();
        }
        file = new File("Schedule\\" + year + ".xlsx");
        if (!file.exists()) {

            workbook = new XSSFWorkbook();
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                outputStream.close();
            }
        } else {
            fileStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileStream);
            fileStream.close();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        weekly = new javax.swing.JRadioButton();
        everyNday = new javax.swing.JRadioButton();
        N = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        selected_month = new javax.swing.JLabel();
        rotation_base = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mSpiner = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        aSpiner = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        nSpinner = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        exempt = new javax.swing.JList<>();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        selected_week = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel15.setText("Month");

        weekly.setText("Weekly");

        everyNday.setText("Every N Day");

        N.setText("0");

        jLabel20.setText("N=");

        jLabel21.setText("Rotation Frequency");

        jButton14.setBackground(new java.awt.Color(204, 0, 0));
        jButton14.setText("Generate");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        selected_month.setText(" ");

        rotation_base.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "select", "Morning", "Afternoon", "Night", "Day", " " }));

        jLabel16.setText("Rotation is based on");

        jLabel18.setText("Shift Size");

        jLabel22.setText("Morning");

        jLabel23.setText("Afternoon");

        jLabel24.setText("Night");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(18, 18, 18)
                                        .addComponent(weekly))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(aSpiner, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(nSpinner))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(everyNday))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(30, 30, 30)
                                .addComponent(rotation_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(N, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton14)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selected_month, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(selected_month))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(weekly)
                    .addComponent(everyNday)
                    .addComponent(N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotation_base, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(mSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(aSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(31, 31, 31)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        list.setFont(new java.awt.Font("Geez Able", 0, 12)); // NOI18N
        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        jLabel1.setText("All Staff");

        jButton13.setText("Allocate");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel17.setText("Exempted From Night Shift");

        exempt.setFont(new java.awt.Font("Geez Able", 0, 12)); // NOI18N
        jScrollPane8.setViewportView(exempt);

        jButton15.setText(">>>");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("<<<");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton16)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jButton15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton16))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setText("Selected Week");

        selected_week.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        selected_week.setForeground(new java.awt.Color(255, 0, 0));
        selected_week.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selected_week)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(selected_week))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:

        morning = (int) mSpiner.getValue();
        afternoon = (int) aSpiner.getValue();
        night = (int) nSpinner.getValue();

        switch ((String) rotation_base.getSelectedItem()) {
            case "Morning":
                jumper = morning;
                break;
            case "Afternoon":
                jumper = afternoon;
                break;
            case "Night":
                jumper = night;
                break;
        }
        if (weekly.isSelected()) {
            rotation_frequency = "weekly";//if it is not weekly, it will be changed to integer
        } else {
            rotation_frequency = N.getText();
            try {
                int x = Integer.parseInt(rotation_frequency);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Please Input a valid frequency number");
            }
        }
        rotationBase = (String) rotation_base.getSelectedItem();

//        Morning = new ArrayList<>();
//        int sizem = tidm.getModel().getSize();
//        for (int i = 0; i < sizem; i++) {
//            Morning.add(tidm.getModel().getElementAt(i));
//        }
        //Afternoon
//        Afternoon = new ArrayList<>();
//        int sizeaft = tida.getModel().getSize();
//        for (int i = 0; i < sizeaft; i++) {
//            Afternoon.add(tida.getModel().getElementAt(i));
//        }
        //Afternoon
//        Night = new ArrayList<>();
//        int sizen = tida.getModel().getSize();
//        for (int i = 0; i < sizen; i++) {
//            Night.add(tida.getModel().getElementAt(i));
//        }
//        Day = new ArrayList<>();
//        int sized = bidd.getModel().getSize();
//        for (int i = 0; i < sized; i++) {
//            Day.add(bidd.getModel().getElementAt(i));
//        }
//
//        Night2 = new ArrayList<>();
//        int sizen2 = bidn.getModel().getSize();
//        for (int i = 0; i < sizen2; i++) {
//            Night2.add(bidn.getModel().getElementAt(i));
//        }
//
//        Office_Hour = new ArrayList<>();
//        int sizeO = normal.getModel().getSize();
//        for (int i = 0; i < sizeO; i++) {
//            Office_Hour.add(normal.getModel().getElementAt(i));
//        }
        try {
            makeShift();
        } catch (IOException ex) {
            Logger.getLogger(Allocator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        int ind = list.getSelectedIndex();
        String value = list.getSelectedValue();
        if (EList.contains(value)) {
        } else {
            EList.addElement(value);
            exempt.setModel(EList);
        }
//        Repaint();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        String value = exempt.getSelectedValue();
        EList.removeElement(value);
//        Repaint();
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Allocator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Allocator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Allocator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Allocator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField N;
    private javax.swing.JSpinner aSpiner;
    private javax.swing.JRadioButton everyNday;
    private javax.swing.JList<String> exempt;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JList<String> list;
    private javax.swing.JSpinner mSpiner;
    private javax.swing.JSpinner nSpinner;
    private javax.swing.JComboBox<String> rotation_base;
    private javax.swing.JLabel selected_month;
    private javax.swing.JLabel selected_week;
    private javax.swing.JRadioButton weekly;
    // End of variables declaration//GEN-END:variables
 class MyListRenderer extends DefaultListCellRenderer {

        ArrayList<Integer> toBelist = new ArrayList<>();

        @Override
        public Component getListCellRendererComponent(JList list,
                Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            int count = 0;
            if (tidMList.contains(value)) {
                count++;
            }
            if (tidAList.contains(value)) {
                count++;
            }
            if (tidNList.contains(value)) {
                count++;
            }
            if (bidDList.contains(value)) {
                count++;
            }
            if (bidNList.contains(value)) {
                count++;
            }
            if (NList.contains(value)) {
                count++;
            }
            if (count > 1) {
                setForeground(Color.red);
            } else {

            }
            return (this);
        }
    }

    public void setDays() {

        days = new ArrayList<String>();
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");
        days.add("Sun");
        this.year = year;
        this.mth = mth;
        this.wkday = wkday;

        w1fd = 1;
        w1ld = 7 - days.indexOf(wkday);

        w2fd = w1ld + 1;
        w2ld = w2fd + 6;

        w3fd = w2ld + 1;
        w3ld = w3fd + 6;

        w4fd = w3ld + 1;
        w4ld = w4fd + 6;

        w5fd = w4ld + 1;
        w5ld = w5fd + 6;

        w6fd = w4ld + 1;
        w6ld = w5fd + 6;

        if (w5ld > 30) {
            w5ld = 30;
        }
        if (w5ld < 30) {
            w6fd = w5ld + 1;
            w6ld = w6fd + 6;
            if (w6ld > 30) {
                w6ld = 30;
            }
        }

    }
    ArrayList<String[]> staffs = new ArrayList<>();

    public void makeShift() throws FileNotFoundException, IOException {
        days = new ArrayList<String>();
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");
        days.add("Sun");

        wks = new ArrayList<String>();
        wks.add("wk1");
        wks.add("wk2");
        wks.add("wk3");
        wks.add("wk4");
        wks.add("wk5");
        wks.add("wk6");

        WeekDays = new ArrayList<String>();
        WeekNumber = new ArrayList<String>();
        int startDate = days.indexOf(wkday);
        int count = 0;
        for (int i = 0; i < 30; i++) {
            if (startDate == 7) {
                startDate = 0;
                count++;
            }
            WeekDays.add(days.get(startDate));
            WeekNumber.add(wks.get(count));
            startDate++;
        }
        count = 0;
        int colCount = 0, rowCount = 0;
        if (rotation_frequency.equals("weekly")) {
            sheet = workbook.createSheet(mth + "_Schedule");
            row = sheet.createRow(rowCount);
            cell = row.createCell(colCount);
            cell.setCellValue("Week");
            colCount++;
            rowCount++;
            for (int i = 0; i < 30; i++) {
                cell = row.createCell(colCount);
                cell.setCellValue(WeekNumber.get(i));
                colCount++;
            }

            colCount = 0;
            row = sheet.createRow(rowCount);
            cell = row.createCell(colCount);
            cell.setCellValue("Week Days");
            colCount++;
            for (int i = 0; i < 30; i++) {
                cell = row.createCell(colCount);
                cell.setCellValue(WeekDays.get(i));
                colCount++;
            }
            rowCount++;
            colCount = 0;
            row = sheet.createRow(rowCount);
            cell = row.createCell(colCount);
            cell.setCellValue("0");
            colCount++;
            for (int i = 0; i < 30; i++) {
                cell = row.createCell(colCount);
                cell.setCellValue(i + 1);
                colCount++;
            }
            ArrayList<String[]> orderedList = staffs;
            prepareArrayofStaffs();

            rowCount++;
            for (int i = 0; i < orderedList.size(); i++) {
                row = sheet.createRow(rowCount);
                for (int j = 0; j < 31; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(orderedList.get(i)[j]);
                    System.out.println("......" + orderedList.get(i)[j]);
                }
                rowCount++;
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                outputStream.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Error: " + e);
                workbook.removeSheetAt(workbook.getSheetIndex(mth + "_Schedule"));
            }

        } else {

        }

    }

    public void prepareArrayofStaffs() throws IOException {

        for (int i = 0; i < empList.size(); i++) {
            String name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            String[] st = new String[31];
            st[0] = name;
            staffs.add(st);
        }

        int wkcount = 5;
        if (wkday.equals("Sun")) {
            wkcount = 6;
        }
        for (int j = 0; j < wkcount; j++) {
            ArrayList<String> mElist = getMorning_eligible(morning);
            ArrayList<String> aElist = getAfternoon_eligible(afternoon);
            ArrayList<String> nElist = getNight_eligible(night);
            for (int i = 0; i < empList.size(); i++) {
                String name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
                if (mElist.contains(name)) {
                    fillWeeks(name, j, "M");
                } else if (aElist.contains(name)) {
                    fillWeeks(name, j, "A");
                } else if (nElist.contains(name)) {
                    fillWeeks(name, j, "N");
                }
            }
        }

    }

    public void fillWeeks(String staf, int wk, String shift) {
        setDays();
        int fday = 0, lday = 0;
        switch (wk) {
            case 0:
                fday = w1fd;
                lday = w1ld;
                break;
            case 1:
                fday = w2fd;
                lday = w2ld;
                break;
            case 2:
                fday = w3fd;
                lday = w3ld;
                break;
            case 3:
                fday = w4fd;
                lday = w4ld;
                break;
            case 4:
                fday = w5fd;
                lday = w5ld;
                break;
            case 5:
                fday = w6fd;
                lday = w6ld;
                break;
        }
        for (int i = 0; i < staffs.size(); i++) {
            String name = staffs.get(i)[0];
            if (name.equals(staf)) {
                for (int j = fday; j < lday + 1; j++) {
                    staffs.get(i)[j] = shift;
                }
                break;
            }
        }

    }

    public void rotateQ(int j) {
        for (int i = 0; i < j; i++) {
            String temp = qtid.element();
            qtid.poll();
            qtid.add(temp);
        }
    }

    public ArrayList<String> getMorning_eligible(int n) {

        ArrayList<String> currentNames = new ArrayList<>();
        sort("Morning");

        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < n; i++) {
            String name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            if (currentNames.contains(name) || pmselecteNames.contains(name)) {
                n++;
            } else {
                System.out.println("name = " + name);
                list.add(name);
                currentNames.add(name);
            }
        }
        System.out.println("");
        pmselecteNames.clear();
        pmselecteNames = currentNames;
        return list;
    }

    public ArrayList<String> getAfternoon_eligible(int n) {
        ArrayList<String> currentNames = new ArrayList<>();
        sort("Afternoon");

        ArrayList<String> list = new ArrayList();

        for (int i = 0; i < n; i++) {
            String name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            if (currentNames.contains(name) || amselecteNames.contains(name)) {
                n++;
            } else {
                list.add(name);
                currentNames.add(name);
            }

        }
        amselecteNames.clear();
        amselecteNames = currentNames;
        return list;
    }

    public ArrayList<String> getNight_eligible(int n) {

        sort("Night");
        ArrayList<String> currentNames = new ArrayList<>();
        ArrayList<String> list = new ArrayList();

        for (int i = 0; i < n; i++) {
            String name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            if (currentNames.contains(name) || nmselecteNames.contains(name)) {
                n++;
            } else {
                list.add(name);
                currentNames.add(name);
            }

        }
        nmselecteNames.clear();
        nmselecteNames = currentNames;
        return list;
    }

    public void sort(String type) {
        if (type.equals("Morning")) {
            Collections.sort(empList, new Comparator<Employe>() {
                @Override
                public int compare(Employe p1, Employe p2) {
                    return p1.getMcount() - p2.getMcount();
                }

            });

            return;
        }
        if (type.equals("Afternoon")) {
            Collections.sort(empList, new Comparator<Employe>() {
                @Override
                public int compare(Employe p1, Employe p2) {
                    return p1.getAcount() - p2.getAcount();
                }

            });

            return;
        }
        if (type.equals("Night")) {
            Collections.sort(empList, new Comparator<Employe>() {
                @Override
                public int compare(Employe p1, Employe p2) {
                    return p1.getNcount() - p2.getNcount();
                }

            });

            return;
        }

    }

}
