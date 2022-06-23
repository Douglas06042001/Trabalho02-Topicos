package br.edu.univas.si7.topicos.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.univas.si7.topicos.domain.enums.UserProfile;
import lombok.Getter;

public class UserPrincipal implements UserDetails {

    @Getter
    private String id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String email, String password, Set<UserProfile> profiles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = profiles.stream()
            .map(p -> new SimpleGrantedAuthority(p.getDescription()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    /**
     * Verifica se o usuário possui o perfil do parâmetro. 
     * @param profile
     * @return
     */
    public boolean hasRole(UserProfile profile) {
        return authorities.contains(new SimpleGrantedAuthority(profile.getDescription()));
    }
}