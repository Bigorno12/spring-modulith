package mu.architecture.modulith.order.service

import mu.architecture.modulith.order.dto.OrderResponse
import mu.architecture.modulith.order.dto.OrderSaveRequest

interface OrderService {
     fun save(orderSaveRequest: OrderSaveRequest?)

     fun findOrder(orderId: String): OrderResponse?
}