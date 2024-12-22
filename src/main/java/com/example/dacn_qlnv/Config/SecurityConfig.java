package com.example.dacn_qlnv.Config;

import com.example.dacn_qlnv.Services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final EmployeeService employeeService;

    // Cấu hình UserDetailsService sử dụng EmployeeService
    @Bean
    public UserDetailsService userDetailsService() {
        return employeeService;
    }

    // Cấu hình DaoAuthenticationProvider với UserDetailsService và PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Cấu hình bảo mật HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Tắt CSRF
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/login",
                                "/register",
                                "/error",
                                "/forgot-password",
                                "/reset-password",
                                "/images/**"
                        ).permitAll()  // Cho phép truy cập không cần đăng nhập

                        .requestMatchers(
                                "/employees/**",
                                "/departments/**",
                                "/statistics/**",
                                "/api/products",
                                "/categories/add",
                                "/order/list/**"
                        ).hasAnyAuthority("ADMIN", "STAFF")  // Chỉ ADMIN hoặc STAFF mới truy cập được

                        .anyRequest().authenticated()  // Các yêu cầu khác phải đăng nhập
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")                     // Trang đăng nhập
                        .loginProcessingUrl("/login")            // URL xử lý đăng nhập
                        .defaultSuccessUrl("/")                  // Đăng nhập thành công -> chuyển về trang chủ
                        .failureUrl("/login")                    // Đăng nhập thất bại -> quay lại login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                    // URL xử lý đăng xuất
                        .logoutSuccessUrl("/login")              // Đăng xuất thành công -> quay lại login
                        .deleteCookies("JSESSIONID")             // Xóa cookie phiên
                        .invalidateHttpSession(true)             // Hủy phiên hiện tại
                        .clearAuthentication(true)               // Xóa xác thực
                        .permitAll()
                );

        return http.build();
    }

    // Cấu hình mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
