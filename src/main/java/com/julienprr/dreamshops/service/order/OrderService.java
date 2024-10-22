package com.julienprr.dreamshops.service.order;

import com.julienprr.dreamshops.enums.OrderStatus;
import com.julienprr.dreamshops.exceptions.InsufficientStockException;
import com.julienprr.dreamshops.exceptions.ResourceNotFoundException;
import com.julienprr.dreamshops.model.Cart;
import com.julienprr.dreamshops.model.Order;
import com.julienprr.dreamshops.model.OrderItem;
import com.julienprr.dreamshops.model.Product;
import com.julienprr.dreamshops.repository.OrderRepository;
import com.julienprr.dreamshops.repository.ProductRepository;
import com.julienprr.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCartById(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        // TODO: set the user
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            if (product.getInventory() >= cartItem.getQuantity()) {
                product.setInventory(product.getInventory() - cartItem.getQuantity());
                productRepository.save(product);
                return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
            } else {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList) {
        return orderItemsList
                .stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

        public List<Order> getUSerOrders(Long userId) {
        return  orderRepository.findByUserId(userId);
        }

}
