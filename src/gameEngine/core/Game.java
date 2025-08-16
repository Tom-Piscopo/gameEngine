package gameEngine.core;

public interface Game {
	void init();
	void update(float dt);
	void render();
	void dispose();
}
