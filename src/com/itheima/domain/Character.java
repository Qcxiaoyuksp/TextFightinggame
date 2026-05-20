package com.itheima.domain;

public class Character {
    public String name;  //  姓名
    public int HP;  // 生命值
    public int maxHP;  // 最大生命值
    public int attack;  // 攻击力
    public int defense;  // 防御力

    public Character() {
    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    // 1. 判断当前角色是否还活着
    public boolean isAlive() {
        return HP > 0;
    }

    // 2. 恢复血量
    public void heal(int amount) {
        HP += amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    // 3. 收到伤害
    public void takeDamage(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }

    // 4. 展示人物属性
    public String show() {
        return name + "[当前生命: " + HP + ", 攻击: " + attack + ", 防御: " + defense + "]";
    }
}
