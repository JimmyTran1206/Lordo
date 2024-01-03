package com.skilldistillery.entities;

public class Player extends GameCharacter {
	private String name;
	
	public Player(int hitPoints, int attack, double dodgeChance, String name) {
		super(hitPoints, attack, dodgeChance);
		this.name=name;
	}
	public void healthResetAfterBattle(int initialHitPoint) {
		this.setHitPoints(initialHitPoint);
	}
	
	public int attackMonster() {
		int damage= dealDamage();
		return damage;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
