package gameEngine;

import gameEngine.core.GameLoop;
import gameEngine.core.Window;
import gameEngine.pong.Pong;

public class Main {
    public static void main(String[] args) {
        int W = 960, H = 540;
        Window window = new Window(W, H, "Pong - LWJGL");

        GameLoop loop = new GameLoop(window);

        window.create();
        
        loop.run(new Pong(W, H));
    }
}
