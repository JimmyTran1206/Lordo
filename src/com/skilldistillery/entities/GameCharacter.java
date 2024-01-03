package com.skilldistillery.entities;

public class GameCharacter {
	private int hitPoints;
	private int attack;
	private double dodgeChance;
	
	public GameCharacter() {
		super();
	}

	public GameCharacter(int hitPoints, int attack, double dodgeChance) {
		this.hitPoints = hitPoints;
		this.attack = attack;
		this.dodgeChance=dodgeChance;
	}

	public double getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(double dodgeChance) {
		this.dodgeChance = dodgeChance;
	}

	public int dealDamage() {
		return attack; // make random from 0 to attack later
	}

	public void takeDamage(int damage) {
		hitPoints -= damage;
	}

	public boolean isDefeated() {
		return hitPoints <= 0 ? true : false;
	}

	public int getHitPoints() {
		return hitPoints;
	}
	
	public void setHitPoints(int hp) {
		this.hitPoints=hp;
	}

}
