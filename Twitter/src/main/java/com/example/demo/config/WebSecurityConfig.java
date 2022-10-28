package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.MyUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserService userService;

    @Autowired
    public WebSecurityConfig (MyUserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
            .authorizeRequests()
            .antMatchers("/js/**", "/css/**", "/webjars/**", "/loginForm").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .loginProcessingUrl("/login")
            .usernameParameter("user_name")
            .passwordParameter("password")
            .defaultSuccessUrl("/home", true)
            .failureUrl("/loginForm?error=true")
			.and()
			.logout()
			.permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            return bcpe;
    }
}

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//	private MyUserService userService;
//
//	@Autowired
//	public WebSecurityConfig (MyUserService userService) {
//		this.userService = userService;
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//			.authorizeHttpRequests()
//// antMatchers～→アクセスできるページを設定できる
////			.antMatchers("/js/**", "/css/**", "/loginForm").permitAll()
//// 全てのURLリクエストは認証されているユーザーしかアクセスできないという記述
////			.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.loginPage("/loginForm")
//			.loginProcessingUrl("/login")
//			.usernameParameter("username")
//			.passwordParameter("password")
//			.defaultSuccessUrl("/home", true)
//			.failureUrl("/loginFrom?error=true")
//			.and()
//			.logout()
//			.permitAll();
//
//			return http.build();
//	}
//
//	public void configure (AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//	}
//
//	public BCryptPasswordEncoder passwordEncoder() {
//		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
//		return bcpe;
//	}
//}
