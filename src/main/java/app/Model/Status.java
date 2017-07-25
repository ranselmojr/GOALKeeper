package main.java.app.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import main.java.app.util.DatabaseConnection;

public class Status {
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int statusid;
	private int goalid;
	private Date comp_date;
	private int percent_done;
	
	public Status() {
		
	}
	
	public Status(int statusid, int goalid, Date comp_date, int percent_done) {
		this.statusid = statusid;
		this.goalid = goalid;
		this.comp_date = comp_date;
		this.percent_done = percent_done;
	}

	/**
	 * @return the statusid
	 */
	public int getStatusid() {
		return statusid;
	}

	/**
	 * @param statusid the statusid to set
	 */
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}

	/**
	 * @return the goalid
	 */
	public int getGoalid() {
		return goalid;
	}

	/**
	 * @param goalid the goalid to set
	 */
	public void setGoalid(int goalid) {
		this.goalid = goalid;
	}

	/**
	 * @return the comp_date
	 */
	public Date getComp_date() {
		return comp_date;
	}

	/**
	 * @param comp_date the comp_date to set
	 */
	public void setComp_date(Date comp_date) {
		this.comp_date = comp_date;
	}

	/**
	 * @return the percent_done
	 */
	public int getPercent_done() {
		return percent_done;
	}

	/**
	 * @param percent_done the percent_done to set
	 */
	public void setPercent_done(int percent_done) {
		this.percent_done = percent_done;
	}
	
	public Status getStatus(int goalid) {

		Status status = new Status();

		String sql = "select * from status where status.goalid = '" + goalid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				status.statusid = rs.getInt(1);
				status.goalid = rs.getInt(2);
				status.comp_date = rs.getDate(3);
				status.percent_done = rs.getInt(4);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
}
