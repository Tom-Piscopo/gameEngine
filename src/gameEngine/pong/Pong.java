package gameEngine.pong;

import gameEngine.core.Game;
import gameEngine.gfx2d.Renderer2D;
import gameEngine.input.Input;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class Pong implements Game {
    private final int W, H;
    private Renderer2D g;

    // paddles & ball
    private float padW = 12, padH = 96, padSpeed = 300;
    private float p1x, p1y, p2x, p2y;
    private float ballX, ballY, ballS = 12, vx = 220, vy = 180;

    public Pong(int width, int height) { this.W = width; this.H = height; }

    @Override public void init() {
        g = new Renderer2D(W, H);
        p1x = 30; p1y = (H - padH) * 0.5f;
        p2x = W - 30 - padW; p2y = (H - padH) * 0.5f;
        ballX = (W - ballS) * 0.5f; ballY = (H - ballS) * 0.5f;
        glClearColor(0.08f, 0.09f, 0.12f, 1f);
    }

    @Override public void update(float dt) {
        // input
        if (Input.down(GLFW_KEY_W)) p1y -= padSpeed * dt;
        if (Input.down(GLFW_KEY_S)) p1y += padSpeed * dt;
        if (Input.down(GLFW_KEY_UP))   p2y -= padSpeed * dt;
        if (Input.down(GLFW_KEY_DOWN)) p2y += padSpeed * dt;

        // clamp paddles
        p1y = clamp(p1y, 0, H - padH);
        p2y = clamp(p2y, 0, H - padH);

        // move ball
        ballX += vx * dt; ballY += vy * dt;

        // walls
        if (ballY <= 0) { ballY = 0; vy = Math.abs(vy); }
        if (ballY + ballS >= H) { ballY = H - ballS; vy = -Math.abs(vy); }

        // paddle collisions (AABB)
        if (overlap(ballX, ballY, ballS, ballS, p1x, p1y, padW, padH) && vx < 0) {
            ballX = p1x + padW; vx = Math.abs(vx) + 20f;
        }
        if (overlap(ballX, ballY, ballS, ballS, p2x, p2y, padW, padH) && vx > 0) {
            ballX = p2x - ballS; vx = -Math.abs(vx) - 20f;
        }

        // score (reset)
        if (ballX < -50 || ballX > W + 50) {
            ballX = (W - ballS) * 0.5f; ballY = (H - ballS) * 0.5f;
            vx = (Math.random() < 0.5 ? -1 : 1) * 220;
            vy = (float)((Math.random() * 2 - 1) * 180);
        }
    }

    @Override public void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        // center line
        for (int y = 0; y < H; y += 24) {
            g.fillRect(W/2f - 2, y, 4, 12, 0.25f, 0.25f, 0.3f);
        }
        // paddles & ball
        g.fillRect(p1x, p1y, padW, padH, 0.9f, 0.9f, 0.9f);
        g.fillRect(p2x, p2y, padW, padH, 0.9f, 0.9f, 0.9f);
        g.fillRect(ballX, ballY, ballS, ballS, 0.95f, 0.95f, 0.2f);
    }

    @Override public void dispose() {}

    private static float clamp(float v, float lo, float hi) { return Math.max(lo, Math.min(hi, v)); }
    private static boolean overlap(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
