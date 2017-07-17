package main.java.app.Model;

import main.java.app.util.DatabaseConnection;
import java.sql.*;
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

		// String username_lower = username.toLowerCase();

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

}
