package app.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import app.util.DatabaseConnection;

public class Relationship {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int relid;
	private int userid;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String category;

	public Relationship() {

	}

	public Relationship(int relid, int userid, String firstname,
			String lastname, String phone, String email) {
		super();
		this.relid = relid;
		this.userid = userid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * @return the relid
	 */
	public int getRelid() {
		return relid;
	}

	/**
	 * @param relid
	 *            the relid to set
	 */
	public void setRelid(int relid) {
		this.relid = relid;
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

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<Relationship> getAllRelationships(int userid) {

		ArrayList<Relationship> relList = new ArrayList<>();
		Relationship rel;

		String sql = "select * from relationship where relationship.user_id = '"
				+ userid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				rel = new Relationship();

				rel.relid = rs.getInt(1);
				rel.userid = rs.getInt(2);
				rel.lastname = rs.getString(3);
				rel.firstname = rs.getString(4);
				rel.phone = rs.getString(5);
				rel.email = rs.getString(6);
				rel.category = rs.getString(7);

				relList.add(rel);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return relList;
	}

	public Relationship getRelationship(int relid) {

		Relationship rel = new Relationship();

		String sql = "select * from relationship where relationship.relid = '"
				+ relid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				rel.relid = rs.getInt(1);
				rel.userid = rs.getInt(2);
				rel.lastname = rs.getString(3);
				rel.firstname = rs.getString(4);
				rel.phone = rs.getString(5);
				rel.email = rs.getString(6);
				rel.category = rs.getString(7);
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return rel;
	}

	public Relationship addRelationship(int userid, String fname, String lname,
			String phone, String email, String category) {

		Relationship rel = new Relationship();
		int i = 0;

		String sql = "INSERT INTO goalkeeper.relationship (user_id,firstname,lastname,phone,email,category) "
				+ "VALUES ('"
				+ userid
				+ "','"
				+ fname
				+ "','"
				+ lname
				+ "','"
				+ phone + "','" + email + "','" + category + "')";

		String sql1 = "SELECT MAX(relid) FROM goalkeeper.relationship WHERE user_id = '"
				+ userid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				i = rs.getInt(1);
			}
			rel.setRelid(i);

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return rel;
	}

	public boolean deleteRelationship(int relid) {

		String sql = "DELETE FROM goalkeeper.relationship WHERE relid='"
				+ relid + "'";

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

	public Relationship editRelationship(int relid, String fname, String lname,
			String phone, String email, String category) {

		Relationship rel = new Relationship();

		String sql = "UPDATE goalkeeper.relationship SET firstname='" + fname
				+ "',lastname='" + lname + "',phone='" + phone + "',email='"
				+ email + "',category='" + category + "'WHERE relid='" + relid
				+ "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return rel;
	}
}
