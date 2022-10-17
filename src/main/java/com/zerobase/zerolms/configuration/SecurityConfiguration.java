package com.zerobase.zerolms.configuration;

import com.zerobase.zerolms.main.service.LogHistoryService;
import com.zerobase.zerolms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RequiredArgsConstructor
@EnableWebSecurity(debug = false) // 스프링 시큐리티 필터가 스프링 필터 체임에 등록이 된다.
@Configuration
public class SecurityConfiguration {

    private final MemberService service;
    private final LogHistoryService logHistoryService;


    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    UserLoginSuccessHandler getSuccessHandler() {
        return new UserLoginSuccessHandler(logHistoryService);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(
                        "/"
                        , "/member/register"
                        , "/member/email-auth"
                        , "/member/find/password"
                        , "/member/reset/password"


                )
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/member/login")
                .successHandler(getSuccessHandler())
                .failureHandler(getFailureHandler())
                .permitAll()
                .and().logout().logoutRequestMatcher(
                        new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)

                .and().headers().frameOptions().sameOrigin();

        http.exceptionHandling().accessDeniedPage("/error/denied");
        return http.build();
    }


    // 웹 ignore 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .mvcMatchers("/favicon.ico")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }




}
