package mu.architecture.modulith.order.service

import mu.architecture.modulith.order.dto.OrderSaveRequest

interface OrderService {
     fun save(orderSaveRequest: OrderSaveRequest?)
}