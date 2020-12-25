package com.slatter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Slatter
 * @create 2020/3/24
 * @since 1.0.0
 */
public class Management {
    static void randGen(int n, String[] name, ArrayList<Employee> member) {                 //随机生成n个员工
        for (int i = 0; i < n; i++) {
            String sex;
            double num = Math.random() * 6;
            if (num >= 0 & num < 3)
                sex = "male";
            else
                sex = "female";

            int sal = 4000 + (int) (Math.random() * 2000);
            int allo = 200 + (int) (Math.random() * 300);
            int bo = 300 + (int) (Math.random() * 400);

            member.add(new Employee(i, name[i], sex, new Performance(sal, allo, bo)));    //添加进入员工信息库
        }
    }

    static void distribution(int n, ArrayList<Employee> em, ArrayList<Department> depart) {     //分配员工进入部门
        for (int i = 0; i < n; i++) {
            Employee e = em.get(i);
            int num = i % 5;
            switch (num) {
                case 0: {
                    e.setDepart(depart.get(0).name);
                    depart.get(0).addMember(e);
                    depart.get(0).setTotal();
                    break;
                }
                case 1: {
                    e.setDepart(depart.get(1).name);
                    depart.get(1).addMember(e);
                    depart.get(1).setTotal();
                    break;
                }
                case 2: {
                    e.setDepart(depart.get(2).name);
                    depart.get(2).addMember(e);
                    depart.get(2).setTotal();
                    break;
                }
                case 3: {
                    e.setDepart(depart.get(3).name);
                    depart.get(3).addMember(e);
                    depart.get(3).setTotal();
                    break;
                }
                case 4: {
                    e.setDepart(depart.get(4).name);
                    depart.get(4).addMember(e);
                    depart.get(4).setTotal();
                    break;
                }
                default:
                    break;
            }
        }
    }

    static void chooseManager(ArrayList<Department> depart) {               //选出各个部门负责人
        for (Department department : depart) {
            int num = (int) (Math.random() * 20);
            Employee m = department.members.get(num);                    //得到负责人
            department.setManager(m);
        }
    }

    static void searchEmployee(String name, ArrayList<Employee> e) {
        boolean flag = true;
        for (Employee employee : e)
            if (employee.getName().equals(name)) {
                flag = false;
                employee.showDetails();
                //不用break防止重名情况发生
            }
        if (flag)
            System.out.println("抱歉，本公司无此人");
    }

    static void checkDepart(Department depart) {
        System.out.println();
        Scanner s = new Scanner(System.in);
        loop1:
        while (true) {
            depart.departContent();
            int i = s.nextInt();
            switch (i) {
                case 1: {
                    depart.showDepartment();
                    break;
                }
                case 2: {
                    System.out.print("请输入所要查找员工姓名:");
                    if (s.hasNext())
                        depart.getMember(s.next());
                    break;
                }
                case 3: {
                    depart.showSortMembers();
                    break;
                }
                case 4: {
                    System.out.println("即将返回主菜单...\n");
                    break loop1;
                }
                default: {
                    System.out.println("输入有误，请重新输入\n");
                    break;
                }
            }
        }
    }

    static void printContent() {                     //打印交互页面
        System.out.println("请根据您的需求输入对应数字浏览数据");
        System.out.println("1.公司所有员工按业绩高低基本信息");
        System.out.println("2.查看指定员工详细信息");
        System.out.println("3.查看指定部门详细信息");
        System.out.println("4.退出");
    }

    public static void main(String[] args) {
        int num_people = 100;
        int num_department = 5;
        String[] fullname = {"Anna", "Amber", "Aurora", "Ada", "Adalia", "Alice", "Alexandra", "Adele", "Adonia", "Akili",
                "Bena", "Elizabeth", "Bonnie", "Bunny", "Bliss", "Caltha", "Carly", "Carol", "Chloe", "Cindy",
                "Crystal", "Cynthia", "Daisy", "Danica", "Dawn", "Diana", "Dior", "Dreama", "Delfina", "Echo",
                "Edana", "Eleanor", "Ester", "Emily", "Faith", "Faye", "Florence", "Freya", "Fronde", "Fronde",
                "Gelsey", "Gilana", "Githa", "Grace", "Gwen", "Halona", "Harriet", "Helen", "Hester", "Hope",
                "Iona", "Irma", "Isis", "Ivy", "Jelena", "Jewel", "Jessica", "Judy", "Julia", "Kara",
                "Kiran", "Kathy", "Kitty", "Lana", "Laura", "Leona", "Lida", "Maeve", "Mercia", "Mora",
                "Nadia", "Neva", "Nissa", "Nova", "Odele", "Olivia", "Oriel", "Pamela", "Petra", "Pythia",
                "Rachel", "Rasine", "Rose", "Sachi", "Serena", "Shirley", "Steven", "Talia", "Thea", "Tina",
                "Vera", "Victoria", "Vicky", "Violet", "Wendy", "Yonina", "Zeva", "Earl", "Edward", "Power",};

        ArrayList<Employee> employees = new ArrayList<>();               //员工信息库
        ArrayList<Department> departments = new ArrayList<>(num_department);        //部门库
        departments.add(0, new Department(101, "策划部"));
        departments.add(1, new Department(102, "开发部"));
        departments.add(2, new Department(103, "市场部"));
        departments.add(3, new Department(104, "统筹部"));
        departments.add(4, new Department(105, "管理部"));

        randGen(num_people, fullname, employees);                        //随机生成100位员工信息并入库
        distribution(num_people, employees, departments);                  //将100位员工分配进各个部门
        chooseManager(departments);                                      //设置各个部门负责人
        employees.sort((o1, o2) -> o2.getPer().getTotal() - o1.getPer().getTotal());    //根据员工业绩高低整理库

        Loop:
        while (true) {
            printContent();
            Scanner input = new Scanner(System.in);
            int num = input.nextInt();
            switch (num) {
                case 1: {
                    for (Employee employee : employees) {
                        employee.showEmployee();
                    }
                    System.out.println("即将返回主菜单...\n");
                    break;
                }
                case 2: {
                    System.out.print("请输入所要查找员工姓名:");
                    if (input.hasNext()) {
                        searchEmployee(input.next(), employees);
                        System.out.println("即将返回主菜单...\n");
                    }
                    break;
                }
                case 3: {
                    System.out.println("公司共有五个部门，请根据提示输入对应数字进入相应部门");
                    System.out.println("1.策划部    2.开发部    3.市场部    4.统筹部    5.管理部");
                    int i = input.nextInt();
                    boolean flag = true;
                    for (int j = 0; j < 5; j++) {
                        if (i == j + 1) {
                            flag = false;
                            checkDepart(departments.get(j));
                        }
                    }
                    if (flag) {
                        System.out.println("输入有误，返回主菜单\n");
                    }
                    break;
                }
                case 4: {
                    System.out.println("成功退出");
                    break Loop;
                }
                default: {
                    System.out.println("输入有误，请根据提示重新输入\n");
                    break;
                }
            }
        }
    }
}

class Employee {                                //员工类
    private final int id;                             //员工编号
    private final String name;                        //员工姓名
    private final String gender;                      //员工性别
    private final Performance per;                    //员工业绩
    private String depart;                  //员工所属部门

    public Employee(int id, String name, String gender, Performance per) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.per = per;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getName() {
        return name;
    }

    public Performance getPer() {
        return per;
    }

    public void showEmployee() {                 //显示员工的信息
        String msg = String.format("编号:%-5d 姓名:%-10s 性别:%-8s 业绩:%d", id, name, gender, per.getTotal());
        System.out.println(msg);
    }

    public void showPfm() {                       //显示员工的业绩
        System.out.print(String.format("姓名:%-10s", name));
        per.showPerformance();
    }

    public void showDetails() {
        String msg = String.format("编号:%-5d 姓名:%-10s 性别:%-8s 所属部门:%-6s 本月业绩:%-6d 薪水:%-6d 津贴:%-5d 奖金:%-4d", id, name, gender, depart, per.getTotal(), per.getSalary(), per.getAllowance(), per.getBonus());
        System.out.println(msg);
    }
}

class Performance {                          //业绩类
    private final int salary;                      //薪水
    private final int allowance;                   //津贴
    private final int bonus;                       //奖金
    private final int total;                       //薪资总和

    public Performance(int salary, int allowance, int bonus) {
        this.salary = salary;
        this.allowance = allowance;
        this.bonus = bonus;
        this.total = salary + allowance + bonus;
    }

    public int getSalary() {
        return salary;
    }

    public int getAllowance() {
        return allowance;
    }

    public int getBonus() {
        return bonus;
    }

    public int getTotal() {
        return total;
    }

    public void showPerformance() {
        String msg = String.format("薪水:%-6d 津贴:%-5d 奖金:%-4d 薪资总和:%d", salary, allowance, bonus, total);
        System.out.println(msg);
    }
}

class Department { //部门类
    int id;                                                         //部门编号
    String name;                                                    //部门名称
    Employee manager;                                               //部门负责人
    int total = 0;                                                  //部门总人数
    ArrayList<Employee> members = new ArrayList<>();        //部门成员

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void setTotal() {
        this.total += 1;
    }

    public void addMember(Employee e) {                     //增加某个部门成员并设置相应信息
        members.add(e);
    }

    public void showDepartment() {                          //显示部门信息
        String msg = String.format("部门编号:%-5d 名称:%-6s 负责人:%-10s 员工数:%d", id, name, manager.getName(), total);
        System.out.println(msg + "\n");
    }

    public void departContent() {
        System.out.println("请根据提示输入对应数字浏览" + name + "数据");
        System.out.println("1.显示" + name + "基本信息");
        System.out.println("2.查看指定员工业绩具体信息");
        System.out.println("3.显示" + name + "员工按业绩排名信息");
        System.out.println("4.回到主菜单");
    }

    public void showSortMembers() {                              //显示部门成员业绩信息
        members.sort((o1, o2) -> o2.getPer().getTotal() - o1.getPer().getTotal());
        for (Employee member : members) {
            member.showPfm();
        }
        System.out.println();
    }

    public void getMember(String name) {         //部门中根据员工姓名查询业绩
        boolean flag = true;
        for (Employee member : members) {
            if (member.getName().equals(name)) {
                flag = false;
                System.out.print(name + "本月业绩   ");
                member.getPer().showPerformance();
                //不用break防止重名情况发生
            }
        }
        if (flag)
            System.out.println("抱歉，本部门无此人");
        System.out.println();
    }
}