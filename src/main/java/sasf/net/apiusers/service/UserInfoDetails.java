package sasf.net.apiusers.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sasf.net.apiusers.model.Usuario;

public class UserInfoDetails implements UserDetails {
    private String name;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserInfoDetails(Usuario userInfo) {
        this.name = userInfo.getName();
        this.password = userInfo.getPassword();
        this.authorities = Arrays.stream(userInfo.getRol().split(";"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
