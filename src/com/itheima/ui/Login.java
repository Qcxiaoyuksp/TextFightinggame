package com.itheima.ui;

import com.itheima.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    private final Scanner sc = new Scanner(System.in);

    // 这个方法是登录注册的主页面（以控制台的形式）
    public void start() {
        ArrayList<User> list = new ArrayList<>();
        // command + option + T : 选择对应的语句包裹代码
        while (true) {
            System.out.println("┌───────────────────────────┐");
            System.out.println("   🎮 欢迎来到文字格斗游戏 🎮    ");
            System.out.println("└───────────────────────────┘");
            System.out.println("请选择操作：1登录  2注册  3忘记密码  4退出");

            String choose = sc.next();

            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("感谢使用，再见！");
                    System.exit(0);
                }
                default -> System.out.println("无效的选择，请重新输入");
            }
        }
    }

    private void forgetPassword(ArrayList<User> list) {
        System.out.println("请输入用户名：");
        String username = sc.next();
        if (!contains(list, username)) {
            System.out.println("用户名不存在，请先注册～");
            return;
        }
        int index = findIndex(list, username);
        User u = list.get(index);

        System.out.println("请输入手机号验证：");
        String phone = sc.next();
        if (u.getPhone() == null || !u.getPhone().equals(phone)) {
            System.out.println("手机号验证失败");
            return;
        }

        System.out.print("手机号正确");
        u.setPassword(inputPasswordWithConfirm("请输入新密码：", "请再次输入新密码："));
        System.out.println("密码修改成功！");
    }


    // 注册操作
    public void register(ArrayList<User> list) {
        User u = new User();
        String username;

        // 1. 验证输入用户名
        // 先验证格式是否正确，再验证是否唯一
        // 先判断异常的数据，剩下的就是正确的数据
        // command + option + T
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.next();

            // 1. 长度必须在3～16位
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名长度不符合要求，必须在3～16位");
                continue;
            }
            // 2. 只能由字母、数字组成，不能是纯数字
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母、数字组成，且不能是纯数字");
                continue;
            }
            // 3. 用户名唯一
            if (contains(list, username)) {
                System.out.println("用户名已存在，请重新输入～");
                continue;
            }

            u.setUsername(username);
            break;
        }

        // 2. 录入密码
        u.setPassword(inputPasswordWithConfirm("请输入密码：", "请再次输入密码："));

        // 3. 录入手机号
        while (true) {
            System.out.println("请输入手机号：");
            String phone = sc.next();
            if (!checkPhone(phone)) {
                System.out.println("手机号格式错误(长度必须11位，并只能以数字1开头)，请重新输入～");
                continue;
            }

            u.setPhone(phone);
            break;
        }

        // 4. 将用户信息添加到集合中
        list.add(u);
        System.out.println("注册成功！欢迎 " + u.getUsername() + " 加入～");
    }


    // 登录操作
    public void login(ArrayList<User> list) {
        // 判断用户名是否存在

        // 1. 键盘录入用户名
        System.out.println("请输入用户名：");
        String username = sc.next();

        // 2. 验证用户名是否存在
        if (!contains(list, username)) {
            System.out.println("用户名" + username + "不存在，请先注册～");
            return;
        }

        // 3. 禁用账户，提示联系客服
        int index = findIndex(list, username);
        User u = list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户" + username + "已禁用，请联系客服XXX：XXX-XXXX-XXXX");
            return;
        }

        // 4. 输入密码和验证码
        String rightPassword = u.getPassword();
        int i = 0;
        while (i < 3) {
            System.out.println("请输入密码：");
            String password = sc.next();

            // 验证码
            while (true) {
                String rightCode = getCode();
                System.out.println("正确的验证码为：" + rightCode);

                System.out.println("请输入验证码：");
                String code = sc.next();

                if (rightCode.equalsIgnoreCase(code)) {
                    System.out.println("验证码正确~");
                    break;
                } else {
                    System.out.println("验证码错误，登录失败！");
                }
            }

            if (rightPassword.equals(password)) {
                System.out.println("登录成功，游戏启动！");
                // 调用方法启动游戏
                FightingGame fg = new FightingGame();
                fg.gameStart(username);
                break;
            } else {
                System.out.println("登录失败，密码输入错误～");
                if (i == 2) {
                    u.setStatus(false);
                    System.out.println("用户" + username + "已被禁用，请联系客服XXX：XXX-XXXX-XXXX");
                    return;
                } else {
                    System.out.println("密码错误，你还有" + (2 - i) + "次机会");
                }
            }

            i++;
        }
    }


    // 在集合中找username所在的索引
    public int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }


    // 判断用户名在集合中是否包含
    public boolean contains(ArrayList<User> list, String username) {
        // list.fori
        for (User u : list) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    // 统计字符串中字母，数字和其他字符各有多少个
    public int[] getCount(String userInfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }

        return new int[]{charCount, numCount, otherCount};
    }


    // 校验用户名是否符合要求
    public boolean checkUsername(String username) {
        int[] arr = getCount(username);

        return arr[0] > 0 && arr[2] == 0;
    }


    // 校验手机号是否符合要求
    public boolean checkPhone(String phone) {
        return phone.charAt(0) == '1' && checkLen(11, 11, phone);
    }


    // 校验密码是否符合要求
    public boolean checkPassword(String password) {
        int[] arr = getCount(password);

        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }


    // 输入密码并做长度、字符和二次确认校验
    private String inputPasswordWithConfirm(String firstPrompt, String secondPrompt) {
        while (true) {
            System.out.println(firstPrompt);
            String password1 = sc.next();
            // 长度3～8
            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度不符合要求，必须在3～8位");
                continue;
            }
            // 只能是字母和数字的组成，不能有其他字母。
            if (!checkPassword(password1)) {
                System.out.println("密码只能是字母和数字的组成，不能有其他字符");
                continue;
            }

            System.out.println(secondPrompt);
            String password2 = sc.next();
            // 校验两次密码是否一致
            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致，请重新输入～");
                continue;
            }

            return password1;
        }
    }


    // 判断字符串的长度是否在指定的范围之内
    public boolean checkLen(int minLen, int maxLen, String str) {
        return str.length() >= minLen && str.length() <= maxLen;
    }


    // 获取验证码
    public static String getCode() {
        // 1. 把所有的大写小写字母放到一个容器里
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        // 2. 从集合中随机抽取字母（4次）
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            sb.append(list.get(index));
        }

        // 3. 生成一个随机的数字
        sb.append(r.nextInt(10));

        // 4. 数字的位置随机
        char[] arr = sb.toString().toCharArray();
        int i = r.nextInt(arr.length);
        char tmp = arr[i];
        arr[i] = arr[arr.length - 1];
        arr[arr.length - 1] = tmp;

        // 5. 把数组转换成字符串
        return new String(arr);
    }
}
