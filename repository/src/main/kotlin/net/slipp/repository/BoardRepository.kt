package net.slipp.repository

import net.slipp.repository.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author galcyurio
 */
@Repository
interface BoardRepository : JpaRepository<Board, Long>