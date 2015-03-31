package com.dblappdev.hitch.app;

/**
 * Created by s128232 on 31-3-2015.
 */
public class ChatThread extends Thread {
    public static boolean RUNNING;

    public ChatThread(Runnable runnable) {
        super(runnable);
        RUNNING = true;
    }

    public void end() {
        RUNNING = false;
    }
}
