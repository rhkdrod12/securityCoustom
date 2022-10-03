package com.example.practicejpa.config;

import com.example.practicejpa.auth.CustomAccessDeniedHandler;
import com.example.practicejpa.auth.authentication.CustomAuthenticationEntryPoint;
import com.example.practicejpa.auth.authentication.CustomAuthenticationFailureHandler;
import com.example.practicejpa.auth.authentication.CustomAuthenticationProcessingFilter;
import com.example.practicejpa.auth.authentication.CustomAuthenticationProvider;
import com.example.practicejpa.auth.authentication.CustomAuthenticationSuccessHandler;
import com.example.practicejpa.auth.authentication.CustomLogoutHandler;
import com.example.practicejpa.auth.authentication.CustomLogoutSuccessHandler;
import com.example.practicejpa.auth.authentication.JwtAuthFilter;
import com.example.practicejpa.utils.Jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    JwtProvider jwtProvider;
    
    /*
     * 스프링 시큐리는 먼저 인증로직을 처리 후 인가로직을 처리한다.(필터기반)
     *
     * 인증 로직
     *
     * SecurityContextPersistenceFilter 에서 먼저 세션정보가 있는지 확인
     * -> 세션이 없다면 UsernamePasswordAuthenticationFilter(기본값)으로 인증시작
     *
     * UsernamePasswordAuthenticationFilter 는 <- 인증 또는 예외를 던지는 필터임
     * 넘어온 username과 password로 UsernamePassworkdAutehnticationToekn(AuthenticationToken)을 생성
     * 그리고 ProviderManager(AuthenticationManager)를 호출하여 ProviderManager안에 있는 AuthenticationProvider를 이용해
     * 완전한 Authentication 생성
     * (Authentication에는 Principal, Credentials, Authorities, Details 로 구성되어있음)
     *
     * -> 세션이 있으면 위의 로직 통과
     *
     * -> 인증이 끝나면 다음으로 인가 로직을 호출하는데 시큐리티 필터 중 마지막 단계임
     * FilterSecurityInterceptor(인가 로직 시작 필터) <- 이놈이 인가 혹은 예외를 던짐
     *
     * FilterSecurityInterceptor는 AccessDecisionManager 를 호출하며
     * 이 AccessDecisionManager 는 AccessDecisionVoter 를 호출하여 해당 유저의 권한를 체크하게 된다.
     * 권한체크 방식에는 3가지가 있는데
     * AffirmativeBased : 여러 voter중에서 하나라도 충족하면 허용(기본값)
     * ConsensusBased   : 다수결
     * UnanimousBased   : 만장일치
     *
     *
     * 임의로 커스텀필터를 등록시키는 방법에는 3가지가 있음
     * addFilterBefore  : 해당 클래스 이전에 삽입
     * addFilterAfter   : 해당 클래스 이후에 삽입
     * addFilterAt      : 해당 클래스에 삽입(오버라이딩은 아님) -> 하지만 커스텀 필터로 인해서 인증이 완료되면 다음 로직을 타지 않기 때문에 자연스럽게 오버라이딩처럼 동작함
     *
     * loginProcessingUrl 해당 url로 들어온 post요청을 처리하는 곳이기 때문에
     * 해당부분으로 보내버리면 되는거였네...
     * 훨씬 더 간편하게 로그인과 로그아웃 구현이 되는거였는데.. 괜히 고생했구만...
     */
    
    
    /*
     * 여기에 작성되면 해당 url는 인증을 하지 않음
     * resources 모든 접근을 허용하는 설정을 해버리면
     * HttpSecurity 설정한 ADIM권한을 가진 사용자만 resources 접근가능한 설정을 무시해버린다.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://192.168.10.79:3000");
        configuration.addAllowedOrigin("http://127.0.0.1:3000");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Content-Disposition");
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.httpBasic().disable()
    
            //로그인
            .formLogin()
            // .loginProcessingUrl("/login")
            // .successHandler(customAuthenticationSuccessHandler())   //굳이 복잡하게 할 필요가 없었군....
            // .failureHandler(customAuthenticationFailureHandler())
            //로그아웃
            .and()
            .logout()
            .logoutUrl("/logout")
            .addLogoutHandler(logoutHandler())
            .logoutSuccessHandler(customLogoutSuccessHandler())
            //크로스정책 끄기
            .and().cors().configurationSource(corsConfigurationSource())
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 금지
            .and()
    
            //인증 및 인가
            .authorizeRequests()
            .antMatchers("/get", "/insertUser", "/menu/**").permitAll()
            .anyRequest().authenticated()
    
            // .accessDecisionManager()
    
            //에러
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)   //인증 실패시 보낼 핸들러
            .accessDeniedHandler(customAccessDeniedHandler)
            //굳이 필터를 만들 필요가 없었어 ㅠㅠ
            .and()
            // 토큰이 존재하는 경우 인증과정을 통과시키기 위한 JWT인증 필터를 추가
            .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            // 로그인 과정을 처리하기위한 필터 추가
            .addFilterAt(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    
    // 커스텀 인증 필터
    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter("/login");
        filter.setAuthenticationManager(customAuthenticationManager());
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        return filter;
    }
    
    @Bean
    public LogoutHandler logoutHandler(){
        return new CustomLogoutHandler();
    }
    
    // 로그아웃 성공 핸들러
    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler(){return new CustomLogoutSuccessHandler();}
    
    // 로그인 실패 핸들러 
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    
    // 로그인 성공 핸들러
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
    
    // 커스텀 인증 매니저
    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
        // return new CustomAuthenticationManager(customAuthenticationProvider());
    }
    
    // 커스텀 인증 제공자
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }
    
    // AuthenticationProvider를 지정하는 부분
    // AuthenticationManager에서 AuthenticationProvider를 가지고 인증객체(Authentication)를 생성
    // Authentication는 Principal(유저정보), Credentials(인증정보), Authorities(권한정보)정보를 가지고 있음
    // auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    // -> userDetailsService를 사용하는 것은 자동으로 DaoAuthenticationProvider로 구현하여 AuthenticationProvider를 사용하는 것
    
    // 어차피 위에 새로운 ProviderManager 쓰도록 선언해놨으니까 필요없겠네
    // 이것저것 해봅시다
    // 어차피 같을거 같기는한데 어차피 ProviderManager로 선언되어있던거니까..
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(customAuthenticationProvider());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
