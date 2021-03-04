import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Teacherpass {
    //用map集合接收用户输入的学号,密码,并判断
    public void add1() {
        Map<String, String> map = teacherifo();
        boolean t=check(map);
        if(t){
            System.out.println("登录成功");
            //实现跳转到另一个类
            Teachermethod s=new Teachermethod();
              s.tmain();
        }
        else{
            System.out.println("登录失败,请重新尝试");
        }
    }
    //链接数据库进行判断
    public boolean check(Map<String, String> map) {
        boolean t1=false;
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        //获取用户输入
        String s1=map.get("sno");
        String s2=map.get("password");

        //注册
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ResourceBundle bandle=ResourceBundle.getBundle("jdbc");
            String url=bandle.getString("url");
            String name=bandle.getString("name");
            String password=bandle.getString("password");
            con=DriverManager.getConnection(url,name,password);
            //取消自动提交机制
            con.setAutoCommit(false);
            //创建预编译的数据库操作对象
            String sql="select * from t_teacherifo where sno=? and pswd=?";
            //预编译
            ps=con.prepareStatement(sql);
            //传值
            ps.setString(1,s1);
            ps.setString(2,s2);
            //执行
            rs=ps.executeQuery();
            if(rs.next()){
                t1=true;
            }
            //自动提交
            con.commit();

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
        return t1;


    }

    //教师输入用户和密码
    public Map<String, String> teacherifo() {
        java.util.Scanner s=new java.util.Scanner(System.in);
        System.out.println("请输入教师编号:");
        String sno=s.nextLine();
        System.out.println("请输入密码:");
        String password=s.nextLine();
        Map<String,String>map1=new HashMap<>();
        map1.put("sno",sno);
        map1.put("password",password);
        return map1;
    }
}
