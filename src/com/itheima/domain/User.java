package com.itheima.domain;

import java.util.Random;

public class User {
    // id、用户名、密码、状态
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User() {
        id = createID();
        status = true;
    }

    public User(String username, String password) {
        id = createID();
        this.username = username;
        this.password = password;
        status = true;
    }

    // id用户无法设置，是自动生成的，格式为：heima + 5位随机数字
    public String createID() {
        StringBuilder sb = new StringBuilder("heima");

        Random r = new Random();
        // 5.fori
        for (int i = 0; i < 5; i++) {
            // command + option + v
            int num = r.nextInt(10);
            sb.append(num);
        }

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
