package com.imooc.miaosha.config;
import com.imooc.miaosha.common.CodeMsg;
import com.imooc.miaosha.common.Result;
import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.MiaoShaUserKey;
import com.imooc.miaosha.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    RedisService redisService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,COOKIE_NAME_TOKEN);
        if(StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)){
            return Result.error( CodeMsg.PARAMETER_ERROR);
        }
        String token = StringUtils.isBlank(paramToken)?cookieToken:paramToken;
        MiaoShaUser miaoShaUser = redisService.get(MiaoShaUserKey.token,token,MiaoShaUser.class);
        if(miaoShaUser != null){
            // 延长有效期，重新设置cookie
            Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
            cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return null;
    }

    private String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
