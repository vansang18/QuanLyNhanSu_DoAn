package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import com.example.dacn_qlnv.Repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginRepository loginRepository;

    //    public boolean authenticate(String email, String password) {
//        Employee employee = employeeRepository.findByEmail(email);
//        if (employee != null) {
//            // So sánh mật khẩu được nhập vào với mật khẩu đã mã hóa trong cơ sở dữ liệu
//            return passwordEncoder.matches(password, employee.getPassword());
//        }
//        return false; // Nếu không tìm thấy nhân viên
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var login = loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(login.getUsername())
                .password(login.getPassword())
                .authorities(login.getAuthorities())
                .accountExpired(!login.isAccountNonExpired())
                .accountLocked(!login.isAccountNonLocked())
                .credentialsExpired(!login.isCredentialsNonExpired())
                .disabled(!login.isEnabled())
                .build();
    }
    public static Optional<Employee> findByUsername(String username) throws UsernameNotFoundException {
        return LoginService.findByUsername(username);
    }

}
