package simulation;

import java.util.List;
import java.util.Random;
/**
 * A class representing shared characteristics of animals.
 */
public abstract class Animal
{
	// Whether the animal is alive or not.
	private boolean alive;
	// The animal's field.
	private Field field;
	// The animal's position in the field.
	private Location location;
	// Individual characteristics (instance fields).
	// The fox's age.
	protected int age;
	// A shared random number generator to control breeding.
	protected static final Random rand = Randomizer.getRandom();

	/**
	 * Create a new animal at location in field.
	 * 
	 * @param field The field currently occupied.
	 * @param location The location within the field.
	 */
	public Animal(Field field, Location location)
	{
		alive = true;
		this.field = field;
		setLocation(location);
		age = 0;
	}

	/**
	 * Make this animal act - that is: make it do
	 * whatever it wants/needs to do.
	 * @param newAnimals A list to receive newly born animals.
	 */
	abstract public void act(List<Animal> newAnimals);

	/**
	 * Check whether the animal is alive or not.
	 * @return true if the animal is still alive.
	 */
	protected boolean isAlive()
	{
		return alive;
	}

	/**
	 * Indicate that the animal is no longer alive.
	 * It is removed from the field.
	 */
	protected void setDead()
	{
		alive = false;
		if(location != null) 
		{
			field.clear(location);
			location = null;
			field = null;
		}
	}

	/**
	 * Return the animal's location.
	 * @return The animal's location.
	 */
	protected Location getLocation()
	{
		return location;
	}

	/**
	 * Place the animal at the new location in the given field.
	 * @param newLocation The animal's new location.
	 */
	protected void setLocation(Location newLocation)
	{
		if(location != null) 
		{
			field.clear(location);
		}
		location = newLocation;
		field.place(this, newLocation);
	}

	/**
	 * Return the animal's field.
	 * @return The animal's field.
	 */
	protected Field getField()
	{
		return field;
	}

	protected boolean canBreed()
	{
		return age >= getBreedingAge();
	}

	protected abstract int getBreedingAge();

	protected void incrementAge()
	{
		age++;
		if(age > getMaxAge()) 
		{
			setDead();
		}
	}

	protected int breed()	
	{
		int births = 0;
		if(canBreed() && rand.nextDouble() <= getBreedingProbability())
		{
			births = rand.nextInt(getMaxLitterSize()) + 1;
		}
		return births;
	}

	protected abstract int getMaxAge();

	protected abstract double getBreedingProbability();

	protected abstract int getMaxLitterSize();
	
	protected abstract Animal reproduce(Field field, Location loc);

	protected void giveBirth(List<Animal> newAnimals)
	{
		Field field = getField();
		List<Location> free = field.getFreeAdjacentLocations(getLocation());
		int births = breed();
		for(int b = 0; b < births && free.size() > 0; b++) 	
		{
			Location loc = free.remove(0);
			Animal young = reproduce(field, loc);
			newAnimals.add(young);
		}
	}
}
