package main.java.app.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.app.util.DatabaseConnection;

/**
 * Created by Kevin on 7/16/2017.
 */

public class Behavior {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int obid;
	private int goalid;
	private String behavior;

	public Behavior() {

	}

	public Behavior(int obid, int goalid, String behavior) {
		super();
		this.obid = obid;
		this.goalid = goalid;
		this.behavior = behavior;
	}

	/**
	 * @return the obid
	 */
	public int getObid() {
		return obid;
	}

	/**
	 * @param obid
	 *            the obid to set
	 */
	public void setObid(int obid) {
		this.obid = obid;
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
	 * @return the behavior
	 */
	public String getBehavior() {
		return behavior;
	}

	/**
	 * @param behavior
	 *            the behavior to set
	 */
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public ArrayList<Behavior> getAllBehaviors(int goalid) {

		ArrayList<Behavior> behaviorList = new ArrayList<>();
		Behavior behavior;

		String sql = "select * from observer_behaviour where observer_behaviour.goalid = '"
				+ goalid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				behavior = new Behavior();

				behavior.obid = rs.getInt(1);
				behavior.goalid = rs.getInt(2);
				behavior.behavior = rs.getString(3);

				behaviorList.add(behavior);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return behaviorList;
	}

	public Behavior getBehavior(int obid) {

		Behavior behavior = new Behavior();

		String sql = "select * from observer_behaviour where observer_behaviour.obid = '"
				+ obid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				behavior.obid = rs.getInt(1);
				behavior.goalid = rs.getInt(2);
				behavior.behavior = rs.getString(3);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return behavior;
	}

	public boolean addBehavior(int goalid, String behavior) {

		String sql = "INSERT INTO goalkeeper.observer_behaviour (goalid,behaviour) VALUES ('"
				+ goalid + "','" + behavior + "')";

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

	public boolean editBehavior(int obid, String behavior) {

		String sql = "UPDATE goalkeeper.observer_behaviour SET behaviour='"
				+ behavior + "' WHERE obid='" + obid + "'";

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

	public boolean deleteBehavior(int obid) {

		String sql = "DELETE FROM goalkeeper.observer_behaviour WHERE obid='"
				+ obid + "'";

		try {
			System.out.println("Delete");
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
