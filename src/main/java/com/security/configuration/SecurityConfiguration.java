package com.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
//MEthod-Based auth anotasyonlarını etkin hale getirir
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.authorizeRequests().anyRequest().permitAll();
        // tum istekleri authorization olmaksızın yerine getirmek icin

        http
                .csrf().disable()    //Cross-site-request-forgery disable yaptık
                .authorizeRequests() //istekleri denetle
                .antMatchers("/","index","/css/*","/js/*").permitAll()

                //=======================ROLE-BASED AUTHENTICATION===========================
               /* .antMatchers("/kisiler").hasRole(KisiRole.USER.name())
                .antMatchers("/kisiler/**").hasRole(KisiRole.ADMIN.name())*/

                // ==================== METHOD-BASED AUTHENTICATION =====================
                // Metot-tabanlı kimlik denetimi için yapılması gereken adımlar.
                // 1- @EnableGlobalMethodSecurity(prePostEnabled = true) anotasyonunun Security
                //    class'ına konulması gerekir.
                // 2- Rollerin ROLE_ISIM şeklinde tanımlanması gerekir. Bunlar hard-coded olabileceği
                //    gibi KisiRole içerisinde varolan rollerin kullanılmasi ile de olabilir.
                //    Tabi bunun için Enum olan role isimleri ile sabit "ROLE_" kelimesini birleşirecek bir
                //    metot yazmak gerekir.
                // 3- UserDetailService metodu içerisinde kişilerin roles() tanımlamalarını authorities() olarak ,
                //    değiştirmeli ve KisiRole isimlerini ROLE_ISIM şeklinde almak için KisiRole içerisinde yazdığımız
                //    metotu kullanmalıyız.
                // 4- İzinleri ayarlamak için KisiContorller'a giderek metot başına hangi Rollere izin verileceğini
                //    belirlemek gerekmektedir.Bunun için  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                //    anotasyonu kullanılabilir.
                .anyRequest()        //tum istekleri
                .authenticated()     //sifreli olarak kullan
                .and()              //farklı islemleri birlestirme
                .httpBasic()        // basic http kimlik denetimini kullan
                .and()
                .formLogin()         //form login sayfası giris yapılsın
                // === kendi login sayfamızı kullanmak için======
                // 1- Webapp klasöründe yeni login.html sayfası oluşturlur.
                //  2- HomeController içerisinde bir RequestMapping metodu ile path tanımlanır
                //  3- SecurityConfig içerisinde loginPage(/login) metodu ile aktif hale getirilir.
                .loginPage("/login") //kendi login sayfamızı kullanalım
                .permitAll()
                .defaultSuccessUrl("/success")
                .and()
                .logout()
                //logout'dan sonra giris verilerini silmek icin kullandık
                .clearAuthentication(true)      //sifrelemeleri sil
                .invalidateHttpSession(true)    //Http oturumunu bitir
                .deleteCookies("JESSIONID")     //session id'yi sil
                //logout sonrasında tekrar login'e don
                .logoutSuccessUrl("/login");    //logout sonrası login sayfasına yönlendir


    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {

        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                //.roles("USER").build()
                //.roles(KisiRole.ADMIN.name()) ==>dogrudan hard code ile sabit rol vermek icin
                .authorities(KisiRole.USER.otoriteleriAl())//method based rol tahsisi
                .build();


        UserDetails admin1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("5678"))
                //.roles("ADMIN").build()
                //.roles(KisiRole.ADMIN.name())
                .authorities(KisiRole.ADMIN.otoriteleriAl())
                .build();

        return new InMemoryUserDetailsManager(user1,admin1);
    }
}
