package itemStudent;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class userManageSystem {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(true){
            Menu01();
            int userchoice = sc.nextInt();
            switch (userchoice){
                case 1 -> logUser(list); // 登录
                case 2 -> register(list); // 注册
                case 3 -> forgetPassword(list); // 忘记密码
                case 4 -> System.exit(0);
            }
        }
    }
    // 登陆前菜单
    public static void Menu01(){
        System.out.println("欢迎来到学生管理系统");
        System.out.println("1：登陆");
        System.out.println("2：注册");
        System.out.println("3：忘记密码");
        System.out.println("4: 退出");
        System.out.print("请选择操作: ");
    }

    // 忘记密码
    public static void forgetPassword(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("请输入用户名： ");
            String username = sc.next();
            if(contain(list,username)){
                while(true){
                    System.out.print("请输入身份证号: ");
                    String useridcard = sc.next();
                    System.out.print("请输入手机号：");
                    String phoneNumber = sc.next();
                    // 对手机号和身份证号判断 若不符合 就账号信息不匹配 修改失败 直接退出
                    if(matchIdard(list,useridcard) && matchPhoneNumber(list,phoneNumber)){
                        // 账号信息匹配 提示输入密码进行修改
                        System.out.print("账号信息匹配,请输入要修改的密码: ");
                        while(true){
                            // 密码也采用两次判断 若两次输入密码一直就进行修改 否则就提示用户重新输入
                            String userpassword = sc.next();
                            System.out.print("请再输入一次密码: ");
                            String userpassword2 = sc.next();
                            if(userpassword.equals(userpassword2)){
                                System.out.println("密码修改成功!");
                                list.get(searchUser(list,username)).setPassword(userpassword);
                                return;
                            }else{
                                System.out.print("两次密码输入的不一致,请重新输入: ");
                            }
                        }
                    }else{
                        System.out.println("账号信息不匹配，修改失败!");
                    }
                }
            }else{
                System.out.println("用户名不存在!");
            }
            System.out.println("是否返回?");
            System.out.println("1:是");
            System.out.println("2:否");
            System.out.print("请输入你的选择: ");

            int userchoice = sc.nextInt();
            if(userchoice == 1){
                break;
            }
        }
    }

    // 查找对应的索引
    public static int searchUser(ArrayList<User> list,String username){
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    // 判断身份证
    public static boolean matchIdard(ArrayList<User> list,String useridcard){
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getIdcard().equals(useridcard)){
                return true;
            }
        }
        return false;
    }
    // 判断手机号
    public static boolean matchPhoneNumber(ArrayList<User> list,String userPhone){
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getPhonenumber().equals(userPhone)){
                return true;
            }
        }
        return false;
    }

    // 登录功能
    public static void logUser(ArrayList<User> list){
        // 读取用户名并且判断是否在数据中存在
        Scanner sc = new Scanner(System.in);
        int count = 0;
        // 先判断验证码 再判断用户名和密码 试错机会三次
        // 判断用户名是否存在
        while(true){
            System.out.print("请输入用户名: ");
            String userName = sc.next();
            System.out.print("请输入密码: ");
            String password = sc.next();
            // 先判断验证码
            String captcha = captcha();
            System.out.println("验证码: " + captcha);
            System.out.print("请输入验证码： ");
            String usercaptcha = sc.next();
            if(match_captcha(captcha,usercaptcha)){
                // 判断用户名
                if(contain(list,userName)){
                    // 判断密码
                    if(matchPassword(list,password)){
                        System.out.println("登陆成功");
                        // 调用菜单2 然后使用对应的功能 对应功能也封装到一个方法中
                        studentManageSystem();
                    }else{
                        System.out.println("用户名或密码错误!");
                        count++;
                    }
                    if(count == 3){
                        System.out.println("登录次数过多，请稍后尝试!");
                        break;
                    }
                }else{
                    System.out.println("用户名未注册，请先注册!");
                    break;
                }
            }else{
                System.out.println("验证码不符!");
            }
        }
    }

    // 验证用户名的功能
//    public static boolean isExistUserName(ArrayList<User> list,String username){
//        for(int i = 0;i < list.size();i++){
//            if(list.get(i).getUsername().equals(username)){
//                return true;
//            }
//        }
//        return false;
//    }

    // 判断验证码和输入是否符合
    public static boolean match_captcha(String captcha,String usercaptcha){
        return captcha.equalsIgnoreCase(usercaptcha);
    }

    // 密码验证
    public static boolean matchPassword(ArrayList<User> list,String password){
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    // 验证码生成
    public static String captcha(){
        char[] c = new char[52];
        for (int i = 0; i < c.length; i++) {
            if(i < 26){
                c[i] = (char)('a' + i);
            }else{
                c[i] = (char)('A' + i - 26);
            }
        }
        StringBuilder capt = new StringBuilder();
        Random r = new Random();
        for(int i = 0;i < 4;i++){
            capt.append(c[r.nextInt(52)]); // 添加字母
        }
        // 添加数字
        capt.append(r.nextInt(10));
        // 打乱顺序
        String cap = capt.toString();
        char[] arr = cap.toCharArray();
        for(int i = 0;i < 5;i++){
            int index = r.nextInt(5);
            char temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
//        String captacha = new String(arr);
//        return captacha;
        return new String(arr);
    }

    // 注册的功能
    public static void register(ArrayList<User> list){
        //ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要注册的用户名: ");
        while(true){
            User u = new User();
            String name = sc.next();
            if(contain(list,name)){
                System.out.print("用户名已存在，请重新输入: ");
            }else if(name.length() < 3 || name.length() > 15){
                System.out.print("用户名长度过短或者过长,请重新输入:");
            }else{
                System.out.println("用户名添加成功!");
                u.setUsername(name);
                System.out.print("请输入密码(含字母和数字): ");
                while(true){
                    String password = sc.next();
                    // 判断密码组合是否是字母和数字结合 用方法 遍历统计判断
                    if(isRightPassword(password)){
                        System.out.print("请再输入一次密码: ");
                        while(true){
                            String password2 = sc.next();
                            if(password2.equals(password)){
                                u.setPassword(password);
                                break;
                            }else{
                                System.out.print("两次密码不符合，请重新输入: ");
                            }
                        }
                        // 只有密码通过了才能到这里
                        System.out.println("密码添加成功!");
                        System.out.print("请输入身份证号码: ");
                        while(true){
                            String idCard = sc.next();
                            // 判断身份证
                            if(isRightIdcard(idCard)){
                                u.setIdcard(idCard);
                                break;
                            }
                        }
                        System.out.print("请输入手机号: ");
                        while (true){
                            // 判断手机号
                            String Phone = sc.next();
                            if(checkPhoneNumber(Phone)){
                                u.setPhonenumber(Phone);
                                break;
                            }
                        }
                        break;
                    }
                }
                System.out.println("用户注册成功! ");
                list.add(u);
                break;
            }
        }
    }

    // 判断用户名是否是唯一性
    public static boolean contain(ArrayList<User> list,String name){
//        for (int i = 0; i < list.size(); i++) {
//            if(list.get(i).getUsername().equals(name)){
//                return true;
//            }
//        }
//        return false;
        return searchUser(list,name) >= 0;
    }

    // 判断密码
    public static boolean isRightPassword(String password){
        int bigCount = 0,smallCount = 0,numCount = 0,otherCount = 0;
        for(int i = 0;i < password.length();i++){
            char c = password.charAt(i);
            if(c <= 'z' && c >= 'a'){
                smallCount++;
            }else if(c <= 'Z' && c >= 'A'){
                bigCount++;
            }else if(c <= '9' && c >= '0'){
                numCount++;
            }
        }
        if((smallCount + bigCount > 0) && numCount > 0){
            return true;
        }else{
            System.out.print("密码不符合规则，请重新输入: ");
            return false;
        }
    }

    // 判断身份证
    public static boolean isRightIdcard(String idcard){
        if(idcard.length() != 18){
            System.out.print("所输入的身份证过长或者过短，请重新输入: ");
            return false;
        }
        if(idcard.charAt(0) != 0){
            for(int i = 0;i < idcard.length() - 1;i++){
                if(idcard.charAt(i) <= '9' && idcard.charAt(i) >= '0'){
                    continue;
                }else{
                    System.out.println("前十七位必须为数字,请重新输入: ");
                    return false;
                }
            }
            if((idcard.charAt(17) <= '9' && idcard.charAt(17) >= '0') || idcard.charAt(17) == 'x' || idcard.charAt(17) == 'X'){
                System.out.println("身份证添加成功!");
                return true;
            }else{
                System.out.print("最后一个要么是数字要么是x或者X,请重新输入: ");
            }
        }else{
            System.out.println("不能用0开头,请重新输入: ");
        }
        return false;
    }

    // 判断手机号
    public static boolean checkPhoneNumber(String Phone){
        if(Phone.length() == 11){
           if(Phone.charAt(0) != '0'){
               // 遍历查看是否全为数字
               if(allNumber(Phone)){
                   System.out.println("手机号添加成功!");
                   return true;
               }else{
                   System.out.println("手机号必须为纯数字，请重新输入: ");
               }
           }else{
               System.out.println("不能以0开头,请重新输入: ");
           }
        }else{
            System.out.println("手机号码位数不对,请输入正确的手机号: ");
        }
        return false;
    }

    // 判断手机号是否都是为数字
    public static boolean allNumber(String Phone){
        for(int i = 0;i < Phone.length();i++){
            if(Phone.charAt(i) > '9' || Phone.charAt(i) < '0'){
                return false;
            }
        }
        return true;
    }

    // 学生管理系统
// 菜单2:登录之后的 管理系统菜单
    public static void Menu02(){
        System.out.println("-------------欢迎来到星叶学生管理系统----------------");
        System.out.println("|---------------     1：添加学生     ----------------|");
        System.out.println("|---------------     2：删除学生     ----------------|");
        System.out.println("|---------------     3：修改学生     ----------------|");
        System.out.println("|---------------     4：查询学生     ----------------|");
        System.out.println("|---------------     5： 退  出      ----------------|");
        System.out.println("|------------------------------------------------------|");
        System.out.print("请输入你的选择: ");
    }

    // 学生管理系统选择判断
    public static void studentManageSystem(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> list2 = new ArrayList<>();
        loop : while(true){
            Menu02(); // 菜单功能
            int choice = sc.nextInt();
            switch (choice){
                case 1 -> addStudentInfo(list2); // 添加
                case 2 -> deleteStudentInfo(list2); // 删除 : 因为学号是唯一性的 所以根据学号进行删除
                case 3 -> changeStudentInfo(list2); // 修改 ： 通过ID查找 然后进行更改相对应的ID的学生的信息
                case 4 -> searchStudentInfo(list2); // 查询
                case 5 -> {
                    System.out.println("感谢你的使用!");
                    System.exit(0);
                    // 或者System.exit(0) 作用相当于关闭软件 那么 所有代码都停了
                }
//                case 6 -> showStudnetInfo(list);// 测试模块
                default -> System.out.println("没有这个选项，请你重新输入!");
            }
        }
    }

    // 添加
    public static void addStudentInfo(ArrayList<Student> list){
        System.out.print("请输入你要添加的学生数量：");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        for (int i = 0; i < num;) {
            Student stu = new Student();
            System.out.print("请输入第" + (i + 1) + "个学生ID: ");
            stu.setId(sc.nextInt());
            if(uniqueId(list,stu.getId())){
                System.out.println("你输入的ID已存在，请重新输入");
                continue;
            }
            System.out.print("请输入第" + (i + 1) + "个学生的姓名: ");
            stu.setName(sc.next());
            System.out.print("请输入第" + (i + 1) + "个学生的年龄：");
            stu.setAge(sc.nextInt());
            System.out.print("请输入第" + (i + 1) + "个学生的家庭住址：");
            stu.setHomeaddress(sc.next());
            i++;

            list.add(stu);
            System.out.println("添加成功!");
        }
    }

    // 判断ID的唯一性
    public static boolean uniqueId(ArrayList<Student> list,int id){
        // 循环判断
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == id){
                return true;
            }
        }
        return false;
    }

    // 删除学生
    public static void deleteStudentInfo(ArrayList<Student> list){
        if(list.size() == 0){
            System.out.println("当前列表为空！");
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.print("请输入你要删除的学生ID：");
            int id = sc.nextInt();
            for (int i = 0; i < list.size(); i++) {
                // 查找ID 看看是否存在
                if(i == list.size() - 1 && list.get(i).getId() != id){
                    System.out.println("你要查找的学生ID不存在!");
                }
                if(list.get(i).getId() == id){
                    list.remove(i); // 对应的下标删除
                    System.out.println("删除成功!");
                }
            }
        }
    }

    // 修改信息
    public static void changeStudentInfo(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("该列表为空!");
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入你要修改学生信息的ID: ");
            int id = sc.nextInt();
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1 && list.get(i).getId() != id) {
                    System.out.println("该ID不存在!");
                }
                if (list.get(i).getId() == id) {
                    System.out.print("请输入修改为的名字: ");
                    String newname = sc.next();
                    System.out.print("请输入修改为的年龄: ");
                    int newage = sc.nextInt();
                    System.out.print("请输入修改为的家庭地址: ");
                    String newAddress = sc.next();

                    list.get(i).setName(newname);
                    list.get(i).setAge(newage);
                    list.get(i).setHomeaddress(newAddress);

                    System.out.println("成功修改学生信息！");
                }
            }
        }
    }

    // 查询系统
    public static void searchStudentInfo(ArrayList<Student> list){
        if(list.size() == 0){
            System.out.println("列表为空!");
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.print("请输入你要查询的ID：");
            int id = sc.nextInt();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId() == id){
                    // 输出信息
                    System.out.println("id\t姓名\t年龄\t家庭住址");
                    System.out.println(list.get(i).getId() + "\t" + list.get(i).getName() + "\t" +
                            list.get(i).getAge() + "\t\t" + list.get(i).getHomeaddress() );
                    break;
                }
                if(i == list.size() - 1 && list.get(i).getId() != id){
                    System.out.println("你所查询的ID不存在!");
                }
            }
        }
    }

    public static boolean onlyUserName(ArrayList<Student> list,String name){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}