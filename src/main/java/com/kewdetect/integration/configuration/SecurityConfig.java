/*
 * Copyright (c) 2019, 2020 kewmann.com and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kewdetect.integration.configuration;

import com.kwm.configuration.JWTAuthorizationFilter;
import com.kwm.configuration.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * KAI.
 *
 * @author longtran
 * @version 1.0
 * @created_by longtran
 * @created_date 2/25/19 5:30 PM
 * @updated_by longtran
 * @updated_date 2/25/19 5:30 PM
 * @since 2/25/19
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // Custom JWT based security filter
    private final JWTAuthorizationFilter jWTAuthorizationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JWTAuthorizationFilter jWTAuthorizationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jWTAuthorizationFilter = jWTAuthorizationFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.addFilterBefore(new CorsFilterOncePerRequestFilter(), ChannelProcessingFilter.class);
//        httpSecurity.cors().configurationSource((HttpServletRequest request) -> {
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowedHeaders(Collections.singletonList("*"));
//            config.setAllowedMethods(Collections.singletonList("*"));
//            config.addAllowedOrigin("*");
//            config.setAllowCredentials(true);
//            return config;
//        });
        httpSecurity.authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
                        "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**")
                .permitAll()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
                .hasRole("ACTUATOR_ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/*", "/api/v1/flowable/bpmn/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(jWTAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().cacheControl()
                .disable();
        httpSecurity.cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/public/**");
        web.ignoring().antMatchers("api/v2/api-docs", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        //configuration.setAllowedOrigins(ImmutableList.of("http://localhost:8080","http://localhost:8084"));
//        configuration.setAllowedOrigins(ImmutableList.of("*"));
//        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
