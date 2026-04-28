package mu.architecture.modulith.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.json.JsonMapper

@Configuration
class JsonMapperConfig {

    @Bean
    fun objectMapper(): JsonMapper {
        return JsonMapper.builder().findAndAddModules().build()
    }
}