import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * 学生可以进行的功能:浏览自己的成绩
 * 查看自己的排名
 */
public class Studentmethod {


    public void see() {
        java.util.Scanner s = new java.util.Scanner(System.in);
       // System.out.println("---------欢迎来到自助平台-----------");
        System.out.println("请输入自己的学号:");
        String s1 = s.nextLine();

        // boolean t1=false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ResourceBundle bandle = ResourceBundle.getBundle("jdbc");
            String url = bandle.getString("url");
            String name = bandle.getString("name");
            String password = bandle.getString("password");
            con = DriverManager.getConnection(url, name, password);
            //取消自动提交机制
            con.setAutoCommit(false);
            //创建预编译的数据库操作对象
            String sql = "select * from t_studentifm where sno= ?";
            //预编译
            ps = con.prepareStatement(sql);
            //传值
            ps.setString(1, s1);
            //执行
            rs = ps.executeQuery();
            if (rs.next()) {
                String a1 = rs.getString("sno");
                String a2 = rs.getString("name");
                String a3 = rs.getString("Chinese");
                String a4 = rs.getString("Math");
                String a5 = rs.getString("英语");
                String a6 = rs.getString("total");
                System.out.println("你的信息: " + "学号: " + a1 +  "姓名: " + a2 + " 语文: " + a3 + "数学: " + a4 + "英语: " + a5 + "总分 " + a6);
            }
            //自动提交
            con.commit();

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

    //查找自己总分的名次
    public void rank() {
        System.out.println("请输入你的学号:");
        Scanner s = new Scanner(System.in);
        String a = s.nextLine();
        Connection con = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
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
            //得到对应学号人的总分;
            String sql1 = "select total from t_studentifm where sno=?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, a);
            rs1 = ps.executeQuery();
            if (rs1.next()) {
                System.out.println("查询成功");
                ///Double s1 = rs1.getDouble("total");
            } else {
                System.out.println("学号不存在");
                rank();
            }
            Double s1 = rs1.getDouble("total");


            //创建预编译的sql语句
            String sql = "select * from t_studentifm AS stu where stu.total>=? ";
            //预编译
            ps = con.prepareStatement(sql);
            //传值
            ps.setDouble(1, s1);
            //编译
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = count + 1;
            }
            System.out.println("在班级的名次:" + count);
            con.commit();


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

    public void Main() {
        Scanner s = new Scanner(System.in);
        boolean t=true;
        while (t) {
            System.out.println("---------欢迎来到学生平台----------");
            System.out.println("---------1.浏览自己的成绩----------");
            System.out.println("---------2.查看自己的排名----------");
            System.out.println("---------3.退出----------");
            System.out.println("请输入你的选择:");
            int b = s.nextInt();
            switch (b) {
                case 1:
                    see();
                      break;
                case 2:
                     rank();
                     break;
                case 3:
                    t=false;
                      break;

            }

        }
    }
}



