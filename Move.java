public class Move
{
    // Private constants
    private static final int MAX_DAMAGE = 25;
    
    // Write your implementation of the Move class here
    private String name;
    private int damage;
    
    public Move(String name, int damage) {
        this.name = name;
        this.damage = Math.min(damage, MAX_DAMAGE);
    }
    // Returns the name of the Move
    public String getName() {
        return name;
    }

    // Returns how much damage this Move does
    public int getDamage() {
        return damage;
    }
    
    /*
     * Returns true if this Move has the same name
     * as the Move `other`, false otherwise
     */
    public boolean equals(Move other) {
        return name.equals(other.name);
    }

    // Returns a String representation of this Move
    // Example: "Water Gun (15 Damage)"
    public String toString() {
        return name + " (" + damage + " Damage)";
    }
}
