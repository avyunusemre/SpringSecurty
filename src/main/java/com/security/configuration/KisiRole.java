package com.security.configuration;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.security.configuration.KisiPermission.*;

public enum KisiRole {
    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE));

    private Set<KisiPermission> kisiPermission;

    public Set<KisiPermission> getKisiPermission() {
        return kisiPermission;
    }

    public void setKisiPermission(Set<KisiPermission> kisiPermission) {
        this.kisiPermission = kisiPermission;
    }

    KisiRole(Set<KisiPermission> kisiPermission) {
        this.kisiPermission = kisiPermission;
    }

    //metod-based authenteication islemi icin rol birlestirme metodu
    public Set<SimpleGrantedAuthority> otoriteleriAl() {
        Set<SimpleGrantedAuthority> permission = getKisiPermission()
                .stream()
                .map((x -> new SimpleGrantedAuthority(x.getPermission())))
                .collect(Collectors.toSet());

        //permission Set'i icerisindeki izinleri ROLE_ sabit kelimesi ile birleştirir.
        permission.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permission;
    }
}
