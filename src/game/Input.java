package game;

import rendering.Driver;

import java.awt.event.*;

public class Input implements MouseListener, MouseMotionListener, KeyListener {

    private Driver driver;

    private float mouseX = 0;
    private float mouseY = 0;

    private float oldMouseX = 0;
    private float oldMouseY = 0;

    public Input(Driver driver) {
        this.driver = driver;

        this.driver.addKeyListener(this);
        this.driver.addMouseListener(this);
        this.driver.addMouseMotionListener(this);
        this.driver.setFocusable(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                driver.wPressed = true;
                break;
            case KeyEvent.VK_A:
                driver.aPressed = true;
                break;
            case KeyEvent.VK_S:
                driver.sPressed = true;
                break;
            case KeyEvent.VK_D:
                driver.dPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                driver.spacePressed = true;
                break;
            case KeyEvent.VK_SHIFT:
                driver.shiftPressed = true;
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                driver.wPressed = false;
                break;
            case KeyEvent.VK_A:
                driver.aPressed = false;
                break;
            case KeyEvent.VK_S:
                driver.sPressed = false;
                break;
            case KeyEvent.VK_D:
                driver.dPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                driver.spacePressed = false;
                break;
            case KeyEvent.VK_SHIFT:
                driver.shiftPressed = false;
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        oldMouseX = mouseX;
        oldMouseY = mouseY;

        mouseX = e.getX();
        mouseY = e.getY();

        driver.mouseMoved(mouseX - oldMouseX, mouseY - oldMouseY);

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {

        oldMouseX = mouseX;
        oldMouseY = mouseY;

        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
