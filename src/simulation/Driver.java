package simulation;

import java.util.Scanner;

public class Driver 
{
	public static void main(String[] args) 
	{
		Simulator simulator = new Simulator();
		Scanner scan = new Scanner(System.in);
		boolean scanChecker = false;
		String input;
		
		System.out.println("Welcome to the Foxes and Rabbits simulation.");
		System.out.println();
		System.out.println("You have several options to control the simulation.");
		System.out.println();
		System.out.println("1 : This will simulate one step in the simulation.");
		System.out.println("5 : This will simulate five steps in the simulation.");
		System.out.println("L : This will run a long-term simulation of 4000 steps.");
		System.out.println("Q : This will quit the simulation input scanner.");
		
		while(!scanChecker)
		{
			input = scan.nextLine();
			if(input.equals("1"))
			{
				simulator.simulateOneStep();
			}
			if(input.equals("5"))
			{
				simulator.simulate(5);
			}
			if(input.equals("L"))
			{
				simulator.runLongSimulation();
			}
			if(input.equals("Q"))
			{
				scanChecker = true;
			}
		}
		System.out.println("Thank you for using the F&S Simulator!");
		scan.close();
	}

}
