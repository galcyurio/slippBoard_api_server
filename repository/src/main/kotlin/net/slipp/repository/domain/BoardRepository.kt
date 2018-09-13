package net.slipp.repository.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author galcyurio
 */
@Repository
interface BoardRepository : JpaRepository<Board, Long>