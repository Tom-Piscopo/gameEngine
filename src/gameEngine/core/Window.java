package gameEngine.core;

import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.opengl.GL;

public class Window {
    private long handle;
    private int width, height;
    private final String title;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!glfwInit()) throw new IllegalStateException("GLFW init failed");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) throw new RuntimeException("Window creation failed");

        GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(handle, (vid.width() - width)/2, (vid.height() - height)/2);

        glfwMakeContextCurrent(handle);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glfwShowWindow(handle);
    }

    public boolean shouldClose() { return glfwWindowShouldClose(handle); }
    public void poll() { glfwPollEvents(); }
    public void swap() { glfwSwapBuffers(handle); }
    public void destroy() { glfwDestroyWindow(handle); glfwTerminate(); }

    public long handle() { return handle; }
    public int width() { return width; }
    public int height() { return height; }
}
