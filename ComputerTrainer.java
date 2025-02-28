import java.util.ArrayList;

public class ComputerTrainer extends PokemonTrainer {
    // Private constants
    private static final String[] POKEMON_NAMES = {"Pikachu", "Bulbasaur", "Charmander", "Squirtle"};
    private static final String[] MOVE_NAMES = {"Tailwhip", "Bodyslam", "Splash", "Shock"};
    private static final int MAX_DAMAGE = 25;
    private static final int MAX_MOVES = 4;

    private PokemonImages images = new PokemonImages();

    // Constructor
    public ComputerTrainer(String name) {
        super(name); // Call the superclass constructor to set the name
        addRandomPokemon(); // Add the first random Pokemon
        addRandomPokemon(); // Add the second random Pokemon
    }

    /*
     * Adds a randomly generated Pokemon to this ComputerTrainer's
     * collection of Pokemon. A ComputerTrainer can only have 2
     * Pokemon. This method returns true if there was room for the
     * new Pokemon and it was successfully added, false otherwise.
     */
    public boolean addRandomPokemon() {
        if (super.getPokemon().size() >= MAX_POKEMON) {
            return false; // No room for more Pokemon
        }

        // Randomly select a Pokemon name using Randomizer
        String pokemonName = POKEMON_NAMES[Randomizer.nextInt(POKEMON_NAMES.length)];
        Pokemon pokemon = new Pokemon(pokemonName, images.getPokemonImage(pokemonName));

        // Add random moves to the Pokemon
        for (int i = 0; i < MAX_MOVES; i++) {
            String moveName = MOVE_NAMES[Randomizer.nextInt(MOVE_NAMES.length)];
            int moveDamage = Randomizer.nextInt(1, MAX_DAMAGE); // Random damage between 1 and MAX_DAMAGE
            pokemon.learnMove(new Move(moveName, moveDamage));
        }

        // Add the Pokemon to the trainer's collection
        return super.addPokemon(pokemon);
    }

    /*
     * Returns a Move randomly chosen from the set of Moves
     * that this trainer's current Pokemon knows.
     * If all Pokemon have fainted, returns null.
     */
    public Move chooseRandomMove() {
        Pokemon currentPokemon = super.getNextPokemon();
        if (currentPokemon == null) {
            return null; // All Pokemon have fainted
        }

        // Get the list of moves for the current Pokemon
        ArrayList<Move> moves = currentPokemon.getMoves();
        if (moves.isEmpty()) {
            return null; // No moves available
        }

        // Randomly select a move using Randomizer
        return moves.get(Randomizer.nextInt(moves.size()));
    }
}
