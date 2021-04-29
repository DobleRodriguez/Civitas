/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.TituloPropiedad;
/**
 *
 * @author javs
 */
public class PropiedadPanel extends javax.swing.JPanel {
    
    TituloPropiedad tituloPropiedad;
    
    public void setPropiedad(TituloPropiedad tituloPropiedad) {
        this.tituloPropiedad = tituloPropiedad;
        textfieldNombre.setText(tituloPropiedad.getNombre());
        textfieldNroCasas.setText(String.valueOf(tituloPropiedad.getNumCasas()));
        textfieldNroHoteles.setText(String.valueOf(tituloPropiedad.getNumHoteles()));
        textfieldHipotecada.setText(tituloPropiedad.getHipotecado() ? "Sí" : "No");
    }
    /**
     * Creates new form PropiedadPanel
     */
    public PropiedadPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textfieldNombre = new javax.swing.JTextField();
        textfieldNroCasas = new javax.swing.JTextField();
        textfieldNroHoteles = new javax.swing.JTextField();
        textfieldHipotecada = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1920, 1080));

        textfieldNombre.setEditable(false);
        textfieldNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textfieldNombre.setText("jTextField1");
        textfieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldNombreActionPerformed(evt);
            }
        });

        textfieldNroCasas.setEditable(false);
        textfieldNroCasas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textfieldNroCasas.setText("jTextField2");

        textfieldNroHoteles.setEditable(false);
        textfieldNroHoteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textfieldNroHoteles.setText("jTextField3");
        textfieldNroHoteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfieldNroHotelesActionPerformed(evt);
            }
        });

        textfieldHipotecada.setEditable(false);
        textfieldHipotecada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textfieldHipotecada.setText("jTextField4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textfieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(textfieldNroCasas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(textfieldNroHoteles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(textfieldHipotecada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(textfieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfieldNroCasas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfieldNroHoteles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfieldHipotecada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textfieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textfieldNombreActionPerformed

    private void textfieldNroHotelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfieldNroHotelesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textfieldNroHotelesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField textfieldHipotecada;
    private javax.swing.JTextField textfieldNombre;
    private javax.swing.JTextField textfieldNroCasas;
    private javax.swing.JTextField textfieldNroHoteles;
    // End of variables declaration//GEN-END:variables
}
