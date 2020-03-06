package com.demo.urlshortener.filters;

import com.demo.urlshortener.models.PairReturnType;
import com.demo.urlshortener.services.impl.UrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Component
@Order(1)
public class ShortUrlInterceptorFilter implements Filter {

    @Autowired
    private UrlServiceImpl urlService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequest);
        HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);

        String requestedUri = httpServletRequest.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        if (requestedUri.equals("/account") || requestedUri.equals("/register") || antPathMatcher.match("/statistics/**", requestedUri) || requestedUri.equals("/help")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            /**
             * We use requestedUri.substring(1) because requestedUri includes / at the begining, and API of urlService does
             * not work with it.
             */
            PairReturnType destinationUrl = urlService.getDestinationAndRedirectType(requestedUri.substring(1));

            if (!destinationUrl.isValid()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                /**
                 * We can't use httpServletResponse.sendRedirect() as we loose the ability to set redirect type
                 */
                httpServletResponse.setStatus(destinationUrl.getRedirectType());
                httpServletResponse.setHeader("Location", destinationUrl.getUrl());
                urlService.registerVisitForUrl(requestedUri.substring(1));
            }
        }
    }
}
