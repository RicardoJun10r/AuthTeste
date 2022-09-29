package com.authtest.autenticacaoexemplo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.authtest.autenticacaoexemplo.services.DetalheUsuarioServiceImpl;

@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {
    
    private final DetalheUsuarioServiceImpl usuarioServiceImpl;

    public JWTConfiguracao(DetalheUsuarioServiceImpl usuarioServiceImpl)
    {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServiceImpl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/login")
        .permitAll().anyRequest().authenticated().and()
        .addFilter(new JWTAutenticarFilter(authenticationManager()))
        .addFilter(new JWTValidarFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource configurationSource()
    {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
