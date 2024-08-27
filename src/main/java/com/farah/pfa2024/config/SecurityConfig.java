package com.farah.pfa2024.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; //used to fetch user details (like username, password, and roles)
    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //This method returns a SecurityFilterChain bean that configures security for the HTTP requests
        httpSecurity.csrf(AbstractHttpConfigurer::disable) //Disables CSRF protection and enables default CORS configuration
                .cors(Customizer.withDefaults())
                /*.requestMatchers("/admin/**").hasRole("ADMIN")  // checks for ROLE_ADMIN
                .requestMatchers("/user/**").hasAnyRole("USER", "MODERATOR")  // checks for ROLE_USER or ROLE_MODERATOR

                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")  // checks for ROLE_ADMIN
                .requestMatchers("/privileges/**").hasAnyAuthority("READ_PRIVILEGES", "WRITE_PRIVILEGES")  // checks for either READ_PRIVILEGES or WRITE_PRIVILEGES

*/
                .authorizeHttpRequests(request->request.requestMatchers("/auth/**","/public/**","reservations/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/prestataire/**").hasAnyAuthority("prestataire", "admin")
                        .requestMatchers("/client/**").hasAnyAuthority("client","admin")
                        .requestMatchers("/service/**").hasAnyAuthority("prestataire","admin","client")
                        /*.requestMatchers("reservations/**").hasAnyAuthority("prestataire","admin","client")*/
                        .anyRequest().authenticated())
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //the server doesn't store the session data
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );
                return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider DaoAuthProvider = new DaoAuthenticationProvider();
        DaoAuthProvider.setUserDetailsService(userDetailsService);
        DaoAuthProvider.setPasswordEncoder(passwordEncoder());
        return DaoAuthProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //Password Encoder: The DaoAuthenticationProvider uses a PasswordEncoder to compare the raw password provided by the user with the encoded password stored in the database.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
