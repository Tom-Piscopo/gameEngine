package gameEngine.input;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static long window;

    // call once after window.create()
    public static void attach(long win) { window = win; }

    // poll-based key state (for now at least)
    public static boolean down(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }
}
