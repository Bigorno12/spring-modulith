package mu.architecture.modulith.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface GenericRepository<T : Any>: JpaRepository<T, Long>