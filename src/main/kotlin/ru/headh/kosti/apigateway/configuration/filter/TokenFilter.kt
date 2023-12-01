package ru.headh.kosti.apigateway.configuration.filter

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.headh.kosti.apigateway.service.TokenService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenFilter(
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        var token: String? = null
        var userId: Int? = null
        try {
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7)
                userId = tokenService.getUserId(token)
            }

            if (userId != null && SecurityContextHolder.getContext().authentication == null) {
                val upatoken = UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    emptyList()
                )
                SecurityContextHolder.getContext().authentication = upatoken
            }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }
}