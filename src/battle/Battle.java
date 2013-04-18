package battle;

import java.io.IOException;
import java.util.Random;

import javax.swing.JTextArea;

import buffs.Buff.BuffStats;
import buffs.Buff.TriggerTime;
import entities.BattleEntity.Stat;
import entities.Enemy;
import entities.Player;
import game.MainGame;

public class Battle {
	private StringBuilder output;
	private Player p;
	private Enemy e;
	private int turn;
	private Random rng;
	private static final int rollVariance = 10;
	private static final int rollSize = 2 * rollVariance - 1;

	// public variables for buffs to mess with.
	public int playerDamage;
	public boolean playerHit;
	public int enemyDamage;
	public boolean enemyHit;

	public Battle(Player p, Enemy e) {
		turn = 0;
		this.p = p;
		this.e = e;
		output = new StringBuilder();
		outputPrintln("You are fighting a "+e.name);
		rng = new Random();
	}
	public void outputPrint(String s)
	{
		output.append(s);
	}
	public void outputPrintln(String s)
	{
		output.append(s);
		output.append("\n");
	}
	public String getOutput()
	{
		String ans = output.toString();
		output=new StringBuilder();
		return ans;
	}
	public boolean takeTurn()//true if battle is ongoing. 
	{
		if(p.isDead())
		{
			outputPrintln("You shouldn't be fighting");
			return false;
		}
		if (turn==0 && !winInitiative()) {
			outputPrintln("You lost initiative!");
			enemyTurn();
			if(!p.isDead())
				enemyBasicAttack(true);
			turn = 2;
			return (!p.isDead() && !e.isDead());
		}
		else if(turn==0)
		{
			outputPrintln("You win initiative");
			turn = 1;
			return true;
		}
		
		playerTurn();
		if (!e.isDead())
			enemyTurn();
		if (!p.isDead())
			playerBasicAttack(true);
		if (!e.isDead())
			enemyBasicAttack(true);
		
		if (p.isDead()) {
			outputPrintln(p.name + " has lost!");
			p.tick();
			return false;
		} else if (e.isDead()) {
			outputPrintln(p.name + " wins the fight!");
			p.tick();
			return false;
		} else if(turn >=30)// turn >=30
		{
			outputPrintln("Out of boredom, " + e.name
					+ " storms off in a huff");
			p.tick();
			return false;
		}
		return true;
	}

	public Player getPlayer() {
		return p;
	}

	public Enemy getEnemy() {
		return e;
	}

	public int getRound() {
		return turn;
	}

	private void playerTurn() {

		// Get player input
		// if player gave input
		// determine if skill or item.
		// use skill or item.
		// activate buffs based on timing.
	}

	public void forcePlayerBasicAttack() {
		playerBasicAttack(false);
	}

	public void forceEnemyBasicAttack() {
		enemyBasicAttack(false);
	}

	public int weaponDamageRoll() {
		return (int) (0.5 + p.getWeaponPower() * (0.1 + 0.1 * rng.nextDouble()));
	}

	private void playerBasicAttack(boolean useBuffs) {
		// apply before-attack buffs.
		// attack, using a hit function.
		int attackRoll = p.attack() + roll();
		int def = e.defense();
		playerHit = attackRoll >= def;
		// always-hit or always-miss buffs.
		if (playerHit) {
			// roll damage. Damage is based off attacking stat;
			int damage = (p.attack() - e.defense() + 1) / 2;
			if (p.getAttackingStat() != Stat.STRENGTH)
				damage /= 2;// Strenght deals more weapon damage.
			damage = Math.max(0, damage);// no penalty for weak hits.
			int weaponDamage = weaponDamageRoll();
			//weaponDamage = Math.max(1, damage);
			playerDamage = damage + weaponDamage;

			// bigger bonus if matches primary stat.
			// proc buffs that modify damage.
			// print damage message
			int realDamage = e.takeDamage(playerDamage, true);
			String bonusString = "";
			if (p.getDamageBonus() > 0) {
				e.takeDamage(p.getDamageBonus(), false);
				bonusString += "(+" + p.getDamageBonus() + ")";
			}

			outputPrintln(p.name + " hit " + e.name + " for " + realDamage
					+ bonusString + " damage.");
			p.buffs.procAtTime(TriggerTime.AFTER_PLAYER_HIT, this);
			e.buffs.procAtTime(TriggerTime.AFTER_PLAYER_HIT, this);
		} else // miss
		{
			// apply miss buffs.
			outputPrintln(p.name + " missed.");
			p.buffs.procAtTime(TriggerTime.AFTER_PLAYER_MISS, this);
			e.buffs.procAtTime(TriggerTime.AFTER_PLAYER_MISS, this);
		}
		// apply after-attack buffs.
		p.buffs.procAtTime(TriggerTime.AFTER_PLAYER_ATTACK, this);
		e.buffs.procAtTime(TriggerTime.AFTER_PLAYER_ATTACK, this);
	}

	private void enemyBasicAttack(boolean useBuffs) {// enemy attacks
		// apply before-attack buffs.
		// attack
		int attackRoll = e.attack() + roll();
		int def = p.defense();
		enemyHit = attackRoll >= def;
		if (enemyHit) // hit
		{
			enemyDamage = (int) (0.5 + e.attack()
					* (0.2 + 0.05 * rng.nextDouble()));
			int realDamage = p.takeDamage(enemyDamage, true);
			String bonusString = "";
			if (e.getDamageBonus() > 0) {
				p.takeDamage(e.getDamageBonus(), false);
				bonusString += "(+" + e.getDamageBonus() + ")";
			}
			outputPrintln(e.name + " hit " + p.name + " for " + realDamage
					+ bonusString + " damage");
		} else {
			outputPrintln(e.name + " missed.");
		}
		// apply after-attack buffs.
		outputPrintln("");
		
	}

	private void enemyTurn() {
		// use enemy special abilities.
	}

	private int roll() {
		return rng.nextInt(rollSize) - rollVariance;
	}

	private boolean winInitiative() {
		int playerInit = p.getInitiative();
		double pm = p.buffs.getMultiplier(BuffStats.INITIATIVE);
		int pa = p.buffs.getAdditive(BuffStats.INITIATIVE);
		playerInit = (int) (pm * playerInit + pa);
		int enemyInit = e.getInitiative();
		double em = e.buffs.getMultiplier(BuffStats.INITIATIVE);
		int ea = e.buffs.getAdditive(BuffStats.INITIATIVE);
		enemyInit = (int) (em * enemyInit + ea);
		playerInit += roll();
		enemyInit += roll();
		return playerInit >= enemyInit;

	}

}
