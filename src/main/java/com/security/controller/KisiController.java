package com.security.controller;

import com.security.model.Kisi;
import com.security.service.KisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kisiler")
public class KisiController {

    public KisiService kisiService;

    @Autowired
    public KisiController(KisiService kisiService) {
        this.kisiService = kisiService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<Kisi> kisileriGetir() {
        return kisiService.tumKisileriGetir();
    }

    @PostMapping(path="/ekle")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Kisi yeniKisiEkle(@RequestBody Kisi kisi) {
        return kisiService.kisiEkle(kisi);
    }


    @GetMapping(path="/ara/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Optional<Kisi> idIleKisiListele(@PathVariable Integer id) {
        return kisiService.idIleKisiGetir(id);
    }

    @DeleteMapping(path="/sil/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String idIleKisiSil(@PathVariable Integer id) {
        return kisiService.idIleKisiSil(id);
    }

    @PutMapping(path="/guncelle/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public Kisi idIleGuncelle(@PathVariable Integer id, @RequestBody Kisi guncelKisi) {
        return kisiService.idIleKisiGuncelle(id, guncelKisi);
    }

    @PatchMapping(path="/yenile/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Kisi idIleKismiGuncelle(Integer id, @RequestBody Kisi yeniKisi) {
        return kisiService.idIleKısmiGuncelleme(id, yeniKisi);
    }

    @DeleteMapping(path="/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String herkesiSil() {
        return kisiService.tumKisileriSil();
    }

}
