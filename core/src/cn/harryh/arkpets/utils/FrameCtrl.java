/** Copyright (c) 2022-2023, Harry Huang
 * At GPL-3.0 License
 */
package cn.harryh.arkpets.utils;


public class FrameCtrl {
    public int F_MAX;
    public int F_CUR;
    public float F_TIME;
    public boolean LOOPED;

    /** Frame Data Controller instance.
     * @param $duration The time(seconds) that the animation plays once.
     * @param $fps Frame per second.
     */
    public FrameCtrl(float $duration, int $fps) {
        LOOPED = false;
        F_TIME = (float) 1 / $fps;
        F_CUR = 0;
        F_MAX = (int) Math.floor($duration / F_TIME) + 2;
    }

    /** Step to the next frame.
     */
    public void next() {
        if (F_CUR >= F_MAX) {
            LOOPED = true;
            F_CUR = 1;
        } else {
            F_CUR++;
        }
        // System.out.println(F_CUR+"/"+F_MAX);
    }
}