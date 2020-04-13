package com.konado.wardenio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.konado.wardenio.model.Event;
import com.konado.wardenio.model.ExamTaken;

public class ExamTakenDao implements Dao<ExamTaken> {

	@Override
	public ExamTaken get(long id) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

//			id, exam_data, event_id, access_pin, student_name, bucket_name, stream_name
			String sqlStr = "SELECT id, event_id, access_pin, student_name, bucket_name, stream_name, start_date, exam_data FROM exam_taken WHERE ID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqlStr);
			stmt.setLong(1, id);

			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				if (rs.next()) {
					ExamTaken examTaken = new ExamTaken();
					examTaken.setId(rs.getLong(1));
					examTaken.setEventId(rs.getLong(2));
					examTaken.setAccessPin(rs.getString(3));
					examTaken.setStudentName(rs.getString(4));
					examTaken.setBucketName(rs.getString(5));
					examTaken.setStreamName(rs.getString(6));
					examTaken.setStartDate(rs.getTimestamp(7));
					examTaken.setExamData(rs.getString(8));
					return examTaken;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		return null;
	}

	@Override
	public List<ExamTaken> getAll() throws Exception {
		return null;
	}

	@Override
	public void save(ExamTaken newExamTaken) throws Exception {
	}

	@Override
	public void update(ExamTaken t, String[] params) throws Exception {

	}

	@Override
	public void delete(ExamTaken t) throws Exception {

	}

	public ExamTaken getByPin(String pin) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

			PreparedStatement stmt = null;
			ResultSet rs = null;

			String getStatement = "select id, exam_data, event_id, access_pin, student_name, bucket_name, stream_name from exam_taken where access_pin = ?;";

			stmt = conn.prepareStatement(getStatement);
			stmt.setString(1, pin);

			rs = stmt.executeQuery();
			if (rs.next()) {
				ExamTaken examTaken = new ExamTaken();
				examTaken.setId(rs.getLong(1));
				examTaken.setExamData(rs.getString(2));
				examTaken.setEventId(rs.getLong(3));
				examTaken.setAccessPin(rs.getString(4));
				examTaken.setStudentName(rs.getString(5));
				examTaken.setBucketName(rs.getString(6));
				examTaken.setStreamName(rs.getString(7));
				return examTaken;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		return null;
	}

	public ExamTaken CreateNewExamTaken(Event event, String studentName, String examData) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

			conn.setAutoCommit(false);

			String insertStatement = "INSERT INTO exam_taken (exam_data, event_id, student_name) VALUES (?, ?, ?);";

			PreparedStatement stmt = conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, examData);
			stmt.setLong(2, event.getId());
			stmt.setString(3, studentName);
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (!rs.next()) {
				throw new SQLException("No Generated Keys Available");
			}

			long generatedId = rs.getLong(1);

			String generatePinStr = "UPDATE exam_taken SET access_pin =concat(" +
					"substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand(@seed:=round(rand(?)*4294967296))*36+1, 1)," +
					"substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand(@seed:=round(rand(@seed)*4294967296))*36+1, 1)," +
					"substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand(@seed:=round(rand(@seed)*4294967296))*36+1, 1)," +
					"substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand(@seed)*36+1, 1)" +
					") WHERE id=?";

			PreparedStatement stmt2 = conn.prepareStatement(generatePinStr);
			stmt2.setLong(1, generatedId);
			stmt2.setLong(2, generatedId);
			stmt2.execute();

			conn.commit();

			return get(generatedId);

		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<ExamTaken> getAllByEventId(Long eventId) throws Exception {
		Connection conn = DBConnection.getRemoteConnection();
		try {

			String sqlStr = "SELECT id, event_id, access_pin, student_name, bucket_name, stream_name, start_date FROM exam_taken WHERE event_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqlStr);
			stmt.setLong(1, eventId);

			List<ExamTaken> examTakenList = new LinkedList<ExamTaken>();

			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				ExamTaken examTaken = new ExamTaken();
				examTaken.setId(rs.getLong(1));
				examTaken.setEventId(rs.getLong(2));
				examTaken.setAccessPin(rs.getString(3));
				examTaken.setStudentName(rs.getString(4));
				examTaken.setBucketName(rs.getString(5));
				examTaken.setStreamName(rs.getString(6));
				examTaken.setStartDate(rs.getTimestamp(7));
				examTakenList.add(examTaken);
			}
			return examTakenList;
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	public void updateStartExam(ExamTaken examTaken) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

			String queryStr = "UPDATE exam_taken SET bucket_name =?, stream_name = ?, start_date = now() WHERE id=?";

			PreparedStatement stmt = conn.prepareStatement(queryStr);
			stmt.setString(1, examTaken.getBucketName());
			stmt.setString(2, examTaken.getStreamName());
			stmt.setLong(3, examTaken.getId());
			stmt.execute();

		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
}
