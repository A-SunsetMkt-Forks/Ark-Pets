/** Copyright (c) 2022-2024, Harry Huang, Litwak913
 * At GPL-3.0 License
 */
package cn.harryh.arkpets.hwnd;


public class NullHWndCtrl extends HWndCtrl<Object> {
    @Override
    public WindowRect getWindowRect(Object hWnd) {
        return new WindowRect(0, 0, 0, 0);
    }

    @Override
    public String getWindowText(Object hWnd) {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isForeground() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean close(int timeout) {
        return true;
    }

    @Override
    public void setForeground() {
    }

    @Override
    public void setWindowAlpha(float alpha) {
    }

    @Override
    public void setWindowPosition(HWndCtrl<Object> insertAfter, int x, int y, int w, int h) {
    }

    @Override
    public void setWindowTransparent(boolean transparent) {
    }

    @Override
    public void setToolWindow(boolean enable) {
    }

    @Override
    public void setLayered(boolean enable) {
    }

    @Override
    public void setTopmost(boolean enable) {
    }

    @Override
    public void sendMouseEvent(MouseEvent msg, int x, int y) {
    }
}
