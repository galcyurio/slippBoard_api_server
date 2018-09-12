package net.slipp.repository.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

/**
 * @author galcyurio
 */
@Entity
data class User(
    private val username: String,
    private val password: String,

    @ElementCollection(fetch = FetchType.EAGER)
    private val authorities: Collection<SimpleGrantedAuthority>
) : UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities.toMutableSet()
    override fun isEnabled(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}