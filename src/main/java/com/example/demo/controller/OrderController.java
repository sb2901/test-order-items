package com.example.demo.controller;

import com.example.demo.command.CreateOrderCommand;
import com.example.demo.model.Order;
import com.example.demo.service.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl service;

    public OrderController(OrderServiceImpl service) {
        this.service = service;
    }

    /**
     * Criar um endpoint GET /orders/{id} para buscar um pedido pelo ID.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    /**
     * Criar um endpoint GET /orders?customerId=123 para listar pedidos de um cliente.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getOrderByClient(
            @RequestParam(name = "customerId") Long id) {
        return ResponseEntity.ok(service.findByCostumer(id));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody CreateOrderCommand order) {
        return ResponseEntity.ok(service.save(order));
    }

}
