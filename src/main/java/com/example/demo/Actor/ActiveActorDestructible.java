package com.example.demo.Actor;
import com.example.demo.controller.Main;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}
	public boolean isOutOfBounds(double x,double screenWidth) {
		return x < 0 || x > screenWidth;
	}

	@Override
	public void updatePosition() {
		double x = getLayoutX() + getTranslateX();
		//double y = getLayoutY() + getTranslateY();
		if (isOutOfBounds(x, Main.getScreenWidth())) {
			System.out.println(this.getClass().getSimpleName() + " eOut of Bound.");
			this.destroy();
		}
	}

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

}