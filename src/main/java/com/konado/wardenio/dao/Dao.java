package com.konado.wardenio.dao;

import java.util.List;

public interface Dao<T> {

	T get(long id) throws Exception;

	List<T> getAll() throws Exception;

	void save(T t) throws Exception;

	void update(T t, String[] params) throws Exception;

	void delete(T t) throws Exception;
}