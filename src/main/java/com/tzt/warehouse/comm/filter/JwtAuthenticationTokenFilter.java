package com.tzt.warehouse.comm.filter;

import com.tzt.warehouse.comm.Enum.WareHouseEnum;
import com.tzt.warehouse.comm.utlis.JwtUtil;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.entity.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author：帅气的汤
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //    获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token) || "/api/user/refreshToken".equals(requestURI) || "/api/user/logout".equals(requestURI)) {
            //    放行
            filterChain.doFilter(request, response);
            return;
        }


        //    解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //    从redis中获取用户信息
        String redisKey = WareHouseEnum.LOGIN_KEY + userId;
        LoginUser user = redisCache.getCacheObject(redisKey);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户未登录");
        }
        //存到ThreadLocal
        UserUtlis.set(user);
        //    存入SecurityContextHolder
        //TODO 获取权限信息封装到 Authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //    放行
        filterChain.doFilter(request, response);
    }
}
