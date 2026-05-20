package com.itheima.domain;

// 敌人人物角色
public class EnemyCharacter extends Character{
    public String skill;  // 技能
    public boolean defending;  // 当前人物是否拥有减少伤害的状态

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    @Override
    public void takeDamage(int damage) {
        // 如果处于防御状态，伤害减半
        if (defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
            // 防御状态只能持续一个回合
            defending = false;
        }

        // 调用父类方法扣除血量
        super.takeDamage(damage);
    }
}
