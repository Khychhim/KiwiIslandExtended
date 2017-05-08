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
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author joshl
 */
public class MainMenu extends JPanel implements MouseListener {
    private javax.swing.JLabel menuBackground;
    private javax.swing.JLayeredPane menuPane;
    private Image backGroundImage;
    private Image buttonImage;
    private Image startGameText;
    private Timer resizeTimer;
    
    private final JLabel[] buttons;
    private final JLabel[] buttonText;
    
    private static final int START_GAME = 0;
    private static final int HIGH_SCORES = 1;
    
    private static final int NUMBER_OF_BUTTONS = 2;
    
    private static final long WAIT_FOR_RESIZE = 250;
    
    private static final int INIT_WINDOW_WIDTH = 1000;
    private static final int INIT_WINDOW_HEIGHT = 700;
    
    private static final float BUTTON_WIDTH_RATIO = 0.4f;
    private static final float BUTTON_HEIGHT_RATIO = 0.075f;
    private static final float BUTTON_X_POSITION_RATIO = 0.5f;
    private static final float BUTTON_Y_POSITION_RATIO = 0.09f;
    
    public MainMenu() {
        resizeTimer = new Timer();
        buttons = new JLabel[NUMBER_OF_BUTTONS];
        buttonText = new JLabel[NUMBER_OF_BUTTONS];
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
            //Text Images
            startGameText = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/StartGame.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
        
        mainMenu.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                final ComponentEvent ce = e;
                resizeTimer.cancel();
                resizeTimer = new Timer();
                resizeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        resizeWindow(ce);
                    }
                }, WAIT_FOR_RESIZE);
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
        menuBackground = new JLabel();
        menuBackground.setPreferredSize(mainMenu.getSize());
        int width = (int)(mainMenu.getWidth()*BUTTON_WIDTH_RATIO);
        int height = (int)(mainMenu.getHeight()*BUTTON_HEIGHT_RATIO);
        int x = (int) ((mainMenu.getWidth()-width)*BUTTON_X_POSITION_RATIO);
        //Loop for each button with width, height and x position staying the same
        for(int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            //Setup buttons
            int y = (int) ((mainMenu.getHeight()-height)*BUTTON_Y_POSITION_RATIO*(i+1));
            buttons[i] = new JLabel();
            buttons[i].setPreferredSize(new Dimension(width, height));
            buttons[i].setBounds(x, y, width, height);
            //Setup button Text
            buttonText[i] = new JLabel();
            buttonText[i].setPreferredSize(new Dimension(width, height));
            buttonText[i].setBounds(x, y, width, height);
            //Set the layers for correct drawing order
            menuPane.setLayer(buttons[i], 1);
            menuPane.setLayer(buttonText[i], 2);
            //Add to menuPane
            menuPane.add(buttons[i]);
            menuPane.add(buttonText[i]);
        }

        mainMenu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainMenu.setPreferredSize(new java.awt.Dimension(INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT));

        menuPane.setLayer(menuBackground, 0);
        
        menuPane.add(menuBackground);

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
        mainMenu.setLocationRelativeTo(null);
    }
    
    private void resizeWindow(ComponentEvent e) {
        System.out.println("resized");
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        int buttonWidth = (int) (width*BUTTON_WIDTH_RATIO);
        int buttonHeight =  (int) (height*BUTTON_HEIGHT_RATIO);
        menuBackground.setIcon(new ImageIcon(scaleImage(backGroundImage, width, height)));
        menuBackground.setPreferredSize(e.getComponent().getSize());
        menuBackground.setBounds(0, 0, width, height);
        //Set scaled icons to the buttons and text
        ImageIcon buttonIcon = new ImageIcon(
                scaleImage(buttonImage, (int)(width*BUTTON_WIDTH_RATIO), (int)(height*BUTTON_HEIGHT_RATIO)));
        buttonText[START_GAME].setIcon(new ImageIcon(
                scaleImage(startGameText, (int)(width*BUTTON_WIDTH_RATIO), (int)(height*BUTTON_HEIGHT_RATIO))));
        buttonText[HIGH_SCORES].setIcon(new ImageIcon(
                scaleImage(startGameText, (int)(width*BUTTON_WIDTH_RATIO), (int)(height*BUTTON_HEIGHT_RATIO))));
        //Resize the buttons
        int x = (int) ((width-buttonWidth)*BUTTON_X_POSITION_RATIO);
        for(int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            int y = (int) ((height-buttonHeight)*BUTTON_Y_POSITION_RATIO*(i+1));
            buttons[i].setIcon(buttonIcon);
            buttons[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            buttons[i].setBounds(x, y, buttonWidth, buttonHeight);
            buttonText[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            buttonText[i].setBounds(x, y, buttonWidth, buttonHeight);
        }
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
        if(buttons[START_GAME].getBounds().contains(clickLoc)) {
            System.out.println("Start Game...");
        } else if(buttons[HIGH_SCORES].getBounds().contains(clickLoc)) {
            System.out.println("High Scores...");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
