package mu.architecture.modulith.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
class AuditConfiguration {

    @Bean
    fun dateTimeProvider(): DateTimeProvider {
        return DateTimeProvider {
            Optional.of(LocalDateTime.now())
        }
    }

}