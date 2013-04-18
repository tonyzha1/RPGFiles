package game;

import items.Weapon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;

import windows.WindowView;
import battle.Battle;
import buffs.Buff;
import buffs.Buff.BuffStats;
import buffs.Buff.TriggerTime;
import buffs.PermanentBuff;
import buffs.Proc;
import entities.BattleEntity.Stat;
import entities.Enemy;
import entities.Player;

public class MainGame {

	public static BufferedReader inReader = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String args[]) {

		Player p = new Player("Kael", 11, 15, 12, Stat.AGILITY);
	
		Buff b1 = new PermanentBuff("StingerStats", BuffStats.AGILITY, 0, 1,
				"Stinger Stat buff", false);
		Buff b2 = new PermanentBuff("Stinging Wit",
				TriggerTime.AFTER_PLAYER_HIT, new Proc() {

					@Override
					public void action(Battle b) {
						Player p = b.getPlayer();
						Enemy e = b.getEnemy();
						int pAgi = p.getStatByName(Stat.AGILITY);
						int pInt = p.getStatByName(Stat.INTELLIGENCE);
						int eInt = e.getStatByName(Stat.INTELLIGENCE);
						if (pAgi >= eInt || pInt >= eInt) {
							int dcomp1 = Math.max(pAgi - eInt, 0);// Agi > Int
							int dcomp2 = Math.max(pInt - eInt, 0);// Int > Int
							int wpdamage = b.weaponDamageRoll();
							int dmg = (dcomp1 + dcomp2) / 2
									+ wpdamage;// total
							// damage
							e.takeDamage(dmg, false);
							b.outputPrintln(e.name
									+ " says \"That stings!\", "
									+ "prompting you to stab him again for "
									+ dmg + " damage");
						}
					}

				}, "Stinger glows blue when exposed to bad puns", true);
		ArrayList<Buff> stingerBuffs = new ArrayList<Buff>();
		stingerBuffs.add(b1);
		stingerBuffs.add(b2);
		Weapon stinger = new Weapon("Stinger", Stat.AGILITY, 10, stingerBuffs);

		
		WindowView app = new WindowView(p);
		p.equip(stinger);
		app.refresh();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
