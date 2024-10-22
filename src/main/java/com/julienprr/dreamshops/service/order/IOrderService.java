package com.julienprr.dreamshops.service.order;

import com.julienprr.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrderById(Long orderId);
}
