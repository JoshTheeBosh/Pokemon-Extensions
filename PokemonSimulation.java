import java.util.Scanner;
import java.util.ArrayList;

public class PokemonSimulation extends ConsoleProgram {
    private PokemonImages images = new PokemonImages();

    public void run() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Pokemon Simulator!\n");
        System.out.println("Set up your Pokemon Trainer");
        PokemonTrainer player = setupTrainer(input);
        player.addItem(new Item("Potion", 20));
        player.addItem(new Item("Super Potion", 50));
        ComputerTrainer computer = new ComputerTrainer("Computer");

        // Start the battle
        battle(player, computer, input);
    }

    private PokemonTrainer setupTrainer(Scanner input) {
        System.out.print("Trainer, what is your name? ");
        String name = input.next();
        PokemonTrainer trainer = new PokemonTrainer(name);
        System.out.println("Hello " + trainer + "!\n");
        System.out.println("Choose your first Pokemon");
        setupPokemon(input, trainer);
        System.out.println("Choose your second Pokemon");
        setupPokemon(input, trainer);
        return trainer;
    }

    private void setupPokemon(Scanner input, PokemonTrainer trainer) {
        System.out.print("Enter the name of your Pokemon: ");
        String pokemonName = input.next();
        Pokemon pokemon = new Pokemon(pokemonName, images.getPokemonImage(pokemonName));
        trainer.addPokemon(pokemon);
        System.out.println("You chose:\n" + pokemon + "\n");

        while (pokemon.canLearnMoreMoves()) {
            System.out.print("Would you like to teach " + pokemon.getName() + " a new move? (yes/no): ");
            String choice = input.next();
            if (choice.equalsIgnoreCase("no")) {
                break;
            }
            System.out.print("Enter the name of the move: ");
            String moveName = input.next();
            System.out.print("How much damage does this move do? ");
            int moveDmg = input.nextInt();
            pokemon.learnMove(new Move(moveName, moveDmg));
            System.out.println(pokemon.getName() + " learned " + moveName + " (" + Math.min(moveDmg, 25) + " Damage)!");
        }
        System.out.println(pokemon.getName() + " has learned all of their moves\n");
    }

    private void battle(PokemonTrainer player, PokemonTrainer computer, Scanner input) {
        System.out.println("Let the battle begin!\n");
        Pokemon playerPokemon = player.getNextPokemon();
        while (!player.hasLost() && !computer.hasLost()) {
            // Get the current active Pokemon for both trainers
            Pokemon computerPokemon = computer.getNextPokemon();
    
            if (playerPokemon == null || computerPokemon == null) {
                break; // One of the trainers has no more Pokemon
            }
    
            System.out.println(player + "'s " + playerPokemon.getName() + " vs " + computer + "'s " + computerPokemon.getName() + "\n");
    
            // Player turn
            System.out.println("\nYour options:");
            System.out.println("1. Attack");
            System.out.println("2. Switch Pokemon");
            System.out.println("3. Use Item");
    
            int choice = input.nextInt();
            if (choice == 1) {
                // Attack logic
                System.out.println(playerPokemon.getName() + "'s Moves:");
                for (Move move : playerPokemon.getMoves()) {
                    System.out.println("- " + move);
                }
    
                System.out.print("Choose a move for " + playerPokemon.getName() + ": ");
                String moveName = input.next();
    
                if (playerPokemon.attack(computerPokemon, moveName)) {
                    System.out.println(playerPokemon.getName() + " used " + moveName + "!");
                    System.out.println(computerPokemon.getName() + " now has " + computerPokemon.getHealth() + " health.\n");
                } else {
                    System.out.println(playerPokemon.getName() + " doesn't know that move!\n");
                }
            } else if (choice == 2) {
                // Switch Pokemon
                playerPokemon = selectPokemon(input, player);
                if (playerPokemon != null) {
                    System.out.println(player + " switched to " + playerPokemon.getName() + "!\n");
                    player.setActivePokemon(playerPokemon);
                    
                }
            } else if (choice == 3) {
                // Use Item
                useItem(input, player, playerPokemon);
            }
    
            // Check if the opponent's Pokemon has fainted
            if (computerPokemon.hasFainted()) {
                System.out.println(computerPokemon.getName() + " has fainted!\n");
                computer.getNextPokemon(); // Switch to the next Pokemon
            }
    
            // Computer turn
            if (!computer.hasLost()) {
                Move computerMove = ((ComputerTrainer) computer).chooseRandomMove();
                if (computerMove != null) {
                    System.out.println(computer + "'s " + computerPokemon.getName() + " used " + computerMove.getName() + "!");
                    computerPokemon.attack(playerPokemon, computerMove);
                    System.out.println(playerPokemon.getName() + " now has " + playerPokemon.getHealth() + " health.\n");
                }
    
                // Check if the player's Pokemon has fainted
                if (playerPokemon.hasFainted()) {
                    System.out.println(playerPokemon.getName() + " has fainted!\n");
                    player.getNextPokemon(); // Switch to the next Pokemon
                }
            }
        }
    
        // Determine the winner
        if (player.hasLost()) {
            System.out.println(computer + " wins the battle!");
        } else if (computer.hasLost()) {
            System.out.println(player + " wins the battle!");
        } else {
            System.out.println("The battle ended in a draw!");
        }
    }

    private Pokemon selectPokemon(Scanner input, PokemonTrainer trainer) {
        System.out.println("Choose a Pokemon to switch to (#):");
        ArrayList<Pokemon> pokemonList = trainer.getPokemon();
        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon p = pokemonList.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (Health: " + p.getHealth() + ")");
        }

        int choice = input.nextInt();
        if (choice > 0 && choice <= pokemonList.size()) {
            return pokemonList.get(choice - 1);
        }
        System.out.println("Invalid choice!");
        return selectPokemon(input, trainer);
    }

    private void useItem(Scanner input, PokemonTrainer trainer, Pokemon pokemon) {
        System.out.println("Choose an item to use (#):");
        ArrayList<Item> items = trainer.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (Heals: " + item.getHealAmount() + ")");
        }

        int choice = input.nextInt();
        if (choice > 0 && choice <= items.size()) {
            Item item = items.get(choice - 1);
            item.use(pokemon);
            System.out.println(pokemon.getName() + " was healed by " + item.getHealAmount() + " HP!\n");
            items.remove(choice - 1);
        } else {
            System.out.println("Invalid choice!");
        }
    }
}
