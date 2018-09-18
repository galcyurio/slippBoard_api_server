package net.slipp.repository.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * @author galcyurio
 */
@Entity
data class Board(
    val title: String,
    val content: String = ""
) : Auditable() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: User? = null
}