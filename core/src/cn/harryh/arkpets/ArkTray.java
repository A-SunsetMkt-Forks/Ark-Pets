/** Copyright (c) 2022-2024, Harry Huang
 * At GPL-3.0 License
 */
package cn.harryh.arkpets;

import cn.harryh.arkpets.animations.AnimData;
import cn.harryh.arkpets.socket.SocketClient;
import cn.harryh.arkpets.socket.SocketData;
import cn.harryh.arkpets.tray.Tray;
import cn.harryh.arkpets.utils.Logger;
import com.badlogic.gdx.Gdx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static cn.harryh.arkpets.Const.*;


public class ArkTray extends Tray {
    private final ArkPets arkPets;
    private final SocketClient socketClient;
    public AnimData keepAnim;
    public static Font font;
    private final JDialog popWindow;
    private final JPopupMenu popMenu;
    private final boolean[] button = {false, false};

    static {
        try {
            InputStream inputStream = Objects.requireNonNull(ArkTray.class.getResourceAsStream(fontFileRegular));
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            if (font != null) {
                UIManager.put("Label.font", font.deriveFont(9f).deriveFont(Font.ITALIC));
                UIManager.put("MenuItem.font", font.deriveFont(11f));
            }
        } catch (FontFormatException | IOException e) {
            Logger.error("Tray", "Failed to load tray menu font, details see below.", e);
            font = null;
        }
    }

    /** Initializes the ArkPets tray icon instance. <br/>
     * Must be used after Gdx.app was initialized.
     * @param boundArkPets The ArkPets instance that bound to the tray icon.
     */
    public ArkTray(ArkPets boundArkPets, SocketClient socket, UUID uuid) {
        super(uuid);
        arkPets = boundArkPets;
        socketClient = socket;
        popWindow = new JDialog();
        popWindow.setUndecorated(true);
        popWindow.setSize(1, 1);

        // PopupMenu:
        popMenu = new JPopupMenu() {
            @Override
            public void firePopupMenuWillBecomeInvisible() {
                popWindow.setVisible(false); // Hide the container when the menu is invisible.
            }
        };
        name = (arkPets.config.character_label == null || arkPets.config.character_label.isEmpty()) ? "Unknown" : arkPets.config.character_label;
        socketClient.connect(socketData -> {
            if (socketData == null) {
                Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(iconFilePng));
                TrayIcon icon = getTrayIcon(image);

                // Add the icon to the system tray.
                try {
                    SystemTray.getSystemTray().add(icon);
                    Logger.info("Tray", "Tray icon applied");
                } catch (AWTException e) {
                    Logger.error("Tray", "Unable to apply tray icon, details see below", e);
                }
                socketClient.disconnect();
                socketClient.reconnect(() -> {
                    SystemTray.getSystemTray().remove(icon);
                    socketClient.sendRequest(new SocketData(this.uuid, SocketData.OperateType.LOGIN, name, arkPets.canChangeStage()));
                    if (button[0]) {
                        socketClient.sendRequest(new SocketData(this.uuid, SocketData.OperateType.KEEP_ACTION));
                    }
                    if (button[1]) {
                        socketClient.sendRequest(new SocketData(this.uuid, SocketData.OperateType.TRANSPARENT_MODE));
                    }
                });
                return;
            }
            if (socketData.uuid.compareTo(uuid) != 0)
                return;
            switch (socketData.operateType) {
                case LOGOUT -> optExitHandler();
                case KEEP_ACTION -> optKeepAnimEnHandler();
                case NO_KEEP_ACTION -> optKeepAnimDisHandler();
                case TRANSPARENT_MODE -> optTransparentEnHandler();
                case NO_TRANSPARENT_MODE -> optTransparentDisHandler();
                case CHANGE_STAGE -> optChangeStageHandler();
            }
        });
        socketClient.sendRequest(new SocketData(this.uuid, SocketData.OperateType.LOGIN, name, arkPets.canChangeStage()));
        addComponent();
    }

    private TrayIcon getTrayIcon(Image image) {
        TrayIcon icon = new TrayIcon(image, name);
        icon.setImageAutoSize(true);
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 3 && e.isPopupTrigger()) {
                    // After right-click on the tray icon.
                    int x = e.getX();
                    int y = e.getY();
                    showDialog(x + 5, y);
                }
            }
        });
        return icon;
    }

    @Override
    protected void addComponent() {
        JLabel innerLabel = new JLabel(" " + name + " ");
        innerLabel.setAlignmentX(0.5f);
        popMenu.add(innerLabel);

        optKeepAnimEn.addActionListener(e -> socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.KEEP_ACTION)));
        optKeepAnimDis.addActionListener(e -> socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.NO_KEEP_ACTION)));
        optTransparentEn.addActionListener(e -> socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.TRANSPARENT_MODE)));
        optTransparentDis.addActionListener(e -> socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.NO_TRANSPARENT_MODE)));
        optChangeStage.addActionListener(e -> {
            if (keepAnim != null)
                socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.CHANGE_STAGE));
        });
        optExit.addActionListener(e -> socketClient.sendRequest(new SocketData(uuid, SocketData.OperateType.LOGOUT)));

        popMenu.add(optKeepAnimEn);
        popMenu.add(optTransparentEn);
        if (arkPets.canChangeStage()) popMenu.add(optChangeStage);
        popMenu.add(optExit);
        popMenu.setSize(100, 24 * popMenu.getSubElements().length);
    }

    @Override
    protected void optExitHandler() {
        Logger.info("Tray", "Request to exit");
        arkPets.windowAlpha.reset(0f);
        removeTray();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        }, (int) (linearEasingDuration * 1000));
    }

    @Override
    protected void optChangeStageHandler() {
        Logger.info("Tray", "Request to change stage");
        arkPets.changeStage();
        if (keepAnim != null) {
            keepAnim = null;
            popMenu.remove(optKeepAnimDis);
            popMenu.add(optKeepAnimEn, 1);
        }
    }

    @Override
    protected void optTransparentDisHandler() {
        Logger.info("Tray", "Transparent disabled");
        button[1] = false;
        arkPets.windowAlpha.reset(1f);
        arkPets.hWndMine.setWindowTransparent(false);
        popMenu.remove(optTransparentDis);
        popMenu.add(optTransparentEn, 2);
    }

    @Override
    protected void optTransparentEnHandler() {
        Logger.info("Tray", "Transparent enabled");
        button[1] = true;
        arkPets.windowAlpha.reset(0.75f);
        arkPets.hWndMine.setWindowTransparent(true);
        popMenu.remove(optTransparentEn);
        popMenu.add(optTransparentDis, 2);
    }

    @Override
    protected void optKeepAnimDisHandler() {
        Logger.info("Tray", "Keep-Anim disabled");
        button[0] = false;
        keepAnim = null;
        popMenu.remove(optKeepAnimDis);
        popMenu.add(optKeepAnimEn, 1);
    }

    @Override
    protected void optKeepAnimEnHandler() {
        Logger.info("Tray", "Keep-Anim enabled");
        button[0] = true;
        keepAnim = arkPets.cha.getPlaying();
        popMenu.remove(optKeepAnimEn);
        popMenu.add(optKeepAnimDis, 1);
    }

    @Override
    public void removeTray() {
        popMenu.removeAll();
        popWindow.dispose();
        socketClient.disconnect();
    }

    /** Hides the menu.
     */
    public void hideDialog() {
        if (popMenu.isVisible()) {
            popMenu.setVisible(false);
            Logger.debug("Tray", "Hidden");
        }
    }

    /** Shows the menu at the given coordinate.
     */
    public void showDialog(int x, int y) {
        /* Use `System.setProperty("sun.java2d.uiScale", "1")` can also avoid system scaling.
        Here we will adapt the coordinate for system scaling artificially. See below. */
        AffineTransform at = popWindow.getGraphicsConfiguration().getDefaultTransform();
        int scaledX = (int) (x / at.getScaleX());
        int scaledY = (int) (y / at.getScaleY());

        // Show the JDialog together with the JPopupMenu.
        popWindow.setVisible(true);
        popWindow.setLocation(scaledX, scaledY - popMenu.getHeight());
        popMenu.show(popWindow, 0, 0);
        Logger.debug("Tray", "Shown @ " + x + ", " + y);
    }

    /** Toggles the menu at the given coordinate.
     */
    public void toggleDialog(int x, int y) {
        if (popMenu.isVisible()) {
            hideDialog();
        } else {
            showDialog(x, y);
        }
    }
}
