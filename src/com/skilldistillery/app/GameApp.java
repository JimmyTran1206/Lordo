package com.skilldistillery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.entities.Monster;
import com.skilldistillery.entities.Player;

public class GameApp {
	int playerInitialHP = 500;
	int playerAttack = 500;
	double playerDodgeChance = 0.15;
	private Player player;
	private String userChoice;
	private Scanner kb = new Scanner(System.in);
	private List<Monster> monsterList= new ArrayList<>();
	
	String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE= "\u001B[34m";
    String PURPLE="\u001B[35m";
	String CYAN = "\u001B[36m";


	
	public static void main(String[] args) {
		GameApp app = new GameApp();
		app.run();
		app.close();
	}

	public void run() {
		initializePlayer();
		initializeMonster();
		displayMenu();
		while (!isVictorious()) {
			processUserChoice(userChoice);
		}
		
		System.out.println("Thank you for playing!");
	}

	public void close() {
		kb.close();
	}
	
	public boolean isVictorious() {
		for (Monster m: monsterList) {
			if (!m.isDefeated()) {
				return false;
			}
		}
		return true;
	}

	public void initializePlayer() {
		System.out.println("Welcome to Lordo");
		System.out.print("Please enter your hero name: ");
		String playerName = kb.nextLine();
		System.out.println("Welcome " + YELLOW + playerName + RESET+ " to the Lordo land.");
		System.out.println("You find yourself trapped in a room with three doors.");
		player = new Player(playerInitialHP, playerAttack, playerDodgeChance, playerName);
	}
	
	public void initializeMonster() {
		Monster monster1 = new Monster(700, 5, 0.1, "Medusa", 0.5);
		Monster monster2 = new Monster(400, 10, 0.2, "Baba Yaga", 0.5);
		Monster monster3 = new Monster(404, 44, 0.3, "404 NOT FOUND", 0.1);
		monsterList.add(monster1);
		monsterList.add(monster2);
		monsterList.add(monster3);
	}

	public void displayMenu() {
		System.out.println();
		System.out.println("Please choose a door");
		System.out.println("1. Door Emerald");
		System.out.println("2. Door Crystal");
		System.out.println("3. Door Ruby");
		System.out.print("Your choice: ");
		userChoice = kb.nextLine();
	}

	public void processUserChoice(String userChoice) {
		switch (userChoice) {
		case "1":
			enterDoor(monsterList.get(0), "Emerald");
			break;
		case "2":
			enterDoor(monsterList.get(1), "Crystal");
			break;
		case "3":
			enterDoor(monsterList.get(2), "Ruby");
			break;
		default:
			System.out.println("Invalid choice. Please choose 1,2, or 3");
			displayMenu();
		}
	}

	public void enterDoor(Monster monster, String doorName) {
		if(monster.isDefeated()) {
			System.out.printf("The monster %s has already been defeated. \n", monster.getName());
			System.out.println("Returning to the main room.");
			displayMenu();
			return;
		}
		System.out.printf("\nYou encounter a %s" + GREEN+"(%d)"+RESET+ " behind door %s. \n", monster.getName(), monster.getHitPoints(), doorName);
		System.out.println();
		System.out.println("Select one of these options:");
		displayOptions(monster);
		userChoice = kb.nextLine();

		// Battle implementation
		while (!player.isDefeated() && !monster.isDefeated()) {
			if (userChoice.equals("1")) {

				playerAttack(monster);
				if (monster.isDefeated()) {				
					System.out.printf("Congratulation. You defeated %s. \n", monster.getName());
					
					if(isVictorious()) {
						System.out.println("Congratuation. You become the king of Lordo land!!!");
						return;
					}					
					
					player.healthResetAfterBattle(playerInitialHP);
					System.out.printf("Your HP has been restored to %d HP. \n", player.getHitPoints());
					System.out.println("Returning to the main room.");
					displayMenu();
					return;
				}

				monsterAttack(monster);
				if (player.isDefeated()) {
					System.out.printf("You have been defeated by %s. \n", monster.getName());
					return;
				}
				displayOptions(monster);
				userChoice = kb.nextLine();
			}

			if (userChoice.equals("2")) {
				if (Math.random() <= monster.getPercentageToEscape()) {
					playerRunAway(monster);
					return;
				} else {
					System.out.println("You have failed to escape!");
					monsterAttack(monster);
					if (player.isDefeated()) {
						System.out.printf("You have been defeated by %s. \n", monster.getName());
						return;
					}
					displayOptions(monster);
					userChoice = kb.nextLine();
				}

			}
			
			if(!userChoice.equals("1")&&!userChoice.equals("2")) {
				System.out.println("Invalid choice.");
				displayOptions(monster);
				userChoice = kb.nextLine();
			}
		}

	}

	public void playerAttack(Monster monster) {
		if (Math.random() <= monster.getDodgeChance()) {
			// monster dodge
			System.out.printf("You attack, but the monster %s(%d) dodged. \n", monster.getName(),
					monster.getHitPoints());
		} else {
			monster.takeDamage(player.attackMonster());
			if (monster.getHitPoints() <= 0) {
				monster.setHitPoints(0);
			}
			System.out.printf("You deal %d to the %s(%d). \n", player.attackMonster(), monster.getName(),
					monster.getHitPoints());
		}
	}

	public void monsterAttack(Monster monster) {
		if (Math.random() <= player.getDodgeChance()) {
			// monster dodge
			System.out.printf("The monster %s attacks, and you(%d) sucessfully dodged. \n", monster.getName(),
					player.getHitPoints());
		} else {
			player.takeDamage(monster.dealDamage());
			if (player.getHitPoints() <= 0) {
				player.setHitPoints(0);
			}
			System.out.printf("The monster %s deals %d to you(%d). \n", monster.getName(), monster.dealDamage(),
					player.getHitPoints());
		}
	}

	public void displayOptions(Monster monster) {
		System.out.println();
		System.out.printf("1. Attack the monster %s. \n", monster.getName());
		System.out.printf("2. Attempt to runaway with successful chance %.0f%%. \n",
				monster.getPercentageToEscape() * 100);
	}

	public void playerRunAway(Monster monster) {
		System.out.printf("You have successfully run away from the monster %s. \n", monster.getName());
		player.healthResetAfterBattle(playerInitialHP);
		System.out.printf("Your HP has been restored to %d HP. \n", player.getHitPoints());
		System.out.println("Returning to the main room.");
		displayMenu();
	}

	
	
	// BREAK UNTIL 4:35PM
	
	// End of the GameApp class
	
	
}
