package com.hanghae.springboard.config;

import com.hanghae.springboard.exception.GlobalExecptionHandler;
import com.hanghae.springboard.jwt.JwtAuthFilter;
import com.hanghae.springboard.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final GlobalExecptionHandler globalExecptionHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 아래처럼 경로를 모아서 한번에 풀어줌
    }

    /**
     * .requestMatchers(PathRequest.toH2Console())와 같은 설정은
     * securityFilterChain에서 .antMatchers("/h2-console/**").permitAll()로도 표기가능하다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf 설정이 돼 있으면 html에서 CSRF 토큰 값 넘겨줘야 함 : cookie 기반이라 RESTapi는 disable 가능
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/post/**").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // jwt를 이용하여 username,password를 인증한다.


//        http.formLogin().loginPage("/api/auth/login").permitAll(); //로그인 페이지 : form이 있어야 가능,그냥 formLogin만 사용하면 loginPage 없어도됨
//        http.addFilterBefore(new CustomSecurityFilter(userDatailService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class); 써주면 custom한 security filter 쓸수있다.

        http.exceptionHandling().accessDeniedPage("/api/auth/forbidden"); // 인증불가시 보낼곳

        // 401 Error 처리, Authorization 즉, 인증과정에서 실패할 시 처리
//        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);

        // 403 Error 처리, 인증과는 별개로 추가적인 권한이 충족되지 않는 경우
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);


        return http.build();
    }

}
