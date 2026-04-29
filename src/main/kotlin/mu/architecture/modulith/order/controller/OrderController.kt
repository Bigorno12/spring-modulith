package mu.architecture.modulith.order.controller

import mu.architecture.modulith.order.dto.OrderView
import mu.architecture.modulith.order.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(version = "1.0", value = ["/api/v1/order"], produces = ["application/json"])
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun save(@RequestBody orderView: OrderView) : ResponseEntity<Void> {
        orderService.save(orderView)
        return ResponseEntity.ok().build()
    }
}