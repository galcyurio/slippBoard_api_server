package net.slipp.config

import net.slipp.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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

    override fun configure(http: HttpSecurity) {
        // TODO: dev 일 경우만 H2 관련 설정하기
        http.authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .and()
            .csrf().ignoringAntMatchers("/h2-console/**")
            .and()
            .headers().frameOptions().sameOrigin()

        http.userDetailsService(userService)
            .csrf().disable()
            .anonymous().and()
            .authorizeRequests()
            .antMatchers("/public").permitAll()
            .antMatchers(HttpMethod.POST, "/signOn").anonymous()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
    }
}