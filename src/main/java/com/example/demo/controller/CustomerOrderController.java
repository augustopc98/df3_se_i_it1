package com.example.demo.controller;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.OrderItem;
import com.example.demo.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerOrders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    // Crear un nuevo pedido
    @PostMapping
    public void createCustomerOrder(@RequestBody CustomerOrder customerOrder) {
        customerOrderService.createCustomerOrder(customerOrder);
    }

    // Obtener todos los pedidos
    @GetMapping
    public List<CustomerOrder> getAllCustomerOrders() {
        return customerOrderService.listAllCustomerOrders();
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public CustomerOrder getCustomerOrderById(@PathVariable Long id) {
        return customerOrderService.findCustomerOrderById(id);
    }

    // Actualizar un pedido existente
    @PutMapping("/{id}")
    public void updateCustomerOrder(@PathVariable Long id, @RequestBody CustomerOrder updatedOrder) {
        customerOrderService.updateCustomerOrder(id, updatedOrder);
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public void deleteCustomerOrder(@PathVariable Long id) {
        customerOrderService.deleteCustomerOrder(id);
    }

    // Agregar un artículo a un pedido
    @PostMapping("/{id}/items")
    public void addItemToCustomerOrder(@PathVariable Long id, @RequestBody OrderItem item) {
        customerOrderService.addItemToCustomerOrder(id, item);
    }

    // Eliminar un artículo de un pedido
    @DeleteMapping("/{id}/items/{itemId}")
    public void removeItemFromCustomerOrder(@PathVariable Long id, @PathVariable Long itemId) {
        customerOrderService.removeItemFromCustomerOrder(id, itemId);
    }

    // Actualizar el estado de entrega de un pedido
    @PatchMapping("/{id}/deliveryStatus")
    public void updateDeliveryStatus(@PathVariable Long id, @RequestBody String deliveryStatus) {
        customerOrderService.updateDeliveryStatus(id, deliveryStatus);
    }

    // Calcular el total del pedido
    @GetMapping("/{id}/total")
    public Double calculateCustomerOrderTotal(@PathVariable Long id) {
        return customerOrderService.calculateCustomerOrderTotal(id);
    }
}
