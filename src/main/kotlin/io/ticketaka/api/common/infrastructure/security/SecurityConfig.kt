package io.ticketaka.api.common.infrastructure.security

import io.ticketaka.api.user.application.CustomOAuth2UserService
import io.ticketaka.api.user.application.CustomOidcUserService
import io.ticketaka.api.user.infrastructure.CustomAuthorityMapper
import io.ticketaka.api.user.infrastructure.jwt.JwtAuthenticationFilter
import io.ticketaka.api.user.infrastructure.jwt.JwtExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
class SecurityConfig(
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customOidcUserService: CustomOidcUserService,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    /*
     *
Security filter chain: [
  DisableEncodeUrlFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextHolderFilter
  HeaderWriterFilter
  CorsFilter
  LogoutFilter
  OAuth2AuthorizationRequestRedirectFilter
  OAuth2LoginAuthenticationFilter
  JwtAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  JwtExceptionFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]

     * */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().permitAll() } // TODO oauth 구현이후 endpoint별 권한 설정 필요
            .formLogin {
                it
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProc")
                    .defaultSuccessUrl("/")
                    .permitAll()
            }.oauth2Login { oauth2LoginCustomizer ->
                oauth2LoginCustomizer.userInfoEndpoint { userInfoEndpointConfig ->
                    userInfoEndpointConfig
                        .userService(customOAuth2UserService)
//                        .oidcUserService(null)
                }
            }.exceptionHandling { it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }
            .logout { it.logoutSuccessUrl("/") }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, ExceptionTranslationFilter::class.java)
        return http.build()
    }

    @Bean
    fun exceptionTranslationFilter(): ExceptionTranslationFilter {
        val filter = ExceptionTranslationFilter(authenticationEntryPoint)
        filter.setAccessDeniedHandler(accessDeniedHandler)
        return filter
    }

    @Bean
    fun customAuthorityMapper(): GrantedAuthoritiesMapper = CustomAuthorityMapper()
}
