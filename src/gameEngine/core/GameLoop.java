package gameEngine.core;

import gameEngine.input.Input;

public class GameLoop {
	private final Window window;
	private final float fixedDt = 1f / 60f;
	
	public GameLoop(Window window) {this.window = window;}
	
	public void run(Game game) {
		window.create();
		Input.attach(window.handle());
		game.init();
		
		var timer = new Timer();
		timer.init();
		float acc = 0f;
		
		while (!window.shouldClose()) {
			float dt = Math.min(timer.delta(), 0.25f);
			acc += dt;
			
			while (acc >= fixedDt) {
				game.update(fixedDt);
				acc -= fixedDt;
			}
			
			game.render();
			window.swap();
			window.poll();
		}
		game.dispose();
		window.destroy();
	}
}
