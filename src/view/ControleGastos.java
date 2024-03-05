package view;

import java.sql.*;
import controller.GastosController;
import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ControleGastos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ControleGastos() {
        initComponents();
        conexao = GastosController.conector();
    }

    private void inserirRegistro() {
        
        String sql = "INSERT INTO registros(descricao,mes,ano,tipo,valor) "
                + "VALUES (?,?,?,?,?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, txtDescricao.getText());
            ps.setString(2, cmbMes.getSelectedItem().toString());
            ps.setString(3, txtAno.getText());
            ps.setString(4, cmbTipo.getSelectedItem().toString());
            ps.setDouble(5, Double.parseDouble(txtValor.getText()));

            if (txtDescricao.getText().isEmpty() || cmbMes.getSelectedItem()
                    .toString() == null || txtAno.getText().isEmpty()
                    || cmbTipo.getSelectedItem().toString() == null
                    || txtValor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!");
            } else {
                int adicionado = ps.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Adicionado com sucesso!");
                    txtDescricao.setText("");
                    cmbMes.setSelectedItem("Janeiro");
                    txtAno.setText("");
                    txtValor.setText("");
                    cmbTipo.setSelectedItem("Entrada");
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisarRegistro() {
        String sql = "SELECT * FROM "
                + "registros WHERE mes like ? OR ano LIKE ? ORDER BY ano";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, txtPesquisar.getText() + "%");
            ps.setString(2, txtPesquisar.getText() + "%");

            rs = ps.executeQuery();

            tblRegistros.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCampos() {

        int setar = tblRegistros.getSelectedRow();
        
        txtDescricao.setText(tblRegistros.getModel()
                .getValueAt(setar, 1).toString());
        
        cmbMes.setSelectedItem(tblRegistros.getModel()
                .getValueAt(setar, 2).toString());
        
        txtAno.setText(tblRegistros.getModel()
                .getValueAt(setar, 3).toString());
        
        cmbTipo.setSelectedItem(tblRegistros.getModel()
                .getValueAt(setar, 4).toString());
        
        txtValor.setText(tblRegistros.getModel()
                .getValueAt(setar, 5).toString());
        
        btnSalvar.setEnabled(false);
        
    }

    private void atualizarRegistro() {

        String sql = "UPDATE registros set descricao=?, mes=?, ano=?, tipo=?, "
                + "valor=? WHERE idregistros=?";
        
        int setar = tblRegistros.getSelectedRow();
        
        try {
            ps = conexao.prepareStatement(sql);
              
            ps.setString(1, txtDescricao.getText());
            ps.setString(2, cmbMes.getSelectedItem().toString());
            ps.setString(3, txtAno.getText());
            ps.setString(4, cmbTipo.getSelectedItem().toString());
            ps.setDouble(5, Double.parseDouble(txtValor.getText()));
            ps.setInt(6, Integer.parseInt(tblRegistros.getModel().
                    getValueAt(setar, 0).toString()));

            if (txtDescricao.getText().isEmpty() || 
                    cmbMes.getSelectedItem().toString() == null || 
                    txtAno.getText().isEmpty() || 
                    cmbTipo.getSelectedItem().toString() == null || 
                    txtValor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                        "Preencha todos os campos!");
            } else {
                int adicionado = ps.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, 
                            "Atualizado com sucesso!");
                    txtDescricao.setText("");
                    cmbMes.setSelectedItem("Janeiro");
                    txtAno.setText("");
                    txtValor.setText("");
                    cmbTipo.setSelectedItem("Entrada");
                    
                    btnSalvar.setEnabled(true);
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    private void excluirRegistro(){
        int setar = tblRegistros.getSelectedRow();
        int confirma = JOptionPane.showConfirmDialog(null, 
                "Tem certeza que deseja remover este registro?", 
                "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM registros WHERE idregistros=?";
            try {
                ps = conexao.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(tblRegistros.getModel()
                        .getValueAt(setar, 0).toString()));
                int apagado = ps.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, 
                            "Item removido com sucesso!");
                    txtDescricao.setText("");
                    cmbMes.setSelectedItem("Janeiro");
                    txtAno.setText("");
                    txtValor.setText("");
                    cmbTipo.setSelectedItem("Entrada");
                   
                }
            } catch (HeadlessException | NumberFormatException | 
                    SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
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

        txtPesquisar = new javax.swing.JTextField();
        lblPesquisar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        lblDescricao = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        cmbMes = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        lblFiltroMes = new javax.swing.JLabel();
        cmbFiltroMes = new javax.swing.JComboBox<>();
        lblFiltroAno = new javax.swing.JLabel();
        txtFiltroAno = new javax.swing.JTextField();
        lblExportar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Gastos");
        setResizable(false);

        txtPesquisar.setToolTipText("Digite o mês ou ano desejado.");
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        lblPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/8726215_search_alt_icon.png"))); // NOI18N

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
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
        tblRegistros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRegistrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRegistros);

        lblDescricao.setText("Descrição");

        lblMes.setText("Mês");

        lblAno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAno.setText("Ano");

        lblTipo.setText("Tipo");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saida" }));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/285657_floppy_guardar_save_icon (1).png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar um novo item.");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1814090_delete_garbage_trash_icon.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir um item existente.");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/8725906_export_icon.png"))); // NOI18N
        btnExportar.setToolTipText("Exportar os itens selecionados na tabela.");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/8725775_edit_icon.png"))); // NOI18N
        btnEditar.setToolTipText("Editar um item existente.");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jLabel1.setText("Valor");

        lblFiltroMes.setText("Mês");

        cmbFiltroMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));

        lblFiltroAno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFiltroAno.setText("Ano");

        lblExportar.setText("Exportar para PDF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblDescricao)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(116, 116, 116)
                                    .addComponent(btnSalvar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnEditar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnExcluir))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblAno, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFiltroMes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbFiltroMes, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFiltroAno, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFiltroAno, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnExportar)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblExportar))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPesquisar)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMes)
                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAno)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(btnExcluir)
                            .addComponent(btnEditar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExportar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFiltroMes)
                            .addComponent(cmbFiltroMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFiltroAno)
                            .addComponent(txtFiltroAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportar)))
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        inserirRegistro();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        atualizarRegistro();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        pesquisarRegistro();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblRegistrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegistrosMouseClicked
        setarCampos();
    }//GEN-LAST:event_tblRegistrosMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        
   
        
        try {
            
            Map parametros = new HashMap();
            parametros.put("ano",txtFiltroAno.getText());
            parametros.put("mes",cmbFiltroMes.getSelectedItem().toString());
            
            System.out.println(parametros);
            
            JasperPrint print = JasperFillManager.fillReport(
                    getClass().getResourceAsStream(
                            "/reports/controleGastos.jasper"),
                    parametros,conexao);
            
            JasperViewer.viewReport(print,false);
            
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | 
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControleGastos.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new ControleGastos().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbFiltroMes;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblExportar;
    private javax.swing.JLabel lblFiltroAno;
    private javax.swing.JLabel lblFiltroMes;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtFiltroAno;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
