package mu.architecture.modulith.order.service

import mu.architecture.modulith.common.util.OrderNumberUtil.generateOrderNumber
import mu.architecture.modulith.order.dto.OrderView
import mu.architecture.modulith.order.event.OrderEvent
import mu.architecture.modulith.order.mapper.OrderMapper
import mu.architecture.modulith.order.model.Order
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
    override fun save(orderView: OrderView?) {
        val orderNumber: String = generateOrderNumber()
        orderMapper.toEntity(orderView, orderNumber)
            .let { orderRepository.save(it) }
            .also { publishOrderView(it) }
    }

    private fun publishOrderView(order: Order) {
        val event: OrderEvent = orderMapper.toEvent(order)
        log.info("Publishing event for order: {}", event.orderNumber)
        eventPublisher.publishEvent(event)
    }

    private tailrec fun generatingOrderNumber(): String? {
        val copyOrderView: String = generateOrderNumber()
        return if (orderRepository.existsOrderByOrderNumber(copyOrderView)) {
            generatingOrderNumber()
        } else {
            copyOrderView
        }
    }
}