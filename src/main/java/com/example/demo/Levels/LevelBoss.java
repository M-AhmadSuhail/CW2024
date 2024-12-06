package com.example.demo.Levels;

import com.example.demo.Boss;
import com.example.demo.BossHealth;
import com.example.demo.LevelParent;
import com.example.demo.LevelView;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private final BossHealth bossHealth;

	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "Final LEVEL: Defeat THE BOSS");
		this.boss = new Boss();
		this.boss.setLevelView(levelView);
		this.bossHealth = new BossHealth(850, 25, boss.getHealth());
	}
	@Override
	protected void checkIfGameOver() {
		bossHealth.updateHealth(boss.getHealth());
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			System.out.println("Boss Destroyed");
			winGame();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !boss.isDestroyed()) {
			System.out.println("SPAWNING BOSS");
			addEnemyUnit(boss);
			getRoot().getChildren().add(bossHealth.getContainer());
		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH,0);
	}

}