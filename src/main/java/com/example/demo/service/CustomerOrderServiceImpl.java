package com.example.demo.service;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public void createCustomerOrder(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
    }

    @Override
    public List<CustomerOrder> listAllCustomerOrders() {
        return customerOrderRepository.findAll();
    }

    @Override
    public CustomerOrder findCustomerOrderById(Long id) {
        return customerOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado."));
    }

    @Override
    public void updateCustomerOrder(Long id, CustomerOrder updatedOrder) {
        CustomerOrder existingOrder = findCustomerOrderById(id);

        // Actualiza los campos básicos
        existingOrder.setCustomerEmail(updatedOrder.getCustomerEmail());
        existingOrder.setCustomerAddress(updatedOrder.getCustomerAddress());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setDeliveryStatus(updatedOrder.getDeliveryStatus());

        // Actualiza los ítems de forma controlada
        existingOrder.getItems().clear(); // Elimina los ítems actuales
        if (updatedOrder.getItems() != null) {
            existingOrder.getItems().addAll(updatedOrder.getItems()); // Agrega los nuevos ítems
        }

        // Actualiza los pagos de forma controlada
        existingOrder.getPayments().clear(); // Elimina los pagos actuales
        if (updatedOrder.getPayments() != null) {
            existingOrder.getPayments().addAll(updatedOrder.getPayments()); // Agrega los nuevos pagos
        }

        customerOrderRepository.save(existingOrder); // Guarda los cambios
    }


    @Override
    public void deleteCustomerOrder(Long id) {
        customerOrderRepository.deleteById(id);
    }

    @Override
    public void addItemToCustomerOrder(Long id, OrderItem item) {
        CustomerOrder order = findCustomerOrderById(id);
        order.getItems().add(item);
        customerOrderRepository.save(order);
    }

    @Override
    public void removeItemFromCustomerOrder(Long id, Long itemId) {
        CustomerOrder order = findCustomerOrderById(id);
        order.getItems().removeIf(item -> item.getId().equals(itemId));
        customerOrderRepository.save(order);
    }

    @Override
    public void updateDeliveryStatus(Long id, String deliveryStatus) {
        CustomerOrder order = findCustomerOrderById(id);
        order.setDeliveryStatus(deliveryStatus);
        customerOrderRepository.save(order);
    }

    @Override
    public Double calculateCustomerOrderTotal(Long id) {
        CustomerOrder order = findCustomerOrderById(id);
        return order.getItems().stream()
                .mapToDouble(item -> item.getProductPrice().doubleValue() * item.getQuantity())
                .sum();
    }
}
