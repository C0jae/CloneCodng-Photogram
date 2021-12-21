package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 로그인이 필요한 페이지의 경우 interceptor 하여 로그인 페이지로 이동
@EnableWebSecurity  // 해당 파일로 시큐리티 활성화
@Configuration  // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    // 비밀번호 해시
    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // configure(HttpSecurity http)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // super 삭제 - 기존 시큐리티가 가지고 있는 기능이 비활성화

       // csrf token 비활성화
        http.csrf().disable();

       http.authorizeRequests()
        .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/auth/signin")
        .defaultSuccessUrl("/");
    }
}
