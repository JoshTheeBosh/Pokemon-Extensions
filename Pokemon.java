import java.util.ArrayList;

public class Pokemon {
    // Private constants
    private static final int MAX_HEALTH = 100;
    private static final int MAX_MOVES = 4;

    // Instance variables
    private String name;
    private int health;
    private ArrayList<Move> moves;
    private String image;
    private String type;

    // Constructors
    public Pokemon(String name, int health) {
        this.name = name;
        this.health = Math.min(health, MAX_HEALTH);
        this.moves = new ArrayList<>();
        this.type = setType();
    }

    public Pokemon(String name) {
        this(name, MAX_HEALTH);
    }

    public Pokemon(String name, String image) {
        this(name, MAX_HEALTH);
        this.image = image;
    }

    // Set the type based on the Pokemon's name
    private String setType() {
        if (name.equalsIgnoreCase("Squirtle")) {
            return "WaterPokemon";
        } else if (name.equalsIgnoreCase("Charmander")) {
            return "FirePokemon";
        } else if (name.equalsIgnoreCase("Bulbasaur")) {
            return "GrassPokemon";
        } else {
            return "NormalPokemon"; // Default type
        }
    }

    // Returns the type of the Pokemon
    public String getType() {
        return type;
    }

    // Returns an ArrayList of all the Moves this Pokemon knows
    public ArrayList<Move> getMoves() {
        return moves;
    }

    // Returns true if the Pokemon knows this move (has this Move in its collection), false otherwise
    public boolean knowsMove(Move move) {
        return moves.contains(move);
    }

    // Returns true if the Pokemon knows a Move with the name `moveName`, false otherwise
    public boolean knowsMove(String moveName) {
        for (Move move : moves) {
            if (move.getName().equals(moveName)) {
                return true;
            }
        }
        return false;
    }

    // Tries to perform the Move `move` on the Pokemon `other`
    public boolean attack(Pokemon opponent, Move move) {
        if (knowsMove(move)) {
            double effectiveness = 1.0;
            if ((type.equals("WaterPokemon") && opponent.getType().equals("FirePokemon"))) {
                effectiveness = 1.5;
            } else if ((type.equals("FirePokemon") && opponent.getType().equals("GrassPokemon"))) {
                effectiveness = 1.5;
            } else if ((type.equals("GrassPokemon") && opponent.getType().equals("WaterPokemon"))) {
                effectiveness = 1.5;
            }

            int damage = (int) (move.getDamage() * effectiveness);
            opponent.setHealth(Math.max(opponent.getHealth() - damage, 0));
            return true;
        }
        return false;
    }

    // Tries to perform a Move with the name `moveName` on the Pokemon `other`
    public boolean attack(Pokemon opponent, String moveName) {
        for (Move move : moves) {
            if (move.getName().equals(moveName)) {
                return attack(opponent, move);
            }
        }
        return false;
    }

    // Sets this Pokemon's image to be `image`
    public void setImage(String image) {
        this.image = image;
    }

    // Returns the ASCII Art image for this Pokemon
    public String getImage() {
        return image;
    }

    // Returns the name of the Pokemon
    public String getName() {
        return name;
    }

    // Returns how much health this Pokemon has
    public int getHealth() {
        return health;
    }

    // Sets the health of the Pokemon
    public void setHealth(int health) {
        this.health = Math.min(health, MAX_HEALTH);
    }

    // Returns true if this Pokemon has fainted, false otherwise
    public boolean hasFainted() {
        return health <= 0;
    }

    // Returns true if this Pokemon can still learn more Moves, false otherwise
    public boolean canLearnMoreMoves() {
        return moves.size() < MAX_MOVES;
    }

    // Adds the Move `move` to the collection of Moves that this Pokemon knows
    public boolean learnMove(Move move) {
        if (canLearnMoreMoves()) {
            moves.add(move);
            return true;
        }
        return false;
    }

    // Remove the Move `move` from this Pokemon's collection of Moves, if it's there
    public void forgetMove(Move move) {
        moves.remove(move);
    }

    // Return a String containing the name and health of this Pokemon
    public String toString() {
        return image + "\n" + name + " (Health: " + health + " / 100)";
    }
}
