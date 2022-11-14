package com.reesedevelopment.greatneckzmanim;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    GNZUserDetailsService userDetailsService;

//    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // The pages does not require login
        http
                .authorizeRequests()
                    .antMatchers("/", "/zmanim/**", "/orgs/**", "/admin/login", "/admin/logout", "/webjars/**", "/**/*.css", "/**/*.js", "/static/**", "/db/**").permitAll()
                    .antMatchers("/admin", "/admin/dashboard", "/admin/organization", "/admin/account", "/admin/update-organization", "/admin/update-account", "/admin/locations", "/admin/create-location", "/admin/update-location", "/admin/delete-location", "/admin/**/minyanim/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .and()
                .formLogin()//
                    .loginProcessingUrl("/j_spring_security_check")
                    .loginPage("/admin/login")
                    .defaultSuccessUrl("/admin")
                    .failureUrl("/admin/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login?logout=true")
                    .and()
                .rememberMe()
                    .key("uniqueAndSecret").tokenValiditySeconds(604800);
                

        // For ADMIN only.
//        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");


    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers()
//                .xssProtection()
//                .and()
//                .contentSecurityPolicy("script-src 'self'");
//        return http.build();
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        System.out.println("WebSecurityConfiguration.userDetailsService() called");
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}