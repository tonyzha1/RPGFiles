package items;

public abstract class Item {
	public enum Slot {
		WEAPON, TORSO, PANTS, ACCESSORY, OFFHAND, HEAD
	}
	protected Slot slot;
}
