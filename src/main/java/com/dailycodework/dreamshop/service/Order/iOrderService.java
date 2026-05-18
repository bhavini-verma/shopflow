package com.dailycodework.dreamshop.service.Order;

import com.dailycodework.dreamshop.model.Order;
import com.dailycodework.dreamshop.dto.OrderDto;
import java.util.List;

public interface iOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
