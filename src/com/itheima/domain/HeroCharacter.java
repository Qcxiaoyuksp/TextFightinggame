package com.itheima.domain;

import java.util.ArrayList;

// 我方人物角色
public class HeroCharacter extends Character{
    public int MP;
    public int maxMP;
    public ArrayList<String> skillList;  // 技能列表
    public ArrayList<Consumable> packageList;  // 人物背包放于装当前的消耗品

    public HeroCharacter() {
        super();
        skillList = new ArrayList<>();
        packageList = new ArrayList<>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense, int MP) {
        super(name, HP, attack, defense);
        this.MP = MP;
        this.maxMP = MP;
        skillList = new ArrayList<>();
        packageList = new ArrayList<>();
    }

    // 遍历技能列表
    public String showSkill() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(skillList.get(i));
            if (i != skillList.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public String show() {
        return name + "[当前生命: " + HP + "/" + maxHP + ", 当前蓝量: " + MP + "/" + maxMP + ", 攻击: " + attack + ", 防御: " + defense + "]";
    }
}
