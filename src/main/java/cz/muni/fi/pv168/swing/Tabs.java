/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Guest;
import cz.muni.fi.pv168.GuestManager;
import cz.muni.fi.pv168.GuestManagerImpl;
import cz.muni.fi.pv168.Rent;
import cz.muni.fi.pv168.RentManager;
import cz.muni.fi.pv168.RentManagerImpl;
import cz.muni.fi.pv168.Room;
import cz.muni.fi.pv168.RoomManager;
import cz.muni.fi.pv168.RoomManagerImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JDialog;
import javax.swing.SwingWorker;

/**
 *
 * @author PavelKotala
 */
public class Tabs extends javax.swing.JFrame {
    private final RoomManager roomManager = RoomManagerImpl.getInstance();
    private final GuestManager guestManager = GuestManagerImpl.getInstance();
    private final RentManager rentManager = RentManagerImpl.getInstance();
    private final Tabs tabs = this;

    /**
     * Creates new form Tabs
     */
    public Tabs() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        roomsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomsTable = new javax.swing.JTable();
        newRoomButton = new javax.swing.JButton();
        editRoomButton = new javax.swing.JButton();
        deleteRoomButton = new javax.swing.JButton();
        guestsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        guestsTable = new javax.swing.JTable();
        newGuestButton = new javax.swing.JButton();
        editGuestButton = new javax.swing.JButton();
        deleteGuestButton = new javax.swing.JButton();
        rentsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rentsTable = new javax.swing.JTable();
        newRentButton = new javax.swing.JButton();
        editRentButton = new javax.swing.JButton();
        deleteRentButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roomsTable.setModel(new RoomTableModel());
        roomsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(roomsTable);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("texty"); // NOI18N
        newRoomButton.setText(bundle.getString("new_room")); // NOI18N
        newRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRoomButtonActionPerformed(evt);
            }
        });

        editRoomButton.setText(bundle.getString("edit_room")); // NOI18N
        editRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRoomButtonActionPerformed(evt);
            }
        });

        deleteRoomButton.setText(bundle.getString("delete_room")); // NOI18N
        deleteRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRoomButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roomsPanelLayout = new javax.swing.GroupLayout(roomsPanel);
        roomsPanel.setLayout(roomsPanelLayout);
        roomsPanelLayout.setHorizontalGroup(
            roomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roomsPanelLayout.setVerticalGroup(
            roomsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(roomsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newRoomButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editRoomButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteRoomButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("rooms"), roomsPanel); // NOI18N

        guestsTable.setModel(new GuestTableModel());
        guestsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(guestsTable);

        newGuestButton.setText(bundle.getString("new_guest")); // NOI18N
        newGuestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGuestButtonActionPerformed(evt);
            }
        });

        editGuestButton.setText(bundle.getString("edit_guest")); // NOI18N
        editGuestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGuestButtonActionPerformed(evt);
            }
        });

        deleteGuestButton.setText(bundle.getString("delete_guest")); // NOI18N
        deleteGuestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGuestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout guestsPanelLayout = new javax.swing.GroupLayout(guestsPanel);
        guestsPanel.setLayout(guestsPanelLayout);
        guestsPanelLayout.setHorizontalGroup(
            guestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guestsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(guestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteGuestButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newGuestButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editGuestButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        guestsPanelLayout.setVerticalGroup(
            guestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(guestsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newGuestButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editGuestButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteGuestButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("guests"), guestsPanel); // NOI18N

        rentsTable.setModel(new RentTableModel());
        rentsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(rentsTable);

        newRentButton.setText(bundle.getString("new_rent")); // NOI18N
        newRentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRentButtonActionPerformed(evt);
            }
        });

        editRentButton.setText(bundle.getString("edit_rent")); // NOI18N
        editRentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRentButtonActionPerformed(evt);
            }
        });

        deleteRentButton.setText(bundle.getString("delete_rent")); // NOI18N
        deleteRentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rentsPanelLayout = new javax.swing.GroupLayout(rentsPanel);
        rentsPanel.setLayout(rentsPanelLayout);
        rentsPanelLayout.setHorizontalGroup(
            rentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteRentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newRentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editRentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        rentsPanelLayout.setVerticalGroup(
            rentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(rentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newRentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editRentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteRentButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("rents"), rentsPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRoomButtonActionPerformed
        RoomDialog roomDialog = new RoomDialog(this, true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("texty");
        int selected = roomsTable.getSelectedRow();
        RoomTableModel roomModel = (RoomTableModel) roomsTable.getModel();
        Room room = roomModel.getRoomAt(selected);
        
        roomDialog.setHeaderText(bundle.getString("edit_room"));
        roomDialog.setBedsText(room.getBeds() + "");
        roomDialog.setNumberText(room.getNumber() + "");
        roomDialog.setPricePerNightText(room.getPricePerNight().toString());
        roomDialog.setNoteText(room.getNote());
        roomDialog.setId(room.getId());
        
        roomDialog.setVisible(true);
    }//GEN-LAST:event_editRoomButtonActionPerformed

    private void newRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRoomButtonActionPerformed
        RoomDialog roomDialog = new RoomDialog(this, true);
        roomDialog.setVisible(true);
    }//GEN-LAST:event_newRoomButtonActionPerformed

    private void deleteRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRoomButtonActionPerformed
        RoomTableModel roomModel = (RoomTableModel)roomsTable.getModel();
        Room room = roomModel.getRoomAt(roomsTable.getSelectedRow());
        
        final JDialog process = new ProcessingDialog(this, true);
        
        SwingWorker sw = new RoomSwingWorker(room, true) {
            @Override
            protected void done() {
                process.setVisible(false);
                try {
                    List<Room> rooms = get();
                    tabs.refreshRooms(rooms);
                } catch (ExecutionException ex) {
                    ErrorDialog error = new ErrorDialog(tabs, false);
                    error.setText("Exception thrown in doInBackground(): " + ex.getCause());
                    error.setVisible(true);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Operation interrupted (this should never happen)",ex);
                }
            }
        };
        sw.execute();
        process.setVisible(true);
    }//GEN-LAST:event_deleteRoomButtonActionPerformed

    private void newGuestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGuestButtonActionPerformed
        GuestDialog guestDialog = new GuestDialog(this, true);
        guestDialog.setVisible(true);
    }//GEN-LAST:event_newGuestButtonActionPerformed

    private void editGuestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGuestButtonActionPerformed
        GuestDialog guestDialog = new GuestDialog(this, true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("texty");
        int selected = guestsTable.getSelectedRow();
        GuestTableModel guestModel = (GuestTableModel) guestsTable.getModel();
        Guest guest = guestModel.getGuestAt(selected);
        
        guestDialog.setHeaderText(bundle.getString("edit_guest"));
        guestDialog.setDate(Date.from(guest.getBorn().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        guestDialog.setNameText(guest.getName());
        guestDialog.setEmailText(guest.getEmail());
        guestDialog.setId(guest.getId());
        
        guestDialog.setVisible(true);
    }//GEN-LAST:event_editGuestButtonActionPerformed

    private void deleteGuestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGuestButtonActionPerformed
        GuestTableModel guestModel = (GuestTableModel)guestsTable.getModel();
        Guest guest = guestModel.getGuestAt(guestsTable.getSelectedRow());
        
        final JDialog process = new ProcessingDialog(this, true);
        
        SwingWorker sw = new GuestSwingWorker(guest, true) {
            @Override
            protected void done() {
                process.setVisible(false);
                try {
                    List<Guest> guests = get();
                    tabs.refreshGuests(guests);
                } catch (ExecutionException ex) {
                    ErrorDialog error = new ErrorDialog(tabs, false);
                    error.setText("Exception thrown in doInBackground(): " + ex.getCause());
                    error.setVisible(true);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Operation interrupted (this should never happen)",ex);
                }
            }
        };
        sw.execute();
        process.setVisible(true);
    }//GEN-LAST:event_deleteGuestButtonActionPerformed

    private void newRentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRentButtonActionPerformed
        RentDialog rentDialog = new RentDialog(this, true);
        rentDialog.setVisible(true);
    }//GEN-LAST:event_newRentButtonActionPerformed

    private void editRentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRentButtonActionPerformed
        RentDialog rentDialog = new RentDialog(this, true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("texty");
        int selected = rentsTable.getSelectedRow();
        RentTableModel rentModel = (RentTableModel) rentsTable.getModel();
        Rent rent = rentModel.getRentAt(selected);
        
        rentDialog.setHeaderText("edit_rent");
        rentDialog.setId(rent.getId());
        rentDialog.setStartDate(Date.from(rent.getStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        rentDialog.setEndDate(Date.from(rent.getEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        rentDialog.setPrice(rent.getPrice().toString());
        
        rentDialog.setVisible(true);
    }//GEN-LAST:event_editRentButtonActionPerformed

    private void deleteRentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRentButtonActionPerformed
        RentTableModel rentModel = (RentTableModel)rentsTable.getModel();
        Rent rent = rentModel.getRentAt(rentsTable.getSelectedRow());
        
        final JDialog process = new ProcessingDialog(this, true);
        
        SwingWorker sw = new RentSwingWorker(rent, true) {
            @Override
            protected void done() {
                process.setVisible(false);
                try {
                    List<Rent> rents = get();
                    tabs.refreshRents(rents);
                } catch (ExecutionException ex) {
                    ErrorDialog error = new ErrorDialog(tabs, false);
                    error.setText("Exception thrown in doInBackground(): " + ex.getCause());
                    error.setVisible(true);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Operation interrupted (this should never happen)",ex);
                }
            }
        };
        sw.execute();
        process.setVisible(true);
    }//GEN-LAST:event_deleteRentButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Tabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tabs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteGuestButton;
    private javax.swing.JButton deleteRentButton;
    private javax.swing.JButton deleteRoomButton;
    private javax.swing.JButton editGuestButton;
    private javax.swing.JButton editRentButton;
    private javax.swing.JButton editRoomButton;
    private javax.swing.JPanel guestsPanel;
    private javax.swing.JTable guestsTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton newGuestButton;
    private javax.swing.JButton newRentButton;
    private javax.swing.JButton newRoomButton;
    private javax.swing.JPanel rentsPanel;
    private javax.swing.JTable rentsTable;
    private javax.swing.JPanel roomsPanel;
    private javax.swing.JTable roomsTable;
    // End of variables declaration//GEN-END:variables

    public void refreshRooms(List<Room> rooms) {
        RoomTableModel roomModel = (RoomTableModel)roomsTable.getModel();
        roomModel.setRooms(rooms);
    }
    
    public void refreshGuests(List<Guest> guests) {
        GuestTableModel guestModel = (GuestTableModel)guestsTable.getModel();
        guestModel.setGuests(guests);
    }
    
    public void refreshRents(List<Rent> rents) {
        RentTableModel rentModel = (RentTableModel)rentsTable.getModel();
        rentModel.setRents(rents);
    }
    
    public List<Room> getRooms() {
        RoomTableModel roomModel = (RoomTableModel)roomsTable.getModel();
        return roomModel.getRooms();
    }
    
    public List<Guest> getGuests() {
        GuestTableModel guestModel = (GuestTableModel)guestsTable.getModel();
        return guestModel.getGuests();
    }
}
