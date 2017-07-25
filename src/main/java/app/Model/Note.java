package app.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import app.util.DatabaseConnection;

/**
 * Created by Kevin on 7/16/2017.
 */

public class Note {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int noteid;
	private int relid;
	private int goalid;
	private Date noteDate;
	private String note;

	public Note() {

	}

	public Note(int noteid, int relid, int goalid, Date noteDate, String note) {
		this.noteid = noteid;
		this.relid = relid;
		this.goalid = goalid;
		this.noteDate = noteDate;
		this.note = note;
	}

	/**
	 * @return the noteid
	 */
	public int getNoteid() {
		return noteid;
	}

	/**
	 * @param noteid
	 *            the noteid to set
	 */
	public void setNoteid(int noteid) {
		this.noteid = noteid;
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
	 * @return the noteDate
	 */
	public Date getNoteDate() {
		return noteDate;
	}

	/**
	 * @param noteDate
	 *            the noteDate to set
	 */
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public ArrayList<Note> getAllGoalNotes(int goalid) {

		ArrayList<Note> noteList = new ArrayList<>();
		Note note;

		String sql = "select * from notes where notes.goalid = '" + goalid
				+ "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				note = new Note();

				note.noteid = rs.getInt(1);
				note.relid = rs.getInt(2);
				note.goalid = rs.getInt(3);
				note.noteDate = rs.getDate(4);
				note.note = rs.getString(5);

				noteList.add(note);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return noteList;
	}

	public ArrayList<Note> getAllRelationshipNotes(int relid) {

		ArrayList<Note> noteList = new ArrayList<>();
		Note note;

		String sql = "select * from notes where notes.relid = '" + relid + "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				note = new Note();

				note.noteid = rs.getInt(1);
				note.relid = rs.getInt(2);
				note.goalid = rs.getInt(3);
				note.noteDate = rs.getDate(4);
				note.note = rs.getString(5);

				noteList.add(note);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return noteList;
	}

	public Note getNote(int noteid) {

		Note note = new Note();

		String sql = "select * from notes where notes.noteid = '" + noteid
				+ "'";

		try {
			con = DatabaseConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				note.noteid = rs.getInt(1);
				note.relid = rs.getInt(2);
				note.goalid = rs.getInt(3);
				note.noteDate = rs.getDate(4);
				note.note = rs.getString(5);

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return note;
	}

	public boolean addNote(int goalid, int relid, String date, String note)
			throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());

		String sql = "INSERT INTO goalkeeper.notes (relid,goalid,date,note) VALUES ('"
				+ relid + "','" + goalid + "','" + dateDB + "','" + note + "')";

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

	public boolean editNote(int noteid, int relid, String date, String note)
			throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());

		String sql = "UPDATE goalkeeper.notes SET relid='" + relid
				+ "', date='" + dateDB + "', note='" + note
				+ "' WHERE noteid='" + noteid + "'";

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

	public boolean editRelNote(int noteid, int goalid, String date, String note)
			throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = formatter.parse(date);
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());

		String sql = "UPDATE goalkeeper.notes SET goalid='" + goalid
				+ "', date='" + dateDB + "', note='" + note
				+ "' WHERE noteid='" + noteid + "'";

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

	public boolean deleteNote(int noteid) {

		String sql = "DELETE FROM goalkeeper.notes WHERE noteid='" + noteid
				+ "'";

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
