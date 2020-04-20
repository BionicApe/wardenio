package com.konado.wardenio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.konado.wardenio.model.DeviceState;
import com.konado.wardenio.model.ExamTaken;
import com.konado.wardenio.requests.SetDeviceStateRequest;

public class DeviceStateDao implements Dao<DeviceState> {

	private static final String SELECT_COMMON = "SELECT id, device_state, device_model, device_type, exam_taken_id, date FROM device_state ";
	private static final String INSERT_STATEMENT = "INSERT INTO device_state (device_state, device_type, device_model, exam_taken_id, date) VALUES (?, ?, ?, ?, now(3));";

	@Override
	public DeviceState get(long id) throws Exception {

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
	public List<DeviceState> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DeviceState t) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(DeviceState t, String[] params) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DeviceState t) throws Exception {
		// TODO Auto-generated method stub

	}

	public Long createNew(ExamTaken examTaken, SetDeviceStateRequest request) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {

			PreparedStatement stmt = conn.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, request.getDeviceState());
			stmt.setString(2, request.getDeviceType());
			stmt.setString(3, request.getDeviceModel());
			stmt.setLong(4, request.getExamTakenId());
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (!rs.next()) {
				throw new SQLException("No Generated Keys Available");
			}

			return rs.getLong(1);

		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	private DeviceState createFromResultSet(ResultSet rs) throws SQLException {
		DeviceState deviceState = new DeviceState();
		deviceState.setId(rs.getLong(1));
		deviceState.setDeviceState(rs.getString(2));
		deviceState.setDeviceModel(rs.getString(3));
		deviceState.setDeviceType(rs.getString(4));
		deviceState.setExamTakenId(rs.getLong(5));
		deviceState.setTimestamp(rs.getTimestamp(6));
		return deviceState;
	}

	public List<DeviceState> getAllByExamTakenId(Long eventId) throws Exception {
		Connection conn = DBConnection.getRemoteConnection();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append(SELECT_COMMON);
			sb.append("WHERE exam_taken_id = ?;");

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setLong(1, eventId);

			List<DeviceState> examTakenList = new LinkedList<DeviceState>();

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
}
