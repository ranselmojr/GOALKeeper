package app.Model;

import app.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Romeo on 6/1/2017.
 */
public class UserAccount {

    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private int userID;
    private String name;
    private String username;
    private String username_lower;
    private String password;
    private String email;
    private String phone;
    private String mobile;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private int zipcode;

    public UserAccount(){

    }

    public int getUserID(){
        return userID;
    }
    public String getName(){
        return name;
    }
    public String getUserName(){
        return username;
    }
    public String getUserNameLower(){
        return username_lower;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public String getMobile(){
        return mobile;
    }
    public String getLine1(){
        return line1;
    }
    public String getLine2(){
        return line2;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public int getZipCode(){
        return zipcode;
    }


    public UserAccount(int id, String name, String username, String username_lower,
                String password, String email, String phone, String mobile,
                String line1, String line2, String city, String state, int zipcode){
        this.userID = id;
        this.name = name;
        this.username = username;
        this.username_lower = username_lower;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }




    public UserAccount getUserAccount(String username){

        UserAccount userInfo = new UserAccount();

        String username_lower = username.toLowerCase();

        String sql = "select * from user_account, address " +
                " where address.user_id = user_account.id and username_lower = '"+ username_lower +"'";

        try {
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                userInfo.userID = rs.getInt(1);
                userInfo.name = rs.getString(2);
                userInfo.username = rs.getString(3);
                userInfo.username_lower = rs.getString(4);
                userInfo.password = rs.getString(5);
                userInfo.email = rs.getString(6);
                userInfo.phone = rs.getString(7);
                userInfo.mobile = rs.getString(8);
                userInfo.line1 = rs.getString(11);
                userInfo.line2 = rs.getString(12);
                userInfo.city = rs.getString(13);
                userInfo.state = rs.getString(14);
                userInfo.zipcode = rs.getInt(15);
            }
            con.close();
            if(userInfo.userID == 0) {
                return userInfo = null;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return userInfo;
    }

    public boolean addAccount(UserAccount user){

        String value = null;
        String sql = "insert into user_account(name, username, username_lower," +
                "password, email, phone, mobile) "
                + "values(?,?,?,?,?,?,?)";
        try {
            con = DatabaseConnection.getConnection();

            String sql1 = "select * from user_account where username_lower = '"
                    + user.username_lower + " ' ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                value = rs.getString(3);
            }

            if (user.username_lower.equals(value)) {
                System.out.println("Username already exist!!!");
                con.close();
                return false;
            }else{
                PreparedStatement preparedStmt = con.prepareStatement(sql);

                preparedStmt.setString(1, user.name);
                preparedStmt.setString(2, user.username);
                preparedStmt.setString(3, user.username_lower);
                preparedStmt.setString(4, user.password);
                preparedStmt.setString(5, user.email);
                preparedStmt.setString(6, user.phone);
                preparedStmt.setString(7, user.mobile);

                preparedStmt.execute();
                System.out.println("User Registered!!!");

                String addressSQL = "select id from user_account where username_lower = '" +
                        user.username_lower + " ' ";

                stmt = con.createStatement();
                rs = stmt.executeQuery(sql1);
                int user_accountid = 0;
                while (rs.next()) {
                    user_accountid = rs.getInt(1);
                }

                String addAdressSQL = "insert into address(user_id, line1, line2," +
                        "city, state, zipcode) "
                        + "values(?,?,?,?,?,?)";

                PreparedStatement preparedStmt2 = con.prepareStatement(addAdressSQL);

                preparedStmt2.setInt(1, user_accountid );
                preparedStmt2.setString(2, user.line1);
                preparedStmt2.setString(3, user.line2);
                preparedStmt2.setString(4, user.city);
                preparedStmt2.setString(5, user.state);
                preparedStmt2.setInt(6, user.zipcode);

                preparedStmt2.execute();
                System.out.println("Address Added!!!");

            }
            con.close();


        }
        catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }



}
