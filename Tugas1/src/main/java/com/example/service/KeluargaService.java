package com.example.service;

import com.example.model.KeluargaModel;

public interface KeluargaService {
	
	KeluargaModel selectKeluargaByNomorKK(String nomor_kk);

	KeluargaModel selectKeluargaByID(String id);

	String selectKeluargaByKelurahan(String id);
	
	void tambahKeluarga(KeluargaModel keluarga);
	
	int hitungNKK(String nomor_kk);
	
	int hitungID();
	
	void ubahKeluarga(KeluargaModel keluarga);
	
	void setKeluargaActive(String id);
	
	void setKeluargaInactive(String id);
	
	//KeluargaModel selectKeluargaWithPenduduk(String nomor_kk);
}
