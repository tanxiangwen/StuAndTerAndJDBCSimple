import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
public class Test {
    public static void main(String[] args) {
        java.util.Scanner s = new java.util.Scanner(System.in);
        boolean t = true;
        Studentpass stu = new Studentpass();
        Teacherpass stu2 = new Teacherpass();
        Teachermethod stu3=new Teachermethod();
        while (t) {
            System.out.println("------------欢迎使用教务管理系统---------------");
            System.out.println("------------1.学生登录-------------");
            System.out.println("------------2.教师登录-------------");
            System.out.println("------------3.退出系统-------------");
            System.out.println("请输入你的选择:");
            int a = s.nextInt();
            switch (a) {
                case 1:
                       stu.add();
                    break;

                case 2:
                    stu2.add1();
                    break;
                case 3:
                    System.out.println("谢谢使用");
                      t=false;

                    break;
            }

        }
    }
}