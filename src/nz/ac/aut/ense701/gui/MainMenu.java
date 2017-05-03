/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author joshl
 */
public class MainMenu extends JPanel {
    private javax.swing.JButton startGame;
    private javax.swing.JLabel menuBackground;
    private javax.swing.JLayeredPane menuPane;
    
    public MainMenu() {
        init();
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
    
    private void init() {
        JFrame mainMenu = new JFrame();
        menuPane = new javax.swing.JLayeredPane();
        menuBackground = new javax.swing.JLabel();

        mainMenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainMenu.setPreferredSize(new java.awt.Dimension(833, 638));
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/MenuBackground.png"));
        menuBackground.setIcon(icon);

        menuPane.setLayer(menuBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout menuLayeredPaneLayout = new javax.swing.GroupLayout(menuPane);
        menuPane.setLayout(menuLayeredPaneLayout);
        menuLayeredPaneLayout.setHorizontalGroup(
            menuLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayeredPaneLayout.createSequentialGroup()
                .addComponent(menuBackground)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menuLayeredPaneLayout.setVerticalGroup(
            menuLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayeredPaneLayout.createSequentialGroup()
                .addComponent(menuBackground)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainMenu.getContentPane());
        mainMenu.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPane)
        );

        mainMenu.pack();
        mainMenu.setVisible(true);
    }
}
