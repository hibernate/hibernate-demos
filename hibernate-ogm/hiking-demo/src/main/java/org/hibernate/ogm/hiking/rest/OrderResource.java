package org.hibernate.ogm.hiking.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.ogm.hiking.model.business.Order;
import org.hibernate.ogm.hiking.repository.business.OrderRepository;

@Path("/orders")
@Stateless
public class OrderResource {

	@Inject
	private OrderRepository orderRepository;

	public OrderResource() {
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public long createOrder() {
		Order order = orderRepository.createOrder();
		return order.id;
	}
	@GET
	@Path("/")
	@Produces("application/json")
	public List<Order> getAllOrders() {
		return orderRepository.getAllOrders();
	}
}
