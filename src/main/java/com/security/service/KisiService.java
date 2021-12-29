package com.security.service;

import com.security.model.Kisi;
import com.security.repository.KisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KisiService {

    public static KisiRepository kisiRepository;

    //Dependency Injection
    @Autowired
    public KisiService(KisiRepository kisiRepository) {
        this.kisiRepository = kisiRepository;
    }

    public List<Kisi> tumKisileriGetir() {
        return kisiRepository.findAll();
    }

    //Veri tabanına kisi ekleyen metodumuz
    public Kisi kisiEkle(Kisi kisi) {
        return kisiRepository.save(kisi);
    }

    //id ile kisi getiren servis metodu

    public Optional<Kisi> idIleKisiGetir(Integer id) {
        return kisiRepository.findById(id);
    }

    public String idIleKisiSil(Integer id) {
        if(kisiRepository.findById(id).isEmpty()) {
            throw new IllegalStateException(id + "'li kisi bulunamadı!");
        }
        kisiRepository.deleteById(id);
        return id + "'li kisi silindi.";
    }

    public String tumKisileriSil(){
        if(kisiRepository.count() == 0){
            return "Veri tabanında kayıtlı kisii yok!";
        } else {
            kisiRepository.deleteAll();
            return "Tüm kisiler silindi!";
        }
    }


    public Kisi idIleKisiGuncelle(Integer id, Kisi guncelKisi) {

        Kisi eskiKisi = kisiRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + "'li kisi bulunamadı!"));
        eskiKisi.setAd(guncelKisi.getAd());
        eskiKisi.setSoyad(guncelKisi.getSoyad());
        eskiKisi.setYas(guncelKisi.getYas());
        kisiRepository.save(eskiKisi);
        return guncelKisi;
    }

    //Patch
    public Kisi idIleKısmiGuncelleme(Integer id, Kisi yeniKisi) {
        Kisi eskiKisi = kisiRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + "'li kisi bulunamadı!"));

        if(yeniKisi.getAd() != null) {
            eskiKisi.setAd(yeniKisi.getAd());
        }
        if(yeniKisi.getSoyad() != null) {
            eskiKisi.setSoyad(yeniKisi.getSoyad());
        }
        if(yeniKisi.getYas() != 0) {
            eskiKisi.setYas(yeniKisi.getYas());
        }

        return kisiRepository.save(eskiKisi);
    }

}