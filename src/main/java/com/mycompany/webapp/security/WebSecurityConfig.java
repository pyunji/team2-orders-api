package com.mycompany.webapp.security;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {	
		log.info("configure(HttpSecurity http) 실행");
		//폼 로그인 비활성화
		http.formLogin().disable();
		
		//사이트간 요청 위조 방지 비활성화
		http.csrf().disable();
		
		//요청 경로 권한 설정
		http.authorizeRequests()
//			.antMatchers("/backoffice/**").hasAuthority("ROLE_MANAGER")
//			.antMatchers("/member/**").authenticated() //로그인된(인증된) 모든 사용자 접근 가능
			.antMatchers("/**").permitAll();
		
		//세션 비활성화
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//JwtCheckFilter 추가(아이디/비밀번보 체크 필터 이전에 있어야 함)
		JwtCheckFilter jwtCheckFilter = new JwtCheckFilter();
		http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);
		
		//CORS 설정 활성화
		http.cors();
	}
	
	@Autowired @Qualifier("db1DataSource") DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("configure(AuthenticationManagerBuilder auth) 실행");
		
		//DB에서 가져올 사용자 정보 설정
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT mid, mpassword, menabled FROM member WHERE mid=?")
			.authoritiesByUsernameQuery("SELECT mid, mrole FROM member WHERE mid=?")
			.passwordEncoder(passwordEncoder()); //default: DelegatingPasswordEncoder
	}
	
	@Bean
	public RoleHierarchyImpl roleHierarchyImpl() {
		log.info("실행");
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
		return roleHierarchyImpl;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("configure(WebSecurity web) 실행");
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchyImpl());	
		web.expressionHandler(defaultWebSecurityExpressionHandler);
		web.ignoring()
		.antMatchers("/images/**")
		.antMatchers("/css/**")
		.antMatchers("/js/**")
		.antMatchers("/bootstrap-4.6.0-dist/**")
		.antMatchers("/jquery/**")
		.antMatchers("/favicon.ico");		
	}	
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		//모든 요청에서 conf에 설정한대로 확인하겠다는 의미
		CorsConfiguration conf = new CorsConfiguration();
		
		//설정1) 모든 요청 사이트 허용
		conf.addAllowedOrigin("*");
		//설정2) 모든 요청 방식 허용: GET, POST, PUT, DELETE ...
		conf.addAllowedMethod("*");
		//설정3) 모든 요청 헤더 허용
		conf.addAllowedHeader("*");
		
		//모든 URL 요청에 대해서 위 내용을 적용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", conf);
		return source;
	}
			
}