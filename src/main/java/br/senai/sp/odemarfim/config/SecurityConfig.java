package br.senai.sp.odemarfim.config;

import br.senai.sp.odemarfim.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                //.requestMatchers(new AntPathRequestMatcher("/**"))
                .requestMatchers(new AntPathRequestMatcher("/assets/**"));


    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/acesso-negado").permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login/register/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/home/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/veterinario/inserir")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/veterinario/alterar")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/veterinario/listagem")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/estoque/**")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/produtos/visualizar")).hasAnyAuthority("ADMIN", "VETERINARIO", "TUTOR")
                        .requestMatchers(new AntPathRequestMatcher("/produtos/finalizarCompra")).hasAnyAuthority("ADMIN", "VETERINARIO", "TUTOR")
                        .requestMatchers(new AntPathRequestMatcher("/produtos/carrinho/**")).hasAnyAuthority("ADMIN", "VETERINARIO", "TUTOR")
                        .requestMatchers(new AntPathRequestMatcher("/tutor/inserir")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/tutor/listagem")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/user/alterar")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/user/inserir")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/user/listagem")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/procedimento/alterar")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/procedimento/inserir")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/procedimento/listagem")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/pet/alterar")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/pet/inserir")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/pet/listagem")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/agendamento/alterar")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/agendamento/inserir")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/agendamento/listagem")).hasAnyAuthority("ADMIN", "VETERINARIO")
                        .requestMatchers(new AntPathRequestMatcher("/admin/alterar")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/admin/inserir")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/admin/listagem")).hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userService)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )

                .exceptionHandling(exc -> exc
                        .accessDeniedPage("/acesso-negado")
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")                  // URL do logout
                        .logoutSuccessUrl("/login?logout")     // redireciona depois do logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return httpSecurity.build();
    }



}
