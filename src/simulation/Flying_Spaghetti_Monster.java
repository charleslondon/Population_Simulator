package simulation;

import java.util.List;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 */
public class Flying_Spaghetti_Monster extends Animal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 100;
    // The age to which a fox can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.30;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int ANY_FOOD_VALUE = 10;
    
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    public Flying_Spaghetti_Monster(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) 
        {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(ANY_FOOD_VALUE);
        }
        else 
        {
            age = 0;
            foodLevel = ANY_FOOD_VALUE;
        }
    }

    public void act(List<Animal> newSpaghetti)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) 
        {
            super.giveBirth(newSpaghetti);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) 
            { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) 
            {
                setLocation(newLocation);
            }
            else 
            {
                // Overcrowding.
                setDead();
            }
        }
    }

    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) 
        {
            setDead();
        }
    }
    
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = ANY_FOOD_VALUE;
                    return where;
                }
            } else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }
        }
        return null;
    }
        
    public int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    public int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    public double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
    
    public int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    public Animal reproduce(Field field, Location loc)
    {
        return new Flying_Spaghetti_Monster(false, field, loc);
    }  
}
