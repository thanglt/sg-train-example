package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.wideplay.warp.persist.Transactional;

/**
 * @author
 * 
 *         <p>
 *         Interface for data access classes.
 * 
 * @param <T>
 * 
 */
public interface IDao<T extends BaseModel<T>> {

	/**
	 * Generic method to get an object based on class and identifier.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to get.
	 * @return a populated object.
	 */
	public T find(Integer id);
	
	public T find(Class<? extends BaseModel<T>> bmc,int id);
	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return the list of populated objects.
	 */
	public List<T> fetchAll();

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save.
	 * @return the persisted object.
	 */
	@Transactional
	public T merge(T object);
	
	
	@Transactional
	public T persist(T object);

	/**
	 * Generic method to delete an object based on class and id.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to remove.
	 * @return 
	 */
	@Transactional
	public T remove(BaseModel<T> object);

	public Integer getRowCount();

}