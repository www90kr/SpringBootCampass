package com.campass.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.campass.demo.security.BuyerAccessDeniedHandler;
import com.campass.demo.security.BuyerLoginFailureHandler;
import com.campass.demo.security.BuyerLoginSuccessHandler;
import com.campass.demo.security.SellerAccessDeniedHandler;
import com.campass.demo.security.SellerLoginFailureHandler;
import com.campass.demo.security.SellerLoginSuccessHandler;

@EnableWebSecurity
public class MemberSecurityConfig {
	// 관리자는 /adim/**으로 접근한다
	@Order(1)
	@Configuration
	public static class AdminSecurityConfing extends WebSecurityConfigurerAdapter {
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("system").password(passwordEncoder.encode("1234")).roles("ADMIN");
		}	
		
		// 파라미터 http는 스프링 시큐리티를 이용한 접근 통제 정보를 저장할 객체
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/admin/login").permitAll();
			http.csrf().disable();
			// /admin/**로 들어오는 요청에 대해 ADMIN 권한과 폼 로그인 설정
			http.requestMatchers().antMatchers("/admin/**").and().authorizeRequests().anyRequest().hasRole("ADMIN")
				.and().formLogin().loginPage("/admin/login").loginProcessingUrl("/admin/login")
				.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
		}
	}
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(2)
	@Configuration
//	@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class sellerSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource sellerdataSource;
		// 로그인에 실패했을 때 뒷처리 -> 로그인실패횟수증가, 5회 실패하면 블록
		@Autowired
		private SellerLoginFailureHandler sellerLoginFailureHandler;

		// 로그인에 성공했을 때 뒷처리
		// 로그인 실패 횟수 리셋, 임시비밀번호로 로그인하면 비밀번호 변경창으로 이동
		@Autowired
		private SellerLoginSuccessHandler sellerLoginSuccessHandler;
		
		// 권한이 없을 때 뒷처리(403)
		// 403은 컨트롤러 어드바이스가 아니라 스프링 시큐리티가 처리
		@Autowired
		private SellerAccessDeniedHandler SaccessDeniedHandler;
		

		// 아이디, 비밀번호, enabled를 읽어오는 sql
		// 아이디와 권한 읽어오는 sql
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(sellerdataSource)
				.usersByUsernameQuery("select username, spassword, enabled from seller where username=?") //db에서 아이디, 비번 비교
				.authoritiesByUsernameQuery("select username, role from seller where username=?");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/seller/login","/seller/join", "/seller/check/id", "/seller/check/email", "/seller/check/businessNo"
					, "/seller/check/tel", "/seller/findBysId", "/seller/find/username", "/seller/reset/password", "/seller/new", "/seller/findBysId", "/seller/resetPassword"
					
					).permitAll();
			http.csrf().disable();
			http.requestMatchers().antMatchers("/seller/**").and().authorizeRequests().anyRequest().hasRole("SELLER")
				.and().exceptionHandling().accessDeniedHandler(SaccessDeniedHandler)
				.and().formLogin().loginPage("/seller/login").loginProcessingUrl("/seller/login").successHandler(sellerLoginSuccessHandler).failureHandler(sellerLoginFailureHandler)
				.and().logout().logoutUrl("/seller/logout").logoutSuccessUrl("/seller/home").invalidateHttpSession(true);
		
		}
	
	       
	    }
	
	
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(3)
	@Configuration
	//@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class buyerSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource buyerdataSource;
		// 로그인에 실패했을 때 뒷처리 -> 로그인실패횟수증가, 5회 실패하면 블록
		@Autowired
		private BuyerLoginFailureHandler buyerLoginFailureHandler;

		// 로그인에 성공했을 때 뒷처리
		// 로그인 실패 횟수 리셋, 임시비밀번호로 로그인하면 비밀번호 변경창으로 이동
		@Autowired
		private BuyerLoginSuccessHandler buyerLoginSuccessHandler;
		
		// 권한이 없을 때 뒷처리(403)
		// 403은 컨트롤러 어드바이스가 아니라 스프링 시큐리티가 처리
		@Autowired
		private BuyerAccessDeniedHandler BaccessDeniedHandler;
		

		// 아이디, 비밀번호, enabled를 읽어오는 sql
		// 아이디와 권한 읽어오는 sql
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(buyerdataSource)
				.usersByUsernameQuery("select username, bpassword, enabled from buyer where username=?") //db에서 아이디, 비번 비교
				.authoritiesByUsernameQuery("select username, role from buyer where username=?");
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
						http.authorizeRequests().antMatchers("/buyer/new","/buyer/login","/buyer/join", "/buyer/check/id", "/buyer/check/email",
								"/buyer/check/nickname","/buyer/check/tel","/buyer/findBybId", "/buyer/find/username",  "/buyer/reset/password","/buyer/resetPassword" 
								,"/campingAll", "camping/campingAll", "/campingRead", "/campingSearch", "/searchDetail"
								,"BBcamping/campingAll"
								).permitAll();
			http.csrf().disable();
			http.requestMatchers().antMatchers("/buyer/**").and().authorizeRequests().anyRequest().hasRole("BUYER")
			.and().exceptionHandling().accessDeniedHandler(BaccessDeniedHandler)
				.and().formLogin().loginPage("/buyer/login").loginProcessingUrl("/buyer/login").successHandler(buyerLoginSuccessHandler).failureHandler(buyerLoginFailureHandler)
				.and().logout().logoutUrl("/buyer/logout").logoutSuccessUrl("/com/home").invalidateHttpSession(true);
		
		
		
		
		}
	}
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(4)
	@Configuration
	//@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class comSecurityConfig extends WebSecurityConfigurerAdapter {
		
		
		// 파라미터 http는 스프링 시큐리티를 이용한 접근 통제 정보를 저장할 객체
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/com/**").permitAll();
			http.csrf().disable();
			// /admin/**로 들어오는 요청에 대해 ADMIN 권한과 폼 로그인 설정
			
		}
		
	}
}
	
		
