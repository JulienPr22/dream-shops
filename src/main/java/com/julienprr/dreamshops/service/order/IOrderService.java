package com.julienprr.dreamshops.service.order;

import com.julienprr.dreamshops.dto.OrderDto;
import com.julienprr.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
