package org.hibernate.ogm.hiking.repository.business;

import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.model.business.Order;
import org.hibernate.ogm.hiking.repository.HikeRepository;

@ApplicationScoped
public class OrderRepository {

	@PersistenceContext(unitName="business")
	private EntityManager entityManager;

	@Inject
	private HikeRepository hikeResolitory;

	public Order createOrder(Order order) {
		order.number = UUID.randomUUID().toString();
		entityManager.persist( order );
		return order;
	}

	public Order getOrderById(long orderId) {
		return entityManager.find( Order.class, orderId );
	}

	public Order getOrderByNumber(String number) {
		return (Order) entityManager.createQuery( "from Order o where o.number = :number" )
				.setParameter( "number", number )
				.getSingleResult();
	}

	public List<Order> getAllOrders() {
		return entityManager.createQuery( "FROM Order", Order.class ).getResultList();
	}


}
