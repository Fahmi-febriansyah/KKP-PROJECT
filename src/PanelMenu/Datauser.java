package PanelMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

public class Datauser extends javax.swing.JPanel {

    public Datauser() {
        initComponents();
        jPasswor.setText("");
        tampilData(); // tampilkan data saat panel dibuka
        jBsimpan.addActionListener(e -> simpanData());
        jhapus.addActionListener(e -> hapusData());
        jubah.addActionListener(e -> ubahData());
        jtambah.addActionListener(e -> bersihkanForm());
        
        jTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable.getSelectedRow() != -1) {
                isiFieldDariTabel();
            }
        });
    }
    
  private void bersihkanForm() {
        jTnama.setText("");
        jTusername.setText("");
        jPasswor.setText("");
        jTalamat.setText("");
        jTnohp.setText("");
        jTable.clearSelection();
    }

    
    // === FUNGSI SIMPAN DATA ===
    private void simpanData() {
        String nama = jTnama.getText();
        String username = jTusername.getText();
        String password = String.valueOf(jPasswor.getPassword());
        String alamat = jTalamat.getText();
        String nohp = jTnohp.getText();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || alamat.isEmpty() || nohp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = koneksi.getKoneksi();
            String sql = "INSERT INTO user (nama, username, password, alamat, no_telp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, alamat);
            ps.setString(5, nohp);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data user berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData();
            bersihkanForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === FUNGSI TAMPILKAN DATA KE TABEL ===
    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Alamat");
        model.addColumn("No HP");

        try {
            Connection conn = koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("alamat"),
                    rs.getString("no_telp")
                });
            }

            jTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    // === FUNGSI HAPUS DATA ===
    private void hapusData() {
        int baris = jTable.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(jTable.getValueAt(baris, 0).toString());

        try {
            Connection conn = koneksi.getKoneksi();
            String sql = "DELETE FROM user WHERE id_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            tampilData();
            bersihkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    // === FUNGSI UBAH DATA ===
    private void ubahData() {
        int baris = jTable.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!");
            return;
        }

        int id = Integer.parseInt(jTable.getValueAt(baris, 0).toString());
        String nama = jTnama.getText();
        String username = jTusername.getText();
        String password = String.valueOf(jPasswor.getPassword());
        String alamat = jTalamat.getText();
        String nohp = jTnohp.getText();

        try {
            Connection conn = koneksi.getKoneksi();
            String sql = "UPDATE user SET nama=?, username=?, password=?, alamat=?, no_telp=? WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, alamat);
            ps.setString(5, nohp);
            ps.setInt(6, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            tampilData();
            bersihkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    // === FUNGSI ISI FIELD SAAT TABEL DIKLIK ===
    private void isiFieldDariTabel() {
        int baris = jTable.getSelectedRow();
        if (baris != -1) {
            jTnama.setText(jTable.getValueAt(baris, 1).toString());
            jTusername.setText(jTable.getValueAt(baris, 2).toString());
            jPasswor.setText(jTable.getValueAt(baris, 3).toString());
            jTalamat.setText(jTable.getValueAt(baris, 4).toString());
            jTnohp.setText(jTable.getValueAt(baris, 5).toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTnama = new javax.swing.JTextField();
        jTusername = new javax.swing.JTextField();
        jPasswor = new javax.swing.JPasswordField();
        jTalamat = new javax.swing.JTextField();
        jTnohp = new javax.swing.JTextField();
        jBsimpan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jubah = new javax.swing.JButton();
        jhapus = new javax.swing.JButton();
        jtambah = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Nama");

        jLabel3.setText("Usernme");

        jLabel4.setText("Password");

        jLabel5.setText("Alamat");

        jLabel6.setText("No. HP.");

        jTnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTnamaActionPerformed(evt);
            }
        });

        jPasswor.setText("jPasswordField1");

        jTalamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTalamatActionPerformed(evt);
            }
        });

        jBsimpan.setText("SIMPAN");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data_pelanggan.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel7.setText("DATA USER");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        jubah.setText("UBAH");

        jhapus.setText("HAPUS");

        jtambah.setText("bersihkan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 931, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jubah)
                        .addGap(76, 76, 76)
                        .addComponent(jBsimpan)
                        .addGap(78, 78, 78)
                        .addComponent(jhapus)
                        .addGap(50, 50, 50)
                        .addComponent(jtambah)
                        .addGap(194, 194, 194))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTusername)
                            .addComponent(jTnama)
                            .addComponent(jPasswor, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                            .addComponent(jTalamat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTnohp))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jPasswor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTalamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTnohp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtambah)
                            .addComponent(jhapus)
                            .addComponent(jBsimpan)
                            .addComponent(jubah))
                        .addGap(75, 75, 75))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
 
    private void jTnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTnamaActionPerformed

    private void jTalamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTalamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTalamatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBsimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField jTalamat;
    private javax.swing.JTextField jTnama;
    private javax.swing.JTextField jTnohp;
    private javax.swing.JTextField jTusername;
    private javax.swing.JButton jhapus;
    private javax.swing.JButton jtambah;
    private javax.swing.JButton jubah;
    // End of variables declaration//GEN-END:variables
}
