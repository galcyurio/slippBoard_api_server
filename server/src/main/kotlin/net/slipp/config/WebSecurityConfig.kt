package net.slipp.config

import net.slipp.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * @author galcyurio
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    val userService: UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
            .and()
            .jdbcAuthentication()
    }

    override fun configure(http: HttpSecurity) {
        // TODO: dev 일 경우만 H2 관련 설정하기
        http.authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .and()
            .csrf().ignoringAntMatchers("/h2-console/**")
            .and()
            .headers().frameOptions().sameOrigin()

        http.authorizeRequests()
            .antMatchers("/public").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
    }
}