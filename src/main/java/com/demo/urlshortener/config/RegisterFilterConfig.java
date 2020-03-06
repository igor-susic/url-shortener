package com.demo.urlshortener.config;

import com.demo.urlshortener.filters.ShortUrlInterceptorFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterFilterConfig {
    /**
     * Here we just register the filter, for other configurations like order of applying it,
     * take a look at the ShortUrlInterceptorFilter class.
     */
    @Bean
    public FilterRegistrationBean<ShortUrlInterceptorFilter> filterRegistrationBean() {
        FilterRegistrationBean <ShortUrlInterceptorFilter> registrationBean = new FilterRegistrationBean();
        ShortUrlInterceptorFilter customURLFilter = new ShortUrlInterceptorFilter();

        registrationBean.setFilter(customURLFilter);
        return registrationBean;
    }
}
