package mu.architecture.modulith.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.json.JsonMapper

@Configuration
class RabbitMQConfiguration {

    companion object {
        const val EXCHANGE_NAME: String = "orders"
    }

    @Bean
    fun exchange(): Exchange {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME)
            .build()
    }

    @Bean
    fun queue(): Queue {
        return QueueBuilder.durable(EXCHANGE_NAME).build()
    }

    @Bean
    fun binding(queue: Queue, exchange: Exchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(EXCHANGE_NAME).noargs()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory, objectMapper: JsonMapper): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = producerJackson2MessageConverter(objectMapper)
        return rabbitTemplate
    }

    @Bean
    fun producerJackson2MessageConverter(objectMapper: JsonMapper): JacksonJsonMessageConverter {
        return JacksonJsonMessageConverter(objectMapper)
    }
}