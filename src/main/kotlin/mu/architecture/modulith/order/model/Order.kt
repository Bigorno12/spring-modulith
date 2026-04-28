package mu.architecture.modulith.order.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import mu.architecture.modulith.common.audit.Auditable

@Entity
@Table(name = "order")
class Order : Auditable() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "order_number", unique = true, nullable = false)
    var orderNumber: String? = null

    @Column(name = "customer_name", nullable = false)
    var customerName: String? = null

    @Column(name = "customer_email", nullable = false)
    var customerEmail: String? = null

    @Column(name = "customer_phone", nullable = false)
    var customerPhone: String? = null

    @Column(name = "delivery_address", nullable = false)
    var deliveryAddress: String? = null

    @Column(name = "product_code", nullable = false)
    var productCode: String? = null

    @Column(name = "product_name", nullable = false)
    var productName: String? = null

    @Column(name = "product_price", nullable = false)
    var productPrice: Double? = null

    @Column(name = "quantity", nullable = false)
    var quantity: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: Status? = null

    @Column(name = "comments")
    var comments: String? = null
}