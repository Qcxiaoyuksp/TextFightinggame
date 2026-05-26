package com.itheima.domain;

// 回血道具
public class Consumable {
    String name;  // 道具名称
    int num;  // 恢复血量的数值

    public  Consumable() {}
    public Consumable(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
