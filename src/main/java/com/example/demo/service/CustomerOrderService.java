package com.example.demo.service;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.OrderItem;

import java.util.List;

public interface CustomerOrderService {
    void createCustomerOrder(CustomerOrder customerOrder);

    List<CustomerOrder> listAllCustomerOrders();

    CustomerOrder findCustomerOrderById(Long id);

    void updateCustomerOrder(Long id, CustomerOrder updatedOrder);

    void deleteCustomerOrder(Long id);

    void addItemToCustomerOrder(Long id, OrderItem item);

    void removeItemFromCustomerOrder(Long id, Long itemId);

    void updateDeliveryStatus(Long id, String deliveryStatus);

    Double calculateCustomerOrderTotal(Long id);
}
