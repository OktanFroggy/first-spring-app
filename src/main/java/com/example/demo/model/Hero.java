package com.example.demo.model;

public class Hero {
    private String name;
    private int hp;
    private final int maxHp;
    private int minDamage;
    private int maxDamage;
    private int baseArmor;
    private boolean isDefending; // Флаг стойки защиты

    public Hero(String name) {
        this.name = name;
        this.maxHp = 100;
        this.hp = 100;
        this.minDamage = 12;
        this.maxDamage = 22;
        this.baseArmor = 3;
        this.isDefending = false;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getBaseArmor() { return baseArmor; }
    public boolean isDefending() { return isDefending; }

    public void setDefending(boolean defending) {
        this.isDefending = defending;
    }

    public int attack() {
        return (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
    }

    public int heal() {
        int healAmount = 20;
        this.hp = Math.min(this.maxHp, this.hp + healAmount);
        return healAmount;
    }

    public int takeDamage(int incomingDamage) {
        // Если герой встал в стойку защиты, броня увеличивается вдвое
        int currentArmor = isDefending ? baseArmor * 2 + 5 : baseArmor;
        int actualDamage = Math.max(1, incomingDamage - currentArmor);

        this.hp -= actualDamage;
        if (this.hp < 0) this.hp = 0;

        // Снимаем стойку защиты после получения урона
        this.isDefending = false;

        return actualDamage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void reset() {
        this.hp = maxHp;
        this.isDefending = false;
    }
}