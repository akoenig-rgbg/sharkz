package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Order;
import de.othr.sw.sharkz.entity.OrderItem;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class OrderService extends ServicePrototype {

    @Transactional(TxType.REQUIRED)
    public void createOrder(List<OrderItem> items, Customer customer) {
        Order order = new Order();
        
        order.setCustomer(customer);
        order.setItems(items);
        order.setDate(new Date(System.currentTimeMillis()));
        
        em.persist(order);
    }
    
    public Order findOrder(long id) {
        return em.find(Order.class, id);
    }
    
    public List<Order> findOrders(Customer customer) {
        Query qu = em.createQuery("SELECT o FROM Order AS o WHERE o.customer ="
                + ":customer", Order.class);
        
        return qu.getResultList();
    }
}
