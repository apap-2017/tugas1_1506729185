package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;

public interface KecamatanService {

	KecamatanModel selectKecamatanByID(String id);

	String selectKecamatanByKota(String id);
	
	List<KecamatanModel> selectKecamatanByIdKota(String id_kota);
	
	List<KecamatanModel> selectAllKecamatan();

}
