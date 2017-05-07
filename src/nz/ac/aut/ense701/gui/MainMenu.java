/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    private Image backGroundImage;
    private Image buttonImage;
    
    public MainMenu() {
        init();
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
    
    private void init() {
        JFrame mainMenu = new JFrame();
        try {
            backGroundImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/MenuBackground.png"));
        } catch (IOException e) {
            System.err.println("Unable to load backGround image. " + e.getMessage());
        }
        try {
            buttonImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/Button.png"));
        } catch (IOException e) {
            System.err.println("Unable to load button image. " + e.getMessage());
        }
        mainMenu.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                menuBackground.setIcon(new ImageIcon(scaleImage(backGroundImage, width, height)));
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        menuPane = new javax.swing.JLayeredPane();
        menuBackground = new javax.swing.JLabel();

        mainMenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainMenu.setPreferredSize(new java.awt.Dimension(867, 677));
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
    
    private Image scaleImage(Image image, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        
        return resized;
    }
}
