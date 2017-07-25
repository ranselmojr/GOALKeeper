package app.Model;

import app.util.DatabaseConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Kevin on 7/16/2017.
 */

public class Goal {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int goalid;
	private String goalText;
	private int userid;
	
	public Goal() {
		
	}

	public Goal(int goalid, String goalText, int userid) {
		this.goalid = goalid;
		this.goalText = goalText;
		this.userid = userid;
	}

	/**
	 * @return the goalid
	 */
	public int getGoalid() {
		return goalid;
	}

	/**
	 * @param goalid
	 *            the goalid to set
	 */
	public void setGoalid(int goalid) {
		this.goalid = goalid;
	}

	/**
	 * @return the goalText
	 */
	public String getGoalText() {
		return goalText;
	}

	/**
	 * @param goalText
	 *            the goalText to set
	 */
	public void setGoalText(String goalText) {
		this.goalText = goalText;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	public ArrayList<Goal> getAllGoals(int userid) {

		ArrayList<Goal> goalList = new ArrayList<>();
		Goal goal;

		String sql = "select * from goal where goal.userid = '" + userid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				goal = new Goal();

				goal.goalid = rs.getInt(1);
				goal.userid = rs.getInt(2);
				goal.goalText = rs.getString(3);

				goalList.add(goal);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return goalList;
	}
	
	public Goal getGoal(int goalid) {

		Goal goal = new Goal();

		String sql = "select * from goal where goal.goalid = '" + goalid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				goal.goalid = rs.getInt(1);
				goal.userid = rs.getInt(2);
				goal.goalText = rs.getString(3);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return goal;
	}
	
	
	public Goal addGoal(int userid, String goalText, String comp_date, String percent_done) throws ParseException {

		Goal goal = new Goal();
		int i=0;
		
		int comp = Integer.parseInt(percent_done);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
		java.util.Date dateStr = formatter.parse(comp_date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
	
		String sql = "INSERT INTO goalkeeper.goal (userid,goal) VALUES ('"+userid + "','" + goalText + "')";
		String sql1 = "SELECT MAX(goalid) FROM goalkeeper.goal WHERE userid = '"+ userid + "'";
		
		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql1);
			
			while (rs.next()) {
				i = rs.getInt(1);
			}
			goal.setGoalid(i);
			String sql2 = "INSERT INTO goalkeeper.status (goalid,comp_date,percent_done) VALUES ('"+i+"','"+dateDB+"','"+ comp +"')";
			stmt = con.createStatement();
			stmt.executeUpdate(sql2);
			
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return goal;
	}
	
	public Goal editGoal(int goalid, String goalText, String comp_date, String percent_done) throws ParseException {

		Goal goal = new Goal();
		
		int comp = Integer.parseInt(percent_done);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = formatter.parse(comp_date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
	
		String sql = "UPDATE goalkeeper.goal SET goal='" + goalText +"' WHERE goalid='" + goalid + "'";
		String sql1 = "UPDATE goalkeeper.status SET comp_date='" + dateDB +"', percent_done='" + comp + "' WHERE goalid='" + goalid + "'";
		
		try {
			con = DatabaseConnection.getConnection();
			
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			stmt = con.createStatement();
			stmt.executeUpdate(sql1);
			
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return goal;
	}
	
	
	public boolean deleteGoal(int goalid) {
		
		String sql = "DELETE FROM goalkeeper.goal WHERE goalid='"+ goalid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			con.close();

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
}
