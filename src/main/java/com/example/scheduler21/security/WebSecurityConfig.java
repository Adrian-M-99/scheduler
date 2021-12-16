package com.example.scheduler21.security;

import com.example.scheduler21.security.oauth2.CustomOAuth2UserService;
import com.example.scheduler21.security.oauth2.OAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static java.lang.invoke.VarHandle.AccessMode.GET;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuthSuccessHandler oAuthSuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/departments/**").hasAnyAuthority( "STAFF", "ROLE_ADMIN")
                .antMatchers("/doctors/**").hasAnyAuthority("STAFF", "ROLE_ADMIN")
                .antMatchers("/patients/**").hasAnyAuthority("STAFF", "ROLE_ADMIN")
                .antMatchers("/appointments/**").hasAnyAuthority("STAFF", "ROLE_ADMIN")
                .antMatchers("/profile").hasAnyAuthority("PATIENT", "STAFF")
                .antMatchers("/files/**").hasAnyAuthority("PATIENT", "STAFF", "ROLE_ADMIN")
//                .antMatchers("/").hasAnyAuthority("PATIENT","STAFF", "ROLE_ADMIN")
                .antMatchers("/register/**").permitAll()
                .antMatchers("/forgot_password").permitAll()
                .antMatchers("/reset_password").permitAll()
                .antMatchers("/", "/login", "/oauth/**", "/contact-us/**", "/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureHandler(customLoginFailureHandler)
                    .successHandler(customLoginSuccessHandler)
                    .permitAll()
                .and()
                .rememberMe().tokenValiditySeconds(24 * 60 * 60)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/").permitAll()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .successHandler(oAuthSuccessHandler)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);
    }
}
