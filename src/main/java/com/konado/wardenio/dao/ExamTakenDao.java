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

	private static final String SELECT_COMMON = "SELECT id, event_id, access_pin, student_name, bucket_name, stream_name, start_date, student_code, exam_data, desktop_stream_name FROM exam_taken ";
	static final String UPDATE_START_EXAM = "UPDATE exam_taken SET bucket_name = ?, stream_name = ?, start_date = now(), desktop_stream_name = ? WHERE id=?";

	@Override
	public ExamTaken get(long id) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(SELECT_COMMON);
			sb.append("WHERE id = ?;");

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setLong(1, id);

			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				if (rs.next()) {
					return createFromResultSet(rs);
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

			StringBuilder sb = new StringBuilder();
			sb.append(SELECT_COMMON);
			sb.append("where access_pin = ?;");

			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, pin);

			rs = stmt.executeQuery();

			if (rs.next()) {
				return createFromResultSet(rs);
			} else {
				throw new Exception("ExamTaken not found");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	private ExamTaken createFromResultSet(ResultSet rs) throws SQLException {
		ExamTaken examTaken = new ExamTaken();
		examTaken.setId(rs.getLong(1));
		examTaken.setEventId(rs.getLong(2));
		examTaken.setAccessPin(rs.getString(3));
		examTaken.setStudentName(rs.getString(4));
		examTaken.setBucketName(rs.getString(5));
		examTaken.setStreamName(rs.getString(6));
		examTaken.setStartDate(rs.getTimestamp(7));
		examTaken.setStudentCode(rs.getString(8));
		examTaken.setExamData(rs.getString(9));
		examTaken.setDesktopStreamName(rs.getString(10));
		return examTaken;
	}

	public ExamTaken createNewExamTaken(Event event, String studentName, String examData, String studentCode) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

			conn.setAutoCommit(false);

			String insertStatement = "INSERT INTO exam_taken (exam_data, event_id, student_name, student_code) VALUES (?, ?, ?, ?);";

			PreparedStatement stmt = conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, examData);
			stmt.setLong(2, event.getId());
			stmt.setString(3, studentName);
			stmt.setString(4, studentCode);
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

			StringBuilder sb = new StringBuilder();
			sb.append(SELECT_COMMON);
			sb.append("WHERE event_id = ?;");

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setLong(1, eventId);

			List<ExamTaken> examTakenList = new LinkedList<ExamTaken>();

			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				examTakenList.add(createFromResultSet(rs));
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

			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(UPDATE_START_EXAM);
			stmt.setString(1, examTaken.getBucketName());
			stmt.setString(2, examTaken.getStreamName());
			stmt.setString(3, examTaken.getDesktopStreamName());
			stmt.setLong(4, examTaken.getId());
			stmt.execute();
			conn.commit();

		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
}
