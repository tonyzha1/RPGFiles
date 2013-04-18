package items;

import java.util.ArrayList;
import java.util.List;

import buffs.Buff;

public abstract class Item {
	public enum Slot {
		WEAPON, TORSO, PANTS, ACCESSORY, OFFHAND, HEAD, USABLE
	}

	public final String name;
	public List<Buff> buffs;
	private Slot slot;

	public Item(String name, Slot s)
	{
		this.name = name;
		slot=s;
		buffs= new ArrayList<Buff>();
	}
	public Item(String name, Slot s, List<Buff> buffs)
	{
		this.name = name;
		slot = s;
		this.buffs= new ArrayList<Buff>();
		for (Buff b : buffs)
		{
			this.buffs.add(b);
		}
	}
}
