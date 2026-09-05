package com.example.demo.model;

public class Enemy {

    private String name;
    private int hp;
    private final int maxHp;
    private final int maxDamage;
    private final int minDamage;

    public Enemy(String name, int maxHp, int maxDamage, int mindamage) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.minDamage = mindamage;
        this.maxDamage = maxDamage;
    }
    public String getName() {return name;}
    public int getHp() {return hp;}
    public int getMaxHp() {return maxHp;}
    public int getMaxDamage() {return maxDamage;}
    public int getMinDamage() {return minDamage;}

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (hp < 0) {
            this.hp = 0;
        }
    }
    public boolean isAlive() {
        return hp > 0;
    }
}
