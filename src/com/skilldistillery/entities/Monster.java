package com.skilldistillery.entities;

public class Monster extends GameCharacter {
	private String name;
	private double percentageToEscape;

	public Monster(int hitPoints, int attack, double dodgeChance, String name, double percentageToEscape) {
		super(hitPoints, attack, dodgeChance);
		this.name = name;
		this.percentageToEscape = percentageToEscape;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentageToEscape() {
		return percentageToEscape;
	}

	public void setPercentageToEscape(double percentageToEscape) {
		this.percentageToEscape = percentageToEscape;
	}

}
