package com.example.demo.model;

public class Hero {

    private int hp;
    private final int maxHp;

    public Hero () {
        this.maxHp = 100;
        this.hp = 100;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }
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