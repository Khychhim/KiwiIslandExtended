/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class MainMenu extends JPanel implements MouseListener {
    private javax.swing.JLabel startGame;
    private javax.swing.JLabel startGameTextLabel;
    private javax.swing.JLabel menuBackground;
    private javax.swing.JLayeredPane menuPane;
    private Image backGroundImage;
    private Image buttonImage;
    private Image startGameText;
    
    private static final int INIT_WINDOW_WIDTH = 1000;
    private static final int INIT_WINDOW_HEIGHT = 700;
    
    private static final float BUTTON_WIDTH_RATIO = 0.4f;
    private static final float BUTTON_HEIGHT_RATIO = 0.075f;
    private static final float BUTTON_X_POSITION_RATIO = 0.5f;
    private static final float BUTTON_Y_POSITION_RATIO = 0.09f;
    
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
        JFrame mainMenu = new JFrame("Kiwi Island");
        try {
            backGroundImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/MenuBackground.png"));
            buttonImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/Button.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
        try {
            startGameText = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/StartGame.png"));
        } catch (IOException e) {
            System.err.println("Unable to load start game text image. " + e.getMessage());
        }
        mainMenu.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                int buttonWidth = (int) (width*BUTTON_WIDTH_RATIO);
                int buttonHeight =  (int) (height*BUTTON_HEIGHT_RATIO);
                menuBackground.setIcon(new ImageIcon(scaleImage(backGroundImage, width, height)));
                menuBackground.setPreferredSize(e.getComponent().getSize());
                menuBackground.setBounds(0, 0, width, height);
                startGame.setIcon(new ImageIcon(scaleImage(buttonImage, (int)(width*BUTTON_WIDTH_RATIO), (int)(height*BUTTON_HEIGHT_RATIO))));
                int x = (int) ((width-buttonWidth)*BUTTON_X_POSITION_RATIO);
                int y = (int) ((height-buttonHeight)*BUTTON_Y_POSITION_RATIO);
                startGame.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                startGame.setBounds(x, y, buttonWidth, buttonHeight);
                startGameTextLabel.setIcon(new ImageIcon(scaleImage(startGameText, (int)(width*BUTTON_WIDTH_RATIO), (int)(height*BUTTON_HEIGHT_RATIO))));
                startGameTextLabel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                startGameTextLabel.setBounds(x, y, buttonWidth, buttonHeight);
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        menuPane = new javax.swing.JLayeredPane();
        menuPane.addMouseListener(this);
        menuBackground = new javax.swing.JLabel();
        menuBackground.setPreferredSize(mainMenu.getSize());
        startGame = new javax.swing.JLabel();
        int width = (int)(mainMenu.getWidth()*BUTTON_WIDTH_RATIO);
        int height = (int)(mainMenu.getHeight()*BUTTON_HEIGHT_RATIO);
        int x = (int) ((mainMenu.getWidth()-width)*BUTTON_X_POSITION_RATIO);
        int y = (int) ((mainMenu.getHeight()-height)*BUTTON_Y_POSITION_RATIO);
        startGame.setPreferredSize(new Dimension(width, height));
        startGame.setBounds(x, y, width, height);
        startGameTextLabel = new javax.swing.JLabel();
        startGameTextLabel.setPreferredSize(new Dimension(width, height));
        startGameTextLabel.setBounds(x, y, width, height);

        mainMenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainMenu.setPreferredSize(new java.awt.Dimension(INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT));

        menuPane.setLayer(menuBackground, 0);
        menuPane.setLayer(startGame, 1);
        menuPane.setLayer(startGameTextLabel, 2);
        
        menuPane.add(menuBackground);
        menuPane.add(startGame);
        menuPane.add(startGameTextLabel);

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

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickLoc = e.getPoint();
        if(startGame.getBounds().contains(clickLoc)) {
            System.out.println("Start Game...");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
