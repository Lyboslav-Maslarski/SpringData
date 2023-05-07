package example.services;

import example.entities.Order;

import java.util.Optional;

public interface OrderService {
    Optional<Order> getOrder(int userId);

    void saveOrder(Order order);

    void deleteOrder(Order order);
}
