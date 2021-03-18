package br.com.exemplo.exemplo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;
		
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder
			.authenticationProvider(authenticationProvider());
	}

	// Configurações de recursos dinâmicos
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.authorizeRequests()
        		.antMatchers("/novoUsuario").permitAll()
        		.antMatchers("/salvarUsuario").permitAll()
        		.antMatchers("/esqueciSenha").permitAll()
        		.antMatchers("/h2/**").permitAll()
        		.anyRequest().authenticated()
        		.and()
        		.formLogin()
        			.loginPage("/login").permitAll()
        			.defaultSuccessUrl("/")
        		.and()
        		.logout()
        			.logoutUrl("/logout")
        			.logoutSuccessUrl("/login");
        httpSecurity
        	.csrf().disable();
        httpSecurity
        	.headers()
        		.frameOptions().disable();
    }
	
	// Configurações de recursos estáticos(js, css, imagens, etc. )
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
		
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider autenticador = new DaoAuthenticationProvider();
        autenticador.setUserDetailsService(autenticacaoService);
        autenticador.setPasswordEncoder(passwordEncoder());
        return autenticador;
    }

	/*
	// Configurações de usuário autenticado em memória
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
	    builder
	        .inMemoryAuthentication()
	        	.withUser("Alexandre")
	        		.password(passwordEncoder().encode("123456"))
	        		.roles("GERENTE")	//ROLE_GERENTE
	        .and()
	        .withUser("usuario2")
	        	.password(passwordEncoder().encode("123xyz"))
	        	.roles("CLIENTE");		//ROLE_CLIENTE
	}*/
}
