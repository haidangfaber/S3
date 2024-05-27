package init_spring1.init_spring.config;

import init_spring1.init_spring.config.security.jwt.JwtAuthenticationEntryPoint;
import init_spring1.init_spring.config.security.jwt.JwtRequestFilter;
import init_spring1.init_spring.config.security.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //    JwtUserDetailsService để lấy thông tin người dùng từ cơ sở dữ liệu,

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


//    JwtRequestFilter để kiểm tra xác thực JWT.
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/api/auth/authenticate").permitAll()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll();
//                   .antMatchers(" /api/auth/register").permitAll();


        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    addFilterBefore là một phương thức của lớp HttpSecurity trong Spring Security,
//    được sử dụng để thêm một bộ lọc trước một bộ lọc khác trong chuỗi bộ lọc.
//   addFilterBefore để chèn một bộ lọc JWT mới vào trước bộ lọc UsernamePasswordAuthenticationFilter
//nó sẽ đi qua bộ lọc JWT trước khi được chuyển đến bộ lọc xác thực tên người dùng và mật khẩu.
//    addFilterBefore sẽ xử ly jwt có hợp lệ không

//    Chúng ta cũng đã sử dụng JwtAuthenticationEntryPoint để xử lý các ngoại lệ khi xác thực không thành công



//    ####################################
    //1. Tạo một bộ lọc JWT:
    // - Chúng ta có thể triển khai một bộ lọc JWT bằng cách kế thừa lớp OncePerRequestFilter và ghi đè phương thức doFilterInternal.
    // - Trong phương thức này, chúng ta có thể xử lý yêu cầu HTTP và kiểm tra xem token JWT có hợp lệ hay không bằng cách sử dụng lớp JwtTokenUti

    //2.Cấu hình bộ lọc JWT trong Spring Security
    // - Chúng ta có thể cấu hình bộ lọc JWT trong Spring Security bằng cách sử dụng phương thức addFilterBefore của lớp HttpSecurity.
    // Bộ lọc JWT sẽ được chạy trước các bộ lọc khác trong chuỗi bộ lọc của Spring Security.

    //3. Tạo một bean JwtTokenUtil:
    // - Chúng ta có thể triển khai một lớp JwtTokenUtil để xác thực thông tin người dùng từ token JWT.
    // Lớp này sẽ được sử dụng để xác thực các yêu cầu được gửi đến ứng dụng.

    // 4 .Bắt đầu sử dụng token JWT:
    // - Khi người dùng đăng nhập thành công và nhận được một token JWT, họ có thể sử dụng token này để truy cập các tài nguyên của ứng dụng.
    // - Các yêu cầu sẽ được gửi đến ứng dụng với token được chèn vào tiêu đề Authorization của yêu cầu HTTP.
}
