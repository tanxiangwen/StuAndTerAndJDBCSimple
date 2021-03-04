/**
 * 教师端业务
 * 对数据库中的学生信息等进行增删改查
 */
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
public class Teachermethod {
    //学生添加
    public void addstud() {
        // Map<String,String>map=new HashMap<>();
        Scanner s = new Scanner(System.in);
        System.out.println("请输入要添加学生的学号:");
        String sno1 = s.nextLine();
        //从数据库中查找是否已经存在这个学号
        Connection con = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        //注册驱动
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ResourceBundle bandle = ResourceBundle.getBundle("jdbc");
            String url = bandle.getString("url");
            String name = bandle.getString("name");
            String password = bandle.getString("password");
            con = DriverManager.getConnection(url, name, password);
            con.setAutoCommit(false);
            //创建数据库操作对象
            String sql = "select * from t_studentifm where sno=?";
            ps = con.prepareStatement(sql);
            //传值
            ps.setString(1, sno1);
            //编译
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("该学号对应的数据已经存在,请重新输入");
                addstud();
            } else {
                System.out.println("请输入姓名:");
                String ss1 = s.nextLine();
                System.out.println("请输入语文成绩:");
                double s1 = s.nextDouble();
                System.out.println("请输入数学成绩:");
                double s2 = s.nextDouble();
                System.out.println("请输入英语成绩:");
                double s3 = s.nextDouble();
                double s4 = s1 + s2 + s3;
                //创建预编译的数据库操作对象
                String sql1 = "insert into t_studentifm(sno,name,Chinese,Math,英语,total) values(?,?,?,?,?,?)";
                //预编译
                ps = con.prepareStatement(sql1);
                //传值
                ps.setString(1, sno1);
                ps.setString(2, ss1);
                ps.setDouble(3, s1);
                ps.setDouble(4, s2);
                ps.setDouble(5, s3);
                ps.setDouble(6, s4);
                int count = ps.executeUpdate();

                System.out.println("录入成功,一共影响" + count + "条数据");
                con.commit();


            }
            //判断

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }


    }

    //删除
    public void delete() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入你要删除学生的学号:");
        String s1 = s.nextLine();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //先判断数据库中是否存在这个学生
        //注册驱动
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //建立链接
            ResourceBundle bandle = ResourceBundle.getBundle("jdbc");
            String url = bandle.getString("url");
            String name = bandle.getString("name");
            String password = bandle.getString("password");
            con = DriverManager.getConnection(url, name, password);
            con.setAutoCommit(false);
            //创建预编译的数据库操作对象
            String sql = "select *from t_studentifm where sno=?";
            //预编译
            ps = con.prepareStatement(sql);
            //传值
            ps.setString(1, s1);
            //编译
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("没有此学生信息,请重新输入");
                delete();
            } else {
                String sql2 = "delete from t_studentifm where sno=?";
                ps = con.prepareStatement(sql2);
                //传值
                ps.setString(1, s1);
                //编译
                int count = ps.executeUpdate();
                System.out.println("删除数据成功!一共影响了" + count + "条学生数据");
                con.commit();


            }

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    public void update() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入你要修改的学生学号:");
        String s1 = s.nextLine();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //先判断数据库中是否存在这个学生
        //注册驱动
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //建立链接
            ResourceBundle bandle = ResourceBundle.getBundle("jdbc");
            String url = bandle.getString("url");
            String name = bandle.getString("name");
            String password = bandle.getString("password");
            con = DriverManager.getConnection(url, name, password);
            con.setAutoCommit(false);
            //创建预编译的数据库操作对象
            String sql = "select *from t_studentifm where sno=?";
            //预编译
            ps = con.prepareStatement(sql);
            //传值
            ps.setString(1, s1);
            //编译
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("没有此学生，请重新输入");
                update();
            } else {
                boolean t = true;
                while (t) {
                    System.out.println("----------1.语文成绩修改----------");
                    System.out.println("----------2.数学成绩修改----------");
                    System.out.println("----------3.英语成绩修改----------");
                    System.out.println("-----------4.退出系统-------------");
                    System.out.println("请输入你的选择:");
                    int a = s.nextInt();
                    switch (a) {
                        case 1:
                            System.out.println("请输入你要修改的语文分数:");
                            double a1 = s.nextDouble();
                            String sql2 = "update t_studentifm set Chinese=? where sno=?";
                            ps = con.prepareStatement(sql2);
                            ps.setDouble(1, a1);
                            ps.setString(2, s1);
                            int count = ps.executeUpdate();
                            //修改总分
                            String sql3 = "select * from t_studentifm where sno=?";
                            ps = con.prepareStatement(sql3);
                            ps.setString(1, s1);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                Double ch = rs.getDouble("Chinese");
                                Double ma = rs.getDouble("Math");
                                Double en = rs.getDouble("英语");
                                Double all = ch + ma + en;
                                String sql4 = "update t_studentifm set total=? where sno=?";
                                ps = con.prepareStatement(sql4);
                                ps.setDouble(1, all);
                                ps.setString(2, s1);
                                count = ps.executeUpdate();
                                System.out.println("修改成功,一共影响" + count + "条数据");
                                con.commit();
                            }
                            break;
                        case 2:
                            System.out.println("请输入你要修改的数学分数:");
                            double a2 = s.nextDouble();
                            String sql4 = "update t_studentifm set Math=? where sno=?";
                            ps = con.prepareStatement(sql4);
                            ps.setDouble(1, a2);
                            ps.setString(2, s1);
                            int count1 = ps.executeUpdate();
                            //修改总分
                            String sql5 = "select * from t_studentifm where sno=?";
                            ps = con.prepareStatement(sql5);
                            ps.setString(1, s1);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                Double ch1 = rs.getDouble("Chinese");
                                Double ma1 = rs.getDouble("Math");
                                Double en1 = rs.getDouble("英语");
                                Double all1 = ch1 + ma1 + en1;
                                String sql6 = "update t_studentifm set total=? where sno=?";
                                ps = con.prepareStatement(sql6);
                                ps.setDouble(1, all1);
                                ps.setString(2, s1);
                                count1 = ps.executeUpdate();
                                System.out.println("修改成功,一共影响" + count1 + "条数据");
                                con.commit();

                            }
                            break;
                        case 3:
                            System.out.println("请输入你要修改的英语分数:");
                            double a3 = s.nextDouble();
                            String sql6 = "update t_studentifm set English=? where sno=?";
                            ps = con.prepareStatement(sql6);
                            ps.setDouble(1, a3);
                            ps.setString(2, s1);
                            int count2 = ps.executeUpdate();
                            //修改总分
                            String sql7 = "select * from t_studentifm where sno=?";
                            ps = con.prepareStatement(sql7);
                            ps.setString(1, s1);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                Double ch1 = rs.getDouble("Chinese");
                                Double ma1 = rs.getDouble("Math");
                                Double en1 = rs.getDouble("英语");
                                Double all1 = ch1 + ma1 + en1;
                                String sql8 = "update t_studentifm set total=? where sno=?";
                                ps = con.prepareStatement(sql6);
                                ps.setDouble(1, all1);
                                ps.setString(2, s1);
                                count2 = ps.executeUpdate();
                                System.out.println("修改成功,一共影响" + count2 + "条数据");
                                con.commit();

                            }
                            break;
                        case 4:
                            t = false;
                            break;
                    }


                }
            }

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    public void select() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入你要查询的学生学号:");
        String s1 = s.nextLine();
        //从数据库中查找是否已经存在这个学号
        Connection con = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        //注册驱动
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ResourceBundle bandle = ResourceBundle.getBundle("jdbc");
            String url = bandle.getString("url");
            String name = bandle.getString("name");
            String password = bandle.getString("password");
            con = DriverManager.getConnection(url, name, password);
            con.setAutoCommit(false);
            String sql="select * from t_studentifm where sno=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,s1);
            rs=ps.executeQuery();
            if(!rs.next()){
                System.out.println("学生不存在,请重新输入");
                select();
            }
            else{
                String a1=rs.getString("sno");
                String a2=rs.getString("name");
                String a3=rs.getString("Chinese");
                String a4=rs.getString("Math");
                String a5=rs.getString("英语");
                String a6=rs.getString("total");
                String sql2="select * from t_studentifm where total>=?";
                ps=con.prepareStatement(sql2);
                ps.setString(1,a6);
                rs=ps.executeQuery();
                int count=0;
                while(rs.next()){
                    count=count+1;
                }
                System.out.println("学号: " + a1 + "姓名: " + a2 + "语文: " +  a3 + "数学: " + a4 + "英语: " + a5 + "总分: " + a6 + "班级排名: " + count);
                con.commit();
            }




        } catch (SQLException e) {
            if(con!=null){
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
    public void tmain(){
        Scanner s=new Scanner(System.in);
        boolean t=true;
        while(t) {

            System.out.println("----------欢迎进入教师服务平台-----------");
            System.out.println("----------1.增加学生数据-----------");
            System.out.println("----------2.删除学生数据-----------");
            System.out.println("----------3.修改学生数据-----------");
            System.out.println("----------4.查找学生数据-----------");
            System.out.println("----------5.退出系统-----------");
            System.out.println("请输入你的选择:");
            int a = s.nextInt();
            switch(a){
                case 1:
                     addstud();
                     break;
                case 2:
                    delete();
                    break;
                case 3:
                     update();
                     break;
                case 4:
                    select();
                    break;
                case 5:
                    t=false;
                    break;

            }
        }


    }
}