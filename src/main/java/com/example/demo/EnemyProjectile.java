package com.example.demo;

public class EnemyProjectile extends Projectile {

	private Runnable onDestruction;

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
	}
	public void setOnDestruction(Runnable onDestruction) {
		this.onDestruction = onDestruction;
	}
	@Override
	public void destroy() {
		if (onDestruction != null) {
			onDestruction.run();
		}
		super.destroy();
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}