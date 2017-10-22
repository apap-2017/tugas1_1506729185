package com.example.service;

import java.util.List;

import com.example.model.PendudukModel;

public interface PendudukService {

	PendudukModel selectPendudukByNIK(String nik);
	
	List<PendudukModel> selectPendudukByKeluarga(String id_keluarga);
	
	void tambahPenduduk (PendudukModel penduduk);
	
	int hitungNIK (String nik);
	
	int hitungID();
	
	void ubahPenduduk (PendudukModel penduduk);
	
	void ubahStatusKematian (String nik);
	
	int hitungTidakMati (String id_keluarga);
	
	List<PendudukModel> selectPendudukByKelurahan(String id_kelurahan);
	
	List<PendudukModel> selectPendudukByKecamatan(String id_kecamatan);
}
