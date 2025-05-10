/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package treasurehauntadventure;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.*;

/**
 *
 * @author ervas
 */
public class Level1 extends javax.swing.JFrame {

    private String username;
    private ArrayList<JLabel> labels = new ArrayList<>();
    private Board<SpotMaking> mapList;
    private Spot<SpotMaking> playerNode;
    private Spot<SpotMaking> jumper;
    private Spot<SpotMaking> beforeJump;
    private int score = 0;
    int level = 1;

    /**
     * Creates new form Level1
     *
     *
     */
    public Level1(String username, int level) {
        this.setLocationRelativeTo(null);
        initComponents();
        this.username = username;
        this.level = level;
        setTitle("Treasure Hunt - Welcome " + username);

        labels = new ArrayList<>(Arrays.asList(
                jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10,
                jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel16, jLabel17, jLabel18, jLabel19,
                jLabel20, jLabel21, jLabel22, jLabel23, jLabel24, jLabel25, jLabel26, jLabel27, jLabel28, jLabel29,
                jLabel30, jLabel31, jLabel32, jLabel33
        ));

        mapList = generateMap();
        playerNode = mapList.getHead();
        // İlk olarak tüm label'lara sırayla numara + varsa ikon yazalım
        Spot<SpotMaking> temp = mapList.getHead();
        while (temp != null) {
            SpotMaking s = temp.data;
            int index = s.index;
            String text = "";

            if (index == 0) {
                text = "START"; // Oyuncu başlangıçta
            } else if (index == 31) {
                text = "FINISH"; // Bitiş sade gösterilir
            } else {
                switch (s.type) {
                    case TREASURE:
                        text = " *";
                        break;
                    case TRAP:
                        text = " #️";
                        break;
                    case EMPTY:
                        text = index + "";
                        break;
                    case FORWARD:
                        text = "->";
                        break;
                    case BACKWARD:
                        text = "<-";
                        break;
                }
            }

            labels.get(index).setText(text);
            temp = temp.next;
        }

    }

    private void movePlayer(int steps) {
        SpotMaking previous = playerNode.data;
        System.out.println("Previous location: " + previous.index + " - Type: " + previous.type);

        if (!previous.isStartOrEnd) {
            switch (previous.type) {
                case TREASURE:
                    labels.get(previous.index).setText("*");
                    break;
                case TRAP:
                    labels.get(previous.index).setText(" #️");
                    break;
                case EMPTY:
                    labels.get(previous.index).setText(previous.index + "");
                    break;
                case FORWARD:
                    labels.get(previous.index).setText(" ->");
                    break;
                case BACKWARD:
                    labels.get(previous.index).setText("<-");
                    break;
            }
        } else {
            if(previous.index == 0){
                labels.get(previous.index).setText("START");
            }else if(previous.index == 31){
                labels.get(previous.index).setText("FINISH");
            }
        }

        // adım atıyorum
        for (int i = 0; i < steps; i++) {
            if (playerNode.next != null) {
                playerNode = playerNode.next;
            } else {
                break; // sona geldi
            }
        }

        System.out.println("Number of steps: " + steps);
        System.out.println("Location after steps: " + playerNode.data.index);

        SpotMaking current = playerNode.data;
        System.out.println("current.jump: " + current.jump);

// Zincirleme zıplama
        while (level == 2 && current.jump > 0
                && (current.type == SpotMaking.Type.FORWARD || current.type == SpotMaking.Type.BACKWARD)) {

            Spot jumper = playerNode;

            if (current.type == SpotMaking.Type.FORWARD) {
                System.out.println("index: " + current.index);
                for (int i = 0; i < current.jump; i++) {
                    if (jumper.next != null) {
                        jumper = jumper.next;
                    } else {
                        break;
                    }
                }
            } else if (current.type == SpotMaking.Type.BACKWARD) {
                for (int i = 0; i < current.jump; i++) {
                    if (jumper.prev != null) {
                        jumper = jumper.prev;
                    } else {
                        break;
                    }
                }
                System.out.println("Jump direction: " + current.type + ", Jumping distance: " + current.jump);
            }
            
            SpotMaking beforeJump = playerNode.data;
            if(!beforeJump.isStartOrEnd){
                beforeJump.type = SpotMaking.Type.EMPTY;
                labels.get(beforeJump.index).setText(beforeJump.index +"");
            }

            // Zıplama işlemi
            playerNode = jumper;
            current = playerNode.data; // Yeni yerdeki içeriği kontrol et
            System.out.println("Location after jumping: " + current.index);

//            // Zıplanan yer tekrar tetiklenmesin diye EMPTY yapıyoruz
//            if (level == 2 && (playerNode != null) && (playerNode.data.type == SpotMaking.Type.FORWARD || playerNode.data.type == SpotMaking.Type.BACKWARD)) {
//                playerNode.data.type = SpotMaking.Type.EMPTY;
//            }

        }

        // puan
        if (!current.isStartOrEnd) {
            if (current.type == SpotMaking.Type.TREASURE) {
                score += 10;
            } else if (current.type == SpotMaking.Type.TRAP) {
                score -= 5;
            } 
        }

        // yeni konumda 
        labels.get(current.index).setText("🧍");

        // skor
        scoreLabel.setText("Score: " + score);
        System.out.println("Score: " + score);

        if (playerNode.next == null) {
            dice.setEnabled(false);
            saveScoreToFile(username, level, score);

            if (level == 1) {
                JOptionPane.showMessageDialog(this, "You reached the finish! Total Score: " + score);

                int choice = JOptionPane.showConfirmDialog(this, "Do you want to continue?", "Game Over", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.NO_OPTION) {
                    this.dispose();
                    Menu MenuFrame = new Menu();
                    MenuFrame.setVisible(true);
                    MenuFrame.pack();
                    MenuFrame.setLocationRelativeTo(null);
                } else {
                    this.dispose();
                    Level1 Level1Frame = new Level1(username, 2);
                    Level1Frame.setVisible(true);
                    Level1Frame.pack();
                    Level1Frame.setLocationRelativeTo(null);
                }
            } else if (level == 2) {
                JOptionPane.showMessageDialog(this, "Game Over! Total Score: " + score);
                this.dispose();
                Menu MenuFrame = new Menu();
                MenuFrame.setVisible(true);
                MenuFrame.pack();
                MenuFrame.setLocationRelativeTo(null);
            }
        }

    }

    private void saveScoreToFile(String username, int level, int score) {
        try (FileWriter writer = new FileWriter("score.txt", true)) { // true: üstüne yazma değil, ekleme
            writer.write(username + "," + level + "," + score + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Skor dosyasına yazılamadı: " + e.getMessage());
        }
    }

    private Board<SpotMaking> generateMap() {
        Board<SpotMaking> list = new Board<>();
        ArrayList<SpotMaking.Type> types = new ArrayList<>();

        // 1–30 arası için 10 treasure, 8 trap, 12 empty toplam 30 spot
        if (level == 1) {
            for (int i = 0; i < 10; i++) {
                types.add(SpotMaking.Type.TREASURE);
            }
            for (int i = 0; i < 8; i++) {
                types.add(SpotMaking.Type.TRAP);
            }
            for (int i = 0; i < 12; i++) {
                types.add(SpotMaking.Type.EMPTY);
            }
        } else if (level == 2) {
            for (int i = 0; i < 6; i++) {
                types.add(SpotMaking.Type.TREASURE);
            }
            for (int i = 0; i < 6; i++) {
                types.add(SpotMaking.Type.TRAP);
            }
            for (int i = 0; i < 8; i++) {
                types.add(SpotMaking.Type.EMPTY);
            }
            for (int i = 0; i < 5; i++) {
                types.add(SpotMaking.Type.BACKWARD);
            }
            for (int i = 0; i < 5; i++) {
                types.add(SpotMaking.Type.FORWARD);
            }
        }

        // Karıştır
        Random rand = new Random();
        for (int i = types.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); // 0 ile i arası rastgele
            SpotMaking.Type temp = types.get(i);
            types.set(i, types.get(j));
            types.set(j, temp);

        }
        // BACKWARD'ları ilk 3 kutudan çıkar (sadece level 2 için)
        if (level == 2) {
            for (int i = 0; i < 3; i++) {
                if (types.get(i) == SpotMaking.Type.BACKWARD) {
                    for (int j = 3; j < types.size(); j++) {
                        if (types.get(j) != SpotMaking.Type.BACKWARD) {
                            // Swap
                            SpotMaking.Type temp = types.get(i);
                            types.set(i, types.get(j));
                            types.set(j, temp);
                            break;
                        }
                    }
                }
            }
        }

        // Start (0. kutu)
        list.add(new SpotMaking(SpotMaking.Type.EMPTY, 0, true));

        // 1–30 arası kutular
        for (int i = 1; i <= 30; i++) {
            SpotMaking.Type t = types.get(i - 1);
//            if (level == 2 && i <= 3 && t == SpotMaking.Type.BACKWARD) {
//                do {
//                    int randIndex = rand.nextInt(types.size());
//                    SpotMaking.Type newType = types.get(randIndex);
//                    if (newType != SpotMaking.Type.BACKWARD) {
//                        t = newType;
//                        break;
//                    }
//                } while (true);
//
//            }
//
//            t = types.get(i - 1);
            SpotMaking s = new SpotMaking(t, i, false);

            if (level == 2 && (t == SpotMaking.Type.FORWARD || t == SpotMaking.Type.BACKWARD)) {
                s.jump = rand.nextInt(3) + 1; // 1-3 aralığında
                System.out.println("s.jump: " + s.jump);

            }
            list.add(s);
        }
        // End (31. kutu)
        list.add(new SpotMaking(SpotMaking.Type.EMPTY, 31, true));

        return list;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        dice = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        zar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 40, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 40, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(82, 32));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 40, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, 40, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel6.setText("jLabel6");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 40, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 420, 40, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel8.setText("jLabel8");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 40, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setText("jLabel9");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 40, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel10.setText("jLabel10");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 40, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel11.setText("jLabel11");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 380, 40, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel12.setText("jLabel12");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 330, 40, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel13.setText("jLabel13");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 260, 40, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel14.setText("jLabel14");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 270, 40, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel15.setText("jLabel15");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 40, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel16.setText("jLabel16");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 40, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel17.setText("jLabel17");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 260, 40, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel18.setText("jLabel18");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 40, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel19.setText("jLabel19");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 40, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel20.setText("jLabel20");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 40, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel21.setText("jLabel21");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 40, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel22.setText("jLabel22");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 40, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel23.setText("jLabel23");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 40, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel24.setText("jLabel24");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 40, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel25.setText("jLabel25");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 40, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel26.setText("jLabel26");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 40, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel27.setText("jLabel27");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 40, 30));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel28.setText("jLabel28");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 40, 30));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel29.setText("jLabel29");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 40, 30));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel30.setText("jLabel30");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 40, 50));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel31.setText("jLabel31");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 40, 40));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel32.setText("jLabel32");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 40, 50));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel33.setText("jLabel33");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, -1, -1));

        dice.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        dice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/treasurehauntadventure/Ekran görüntüsü 2025-05-04 145746.png"))); // NOI18N
        dice.setText("DICE");
        dice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                diceMouseClicked(evt);
            }
        });
        jPanel1.add(dice, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 110, 80));

        scoreLabel.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        scoreLabel.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel1.add(scoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, -1));

        zar.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        zar.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel1.add(zar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 460, 90, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/treasurehauntadventure/Ekran görüntüsü 2025-05-01 022706.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void diceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diceMouseClicked
        // TODO add your handling code here:

        int roll = new Random().nextInt(6) + 1;
        zar.setText("Zar: " + roll);
        System.out.println("zar: " + roll);
        movePlayer(roll);
    }//GEN-LAST:event_diceMouseClicked

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
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel zar;
    // End of variables declaration//GEN-END:variables
}
