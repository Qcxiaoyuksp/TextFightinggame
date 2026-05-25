package com.itheima.ui;

import com.itheima.domain.EnemyCharacter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    // 启动游戏
    public void gameStart(String username) {
        // 1. 显示游戏的标题
        System.out.println("┌───────────────────────────────────┐");
        System.out.println("   🎮 " + username + "欢迎来到文字格斗游戏 🎮    ");
        System.out.println("└───────────────────────────────────┘");

        // 2. 创建玩家角色
        HeroCharacter player = createPlayerCharacter(username);

        // 3. 显示创建角色的信息和技能
        System.out.println("角色创建成功！");
        System.out.println("⭐️ 初始属性：" + player.show());
        System.out.println("⭐️ 拥有技能：" + player.showSkill());

        // 4. 创建多个敌人列表
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 58, "火球术"));

        // 5. 准备战斗（依次跟多个敌人战斗）
        int count = 1;  // 战斗的轮数
        int wins = 0;  // 记录胜利了几场

        // 战斗开始
        while (player.isAlive()) {
            // 1. 重置敌人的属性
            if (wins != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP += 10;
                    c.HP = c.maxHP;
                    c.attack += 3;
                    c.defense += 2;
                    c.defending = false;
                }
            }

            // 2. 随机选择敌人
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println(enemy.show());

            // 3. 战斗开始
            System.out.println("==========================================");
            System.out.println("⚔️ 第" + count + "场战斗开始！对手：" + enemy.name);

            int round = 1;
            while (player.isAlive()) {
                System.out.println("----------------------------------------");
                System.out.println("⚔️ 第" + round + "回合开始！");
                System.out.println(getHealthBar(player.name, player.HP, player.maxHP));
                System.out.println(getHealthBar(enemy.name, enemy.HP, enemy.maxHP));

                // 4. 玩家回合: 选择行动
                playerTurn(player, enemy);

                // 判断敌人是否被击败
                if (!enemy.isAlive()) {
                    System.out.println("🎉 你击败了" + enemy.name + "！");
                    wins++;
                    break;
                }

                System.exit(0);

            }

        }

    }


    // 打印血条
    public String getHealthBar(String name, int HP, int maxHP) {
        int barLength = 20;
        int filled = (int) (((double) HP / maxHP) * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                sb.append("█");
            } else {
                sb.append(" ");
            }
        }

        sb.append("] ").append(HP).append("/").append(maxHP).append(" HP");

        return sb.toString();
    }


    // 用来创建玩家角色
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("创建您的角色：");
        System.out.println("您的角色为：" + username);

        // 属性分配
        int points = 20;

        // 提示
        System.out.println("请分配属性点（共20点）：");
        System.out.println("1. 生命值（每点 + 10HP）");
        System.out.println("2. 攻击力（每点 + 2ATK）");
        System.out.println("3. 防御力（每点 + 1DEF）");

        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻击力", "防御力"};
        int[] values = new int[3];

        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到 " + attributes[i] + "(剩余点数：" + points + ")：");
            int input = sc.nextInt();

            if (input < 0) {
                System.out.println("无效输入！默认分配0点");
                input = 0;
            }
            if (input > points) {
                System.out.println("属性点不足！剩余属性点全部分配到：" + attributes[i]);
                input = points;
            }

            points -= input;
            values[i] = input;
        }

        // 创建玩家角色的对象
        HeroCharacter player = new HeroCharacter(
                username,
                100 + values[0] * 10,
                10 + values[1] * 2,
                0 + values[2] * 1
        );

        // 添加玩家的技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        return player;
    }


    // 玩家回合
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击");
        System.out.println("3. 生命汲取");
        System.out.println("选择行动(1 - 3): ");
        Scanner sc = new Scanner(System.in);
        String choose = sc.next();
        switch (choose) {
            default:
                System.out.println("没有这个操作，默认使用普通攻击");
            case "1":
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔️ 你对" + enemy.name + "使用了普通攻击, 造成" + damage1 + "点伤害!");
                enemy.takeDamage(damage1);
                break;
            case "2":
                if (player.HP > 10) {
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int)(player.attack * 1.8), enemy.defense);
                    System.out.println("💥 消耗10HP，你对" + enemy.name + "使用了强力一击，造成" + damage2 + "点伤害！");
                    enemy.takeDamage(damage2);
                } else {
                    System.out.println("你的生命值不足，攻击失败～");
                }
                break;
            case "3":
                if (player.HP > 10) {
                    player.takeDamage(10);
                    Random r = new Random();
                    int healHP = r.nextInt(21);
                    player.heal(healHP);
                    System.out.println("❤️ 消耗10HP, 你使用了生命汲取，恢复了" + healHP + "点生命值!");
                } else {
                    System.out.println("你的生命值不足，恢复生命失败～");
                }
                break;
        }
    }


    // 计算双方战斗时造成的伤害
    public int calculateDamage(int attack, int defense) {
        int damage = attack - defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }
}
