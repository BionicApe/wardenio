package com.konado.wardenio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.konado.wardenio.model.Customer;

public class CustomerDao implements Dao<Customer> {

	private static final Object FULL_SELECT = "SELECT id, username, password, bucket_name, stream_name FROM customer ";

	private Customer createFromResultSet(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong(1));
		customer.setUsername(rs.getString(2));
		customer.setPassword(rs.getString(3));
		customer.setBucketName(rs.getString(4));
		customer.setStreamName(rs.getString(5));
		return customer;
	}

	@Override
	public Customer get(long id) throws Exception {

		Connection conn = DBConnection.getRemoteConnection();
		StringBuilder sb = new StringBuilder();
		try {

			sb.append(FULL_SELECT);
			sb.append("where id = ?;");

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setLong(1, id);

			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				return createFromResultSet(rs);
			} else {
				throw new Exception("Customer not found");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	@Override
	public List<Customer> getAll() throws Exception {
		return null;
	}

	@Override
	public void save(Customer t) throws Exception {

	}

	@Override
	public void update(Customer t, String[] params) throws Exception {

	}

	@Override
	public void delete(Customer t) throws Exception {

	}

	public Customer GetByUsernameAndPassword(String customerUsername, String customerPassword) throws Exception {
		Connection conn = DBConnection.getRemoteConnection();
		StringBuilder sb = new StringBuilder();
		try {

			sb.append(FULL_SELECT);
			sb.append("where username = ? and password = ?");

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, customerUsername);
			stmt.setString(2, customerPassword);
			stmt.execute();

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				return createFromResultSet(rs);
			} else {
				throw new Exception("Customer not found");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}
}