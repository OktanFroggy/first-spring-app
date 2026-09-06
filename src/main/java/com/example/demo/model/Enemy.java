package com.example.demo.model;

public class Enemy {
    private String name;
    private int hp;
    private final int maxHp;
    private final int minDamage;
    private final int maxDamage;
    private int armor;

    public Enemy(String name, int maxHp, int minDamage, int maxDamage, int armor) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.armor = armor;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getArmor() { return armor; }

    public int attack() {
        return (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
    }

    public int takeDamage(int incomingDamage) {
        // Урон уменьшается на значение брони, но не может быть меньше 1
        int actualDamage = Math.max(1, incomingDamage - this.armor);
        this.hp -= actualDamage;
        if (this.hp < 0) this.hp = 0;
        return actualDamage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void reset() {
        this.hp = maxHp;
    }
}