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
public abstract class SubMenu extends JPanel implements MouseListener {
    private javax.swing.JLabel menuBackground;
    private JLabel backButton;
    protected javax.swing.JLayeredPane menuPane;
    private Image backGroundImage;
    private Image backButtonImage;
    private Timer resizeTimer;
    protected JFrame menu;
    
    private static final long WAIT_FOR_RESIZE = 250;
    
    private static final int INIT_WINDOW_WIDTH = 1000;
    private static final int INIT_WINDOW_HEIGHT = 700;
    private static final float BACK_BUTTON_WIDTH_RATIO    = 0.20f;
    private static final float BACK_BUTTON_HEIGHT_RATIO   = 0.05f;
    private static final float BACK_BUTTON_X_OFFSET       = 1.20f;
    private static final float BACK_BUTTON_Y_OFFSET       = 2.75f;
    
    public SubMenu(String frameName) {
        resizeTimer = new Timer();
        init(frameName);
    }
    
    public JFrame getMenu() {
        return menu;
    }

    public void setMenu(JFrame menu) {
        this.menu = menu;
    }
    
    private void init(String frameName) {
        menu = new JFrame(frameName);
        try {
            backGroundImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/SubMenuBackground.png"));
            backButtonImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/BackButton.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
        
        menu.addComponentListener(new ComponentListener() {
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
        menuBackground.setPreferredSize(menu.getSize());
        backButton = new JLabel();

        menu.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        menu.setPreferredSize(new java.awt.Dimension(INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT));

        menuPane.setLayer(menuBackground, 0);
        menuPane.setLayer(backButton, 1);
        
        menuPane.add(menuBackground);
        menuPane.add(backButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(menu.getContentPane());
        menu.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPane)
        );

        menu.pack();
        menu.setLocationRelativeTo(null);
    }
    
    protected void resizeWindow(ComponentEvent e) {
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        int backButtonWidth = (int) (width*BACK_BUTTON_WIDTH_RATIO);
        int backButtonHeight =  (int) (height*BACK_BUTTON_HEIGHT_RATIO);
        menuBackground.setIcon(new ImageIcon(scaleImage(backGroundImage, width, height)));
        menuBackground.setPreferredSize(e.getComponent().getSize());
        menuBackground.setBounds(0, 0, width, height);
        //Set scaled image for buttons
        ImageIcon buttonIcon = new ImageIcon(
                scaleImage(backButtonImage, backButtonWidth, backButtonHeight));
        backButton.setIcon(buttonIcon);
        backButton.setPreferredSize(new Dimension(backButtonWidth, backButtonHeight));
        backButton.setBounds((int)(width-(backButtonWidth*BACK_BUTTON_X_OFFSET)), 
                             (int)(height-(backButtonHeight*BACK_BUTTON_Y_OFFSET)), 
                             backButtonWidth, backButtonHeight);
    }
    
    protected Image scaleImage(Image image, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        
        return resized;
    }
    
    protected JLabel getBackButton() {
        return backButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickLoc = e.getPoint();
        if(backButton.getBounds().contains(clickLoc)) {
            menu.setVisible(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
