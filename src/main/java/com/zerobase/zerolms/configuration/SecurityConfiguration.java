package com.zerobase.zerolms.configuration;

import com.zerobase.zerolms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfiguration {

    private final MemberService service;


    @Bean
    UserAuthenticationFailureHandler getFailureHandler(){
       return new UserAuthenticationFailureHandler();
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
                        ,"/member/email-auth"
                        ,"/member/find/password"
                        ,"/member/reset/password"
                        )
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/member/login")
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



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/favicon.ico","/files/**") ;
    }


    @Configuration
    public static class WebConfig extends WebMvcConfigurationSupport {

        @Override
        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
            super.addResourceHandlers(registry);
        }
    }

}
