package gameEngine.core;

public class Timer {
	private double last;
	
	public void init() { last = now(); }
	
	public float delta() { 
		double n = now(); 
		float dt = (float)(n - last); 
		last = n; return dt;
		}
	
	private double now() { return System.nanoTime() / 1_000_000_000.0; }
	
}
