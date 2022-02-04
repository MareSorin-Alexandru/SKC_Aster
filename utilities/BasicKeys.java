package utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BasicKeys implements KeyListener {
    public Action action;
    public BasicKeys() {
        action = new Action();
    }

    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_W:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_D:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_SHIFT:
                action.shoot_secondary=true;
                break;
            case KeyEvent.VK_ESCAPE:
                action.restart = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key)
        {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_W:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn =0;
                break;
            case KeyEvent.VK_A:
                action.turn =0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_D:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
            case KeyEvent.VK_SHIFT:
                action.shoot_secondary=false;
                break;
            case KeyEvent.VK_ESCAPE:
                action.restart = false;
                break;
        }
    }
}
