package com.example.demo.service;

import com.example.demo.command.CreateOrderCommand;
import com.example.demo.model.Client;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private ItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            ProductRepository productRepository,
                            ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    public Order save(CreateOrderCommand createOrderCommand) {

        Order orderNew = new Order();
        orderNew.setClient(findClient(createOrderCommand.getClientId()));
        orderNew.setTotalPrice(createOrderCommand.getTotalPrice());
        final Order order = orderRepository.save(orderNew);

        if (createOrderCommand.getItems() != null) {
            createOrderCommand.getItems().forEach(
                    itemC -> {
                        Item item = new Item();
                        item.setQuantity(itemC.getQuantity());
                        item.setOrder(order);
                        item.setProduct(findProduct(itemC.getIdProduct()));
                        order.getItems().add(itemRepository.save(item));
                    }
            );
        }
        return convertToReturn(order);
    }

    public Client findClient(Long id) {
        if (id == null)
            return null;
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            return clientOpt.get();
        } else {
            return clientRepository.save(new Client(id, "Novo cliente : " + id));
        }
    }


    public Product findProduct(Long id) {
        if (id == null)
            return null;
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            return productOpt.get();
        } else {
            return productRepository.save(new Product(id, "Novo produto : " + id, 0.0));
        }
    }


    /****
     * Criar um endpoint GET /orders/{id} para buscar um pedido pelo ID.
     * @return
     */
    public Order get(Long id)  {
        return convertToReturn(orderRepository.findById(id));
    }

    /****
     * Criar um endpoint GET /orders?customerId=123 para listar pedidos de um cliente.
     * @return
     */
    public List<Order> findByCostumer(Long idCostumer) {
        return orderRepository
                .findByClientId(idCostumer)
                .stream()
                .map(this::convertToReturn)
                .toList();
    }

    private Order convertToReturn(Optional<Order> orderOpt) {
        if (orderOpt.isPresent()) {
            return convertToReturn(orderOpt.get());
        } else {
            return null;
        }
    }

    private Order convertToReturn(Order order) {

        order.setItems(order
                .getItems()
                .stream()
                .map(e -> {
            e.setOrder(null);
            return e;
        }).toList());
        return order;
    }

}
