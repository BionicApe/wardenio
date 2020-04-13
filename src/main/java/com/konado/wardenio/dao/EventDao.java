package com.konado.wardenio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.konado.wardenio.model.Customer;
import com.konado.wardenio.model.Event;
import com.konado.wardenio.requests.CreateEventRequest;

public class EventDao implements Dao<Event> {

	@Override
	public Event get(long id) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		try {
			
			String sqlStr = "SELECT id, name, event_data, customer_id FROM event WHERE ID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqlStr);
			stmt.setLong(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				Event event = new Event();
				event.setId(rs.getLong(1));
				event.setName(rs.getString(2));
				event.setEventData(rs.getString(3));
				event.setCustomerId(rs.getLong(4));
				return event;
			} else {
				throw new SQLException("Event not found");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<Event> getAll() throws Exception {
		return null;
	}

	@Override
	public void save(Event newEvent) throws Exception {
	}

	public Long save(CreateEventRequest eventRequest, Customer customer) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();

		PreparedStatement statement = null;

		String stmtString = "INSERT INTO event (name,event_data,customer_id) VALUES (?,?,?);";

		statement = conn.prepareStatement(stmtString, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, eventRequest.getEventName());
		statement.setString(2, eventRequest.getEventData());
		statement.setLong(3, customer.getId());

		int affectedRows = statement.executeUpdate();

		if (affectedRows == 0) {
			throw new SQLException("Creating event failed, no rows affected.");
		}

		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				throw new SQLException("Creating event failed, no ID obtained.");
			}
		}
	}

	@Override
	public void update(Event t, String[] params) throws Exception {

	}

	@Override
	public void delete(Event t) throws Exception {

	}
}
