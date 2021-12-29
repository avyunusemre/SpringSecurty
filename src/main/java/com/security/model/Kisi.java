package com.security.model;

import javax.persistence.*;

@Entity
@Table(name="Kisiler")
public class Kisi {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Integer id;
        private String ad;
        private String soyad;
        private int yas;

        public Kisi() {

        }

        public Kisi(Integer id, String ad, String soyad, int yas) {
            super();
            this.id = id;
            this.ad = ad;
            this.soyad = soyad;
            this.yas = yas;
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getAd() {
            return ad;
        }
        public void setAd(String ad) {
            this.ad = ad;
        }
        public String getSoyad() {
            return soyad;
        }
        public void setSoyad(String soyad) {
            this.soyad = soyad;
        }
        public int getYas() {
            return yas;
        }
        public void setYas(int yas) {
            this.yas = yas;
        }
}
