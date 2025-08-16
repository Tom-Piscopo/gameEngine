package gameEngine.gfx2d;

import static org.lwjgl.opengl.GL11.*;

public class Renderer2D {
    private final int screenW, screenH;

    public Renderer2D(int screenW, int screenH) {
        this.screenW = screenW; this.screenH = screenH;
    }

    // the only shape I have for now
    public void fillRect(float x, float y, float w, float h, float r, float g, float b) {
        float x0 = toNDC_X(x),       y0 = toNDC_Y(y);
        float x1 = toNDC_X(x + w),   y1 = toNDC_Y(y + h);
        glColor3f(r, g, b);
        glBegin(GL_QUADS);
        glVertex2f(x0, y0); glVertex2f(x1, y0);
        glVertex2f(x1, y1); glVertex2f(x0, y1);
        glEnd();
    }

    private float toNDC_X(float px) { return (px / screenW) * 2f - 1f; }
    private float toNDC_Y(float py) { return 1f - (py / screenH) * 2f; }
}
