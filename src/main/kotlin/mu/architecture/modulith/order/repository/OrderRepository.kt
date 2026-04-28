package mu.architecture.modulith.order.repository

import mu.architecture.modulith.common.repository.GenericRepository
import mu.architecture.modulith.order.model.Order
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : GenericRepository<Order>