package battle;

import java.util.Random;

import entities.Enemy;
import entities.Player;

public class Battle {
	private Player p;
	private Enemy e;
	private int turn;
	private Random rng;
	private static final int rollVariance = 10;
	private static final int rollSize = 2 * rollVariance - 1;

	public Battle(Player p, Enemy e) {
		turn = 1;
		this.p = p;
		this.e = e;
		rng = new Random();
	}

	public void fight() {
		if (!winInitiative()) {
			enemyTurn();
			turn = 2;
		}
		while (!p.isDead() && !e.isDead() && turn <= 30) {
			playerTurn();
			enemyTurn();
		}
	}

	private void playerTurn() {

	}

	private void enemyTurn() {

	}

	private int roll() {
		return rng.nextInt(rollSize) - rollVariance;
	}

	private boolean winInitiative() {
		int playerInit = p.getInitiative();
		int enemyInit = e.getInitiative();
		playerInit += roll();
		enemyInit += roll();
		return playerInit >= enemyInit;

	}

}
