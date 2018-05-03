package com.youtalkwesign.service;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/*").permitAll()
                .and()
            .formLogin()
                .loginPage("/")   
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/?error")
                .and()
            .logout()                                    
                .permitAll();
        	
        http.csrf().disable();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication().dataSource(dataSource)
				// default: "select username, password, enabled from users where username=?"
				.usersByUsernameQuery(
		  			"select username, password, enabled from user where username=?")
				.authoritiesByUsernameQuery(
		  			"select username, role from authority where username=?");
	}
}