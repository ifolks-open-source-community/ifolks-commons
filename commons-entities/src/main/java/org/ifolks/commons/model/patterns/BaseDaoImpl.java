package org.ifolks.commons.model.patterns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ifolks.commons.api.exception.repository.ObjectNotFoundException;
import org.ifolks.commons.api.model.OrderType;
import org.ifolks.commons.model.interfaces.Entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public abstract class BaseDaoImpl<T extends Entity<U>, U extends Serializable> implements BaseDao<T, U> {

	/*
	 * resources injected with spring
	 */
	@PersistenceContext
	protected EntityManager entityManager;


	
	private Class<T> clazz;
	

	/**
	 * constructor with the correct class to handle
	 */
	public BaseDaoImpl(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	/**
	 * count object list
	 */
	@Override
	public Long count() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<T> root = criteria.from(clazz);
		criteria.select(builder.count(root));		 
		return entityManager.createQuery(criteria).getSingleResult();
	}

	/**
	 * load object list
	 */
	@Override
	public List<T> loadList() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root);
		List<Order> orders = new ArrayList<>();
		JpaCriteriaUtils.addOrder(builder, orders, root.get("id"), OrderType.DESC);
		criteria.orderBy(orders);
		return entityManager.createQuery(criteria).getResultList();
	}

	/**
	 * load object list eagerly
	 */
	@Override
	public abstract List<T> loadListEagerly();

	/**
	 * load object
	 */
	@Override
	public T load(U id) {
		T obj = get(id);
		if (obj == null) {
			throw new ObjectNotFoundException(clazz.getSimpleName() + ".notFound");
		} else {
			return obj;
		}
	}

	/**
	 * get object
	 */
	@Override
	public T get(U id) {
		return entityManager.find(clazz, id);
	}

	/**
	 * save object
	 */
	@Override
	public U save(T obj) {
		entityManager.persist(obj);
		return obj.getId();
	}

	/**
	 * delete object
	 */
	@Override
	public void delete(T obj) {
		this.entityManager.remove(obj);
	}

	/**
	 * flush
	 */
	@Override
	public void flush() {
		entityManager.flush();
	}

	/**
	 * evict obj
	 */
	@Override
	public void detach(T obj) {
		entityManager.detach(obj);
	}

	/**
	 * clear
	 */
	@Override
	public void clear() {
		entityManager.clear();
	}
}
