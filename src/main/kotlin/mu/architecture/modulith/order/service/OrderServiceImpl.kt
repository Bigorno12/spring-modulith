package mu.architecture.modulith.order.service

import mu.architecture.modulith.common.exception.ExceedAttemptException
import mu.architecture.modulith.common.util.OrderNumberUtil.generateOrderNumber
import mu.architecture.modulith.order.dto.OrderSaveRequest
import mu.architecture.modulith.order.event.OrderEvent
import mu.architecture.modulith.order.mapper.OrderMapper
import mu.architecture.modulith.order.model.Order
import mu.architecture.modulith.order.model.Status
import mu.architecture.modulith.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val eventPublisher: ApplicationEventPublisher
) :
    OrderService {

    companion object {
        private val log = LoggerFactory.getLogger(OrderServiceImpl::class.java)
    }

    @Transactional
    override fun save(orderSaveRequest: OrderSaveRequest?) {
        val orderNumber: String = generateOrderNumber()
        orderMapper.saveOrderToEntity(orderSaveRequest, orderNumber)
            .let { orderRepository.save(it) }
            .also { publishSaveOrderView(it) }
    }

    private tailrec fun generatingOrderNumber(attempts: Int = 0): String? {
        require(attempts < 20) { throw ExceedAttemptException("Failed to generate order number after $attempts attempts") }
        val orderNumber: String = generateOrderNumber()
        return if (orderRepository.existsOrderByOrderNumberAndStatusIn(
                orderNumber, mutableSetOf(
                    Status.ACTIVE,
                    Status.ON_ROUTE
                )
            )
        ) {
            generatingOrderNumber(attempts + 1)
        } else {
            orderNumber
        }
    }
    
    private fun publishSaveOrderView(order: Order) {
        val event: OrderEvent = orderMapper.toEvent(order)
        log.info("Publishing event for order: {}", event.orderNumber)
        eventPublisher.publishEvent(event)
    }
}