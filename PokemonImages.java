import java.util.*;
import java.io.*;

// This is the PokemonImages class
// It acts as a dictionary to get ASCII art for a given pokemon name
// Feel free to explore this code, it uses FileReader and BufferedReader
// to read in Pokemon images from the "pokemonImages.txt" file.
// DO NOT CHANGE THIS CODE, the program may not work if you do.
public class PokemonImages {
	private HashMap<String, String> pokedex;
	private static final String IMAGE_FILE = "pokemonImages.txt";
	
	public PokemonImages()
	{
		pokedex = new HashMap<String, String>();
		loadImages(IMAGE_FILE);
	}
	
	public String getPokemonImage(String name)
	{		
		return pokedex.get(name.toLowerCase());
	}
	
	public String getPokemonImage(Pokemon pokemon)
	{
		String name = pokemon.getName().toLowerCase();
		return pokedex.get(name);
	}
	
	public void setPokemonImage(String pokemonName, String image)
	{
		if(pokemonName != null && image != null)
		{
			pokedex.put(pokemonName, image);
		}
	}
	
	private void loadImages(String filename)
	{
	    // Try to do the following code, but there may be errors
	    // when reading the file.
		try {
			
			// Create a BufferedReader to read from the file where the 
			// ASCII Art is stored
			BufferedReader input = new BufferedReader(new FileReader(filename));
			String line = input.readLine();
			String currentImage = "";
			String currentPokemon = null;
			
			// Read each line of the file and add each pokemon name and image 
			// to the HashMap
			while(line != null)
			{
				// Comment line, we can skip it
				if(line.startsWith("$$"))
				{
					line = input.readLine();
					continue;
				}
				
				// The "##" indicates a new pokemon name
				// Store the old pokemon if there was one and start a new pokemon
				else if(line.startsWith("##"))
				{
					if(currentPokemon != null)
					{
						pokedex.put(currentPokemon, currentImage);
					}
					
					// Get the name of the new pokemon
					currentPokemon = line.substring(2).toLowerCase();
					currentImage = "";
				}
				
				else
				{
					currentImage += line + "\n";
				}
				
				line = input.readLine();
			}
		
			// Fencepost situation
			// The last pokemon has not been added yet, add it to the HashMap
			if(currentPokemon != null)
			{
				pokedex.put(currentPokemon, currentImage);
			}
			
			input.close();
		} 
		
		// If there were any errors when reading the file
		// they will be handled by these `catch` clauses
		catch (FileNotFoundException e) {
			System.out.println("Couldn't open file: " + filename);
		} catch (IOException e) {
			System.out.println("There was an error while reading the file: " + filename);
			e.printStackTrace();
		}
	}
}
