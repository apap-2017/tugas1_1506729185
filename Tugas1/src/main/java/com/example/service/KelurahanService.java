package com.example.service;

import java.util.List;

import com.example.model.KelurahanModel;

public interface KelurahanService {

	KelurahanModel selectKelurahanByID(String string);
	
	String selectKelurahanByKecamatan(String id);
	
	List<KelurahanModel> selectKelurahanByIdKecamatan (String id_kecamatan);
	
	List<KelurahanModel> selectAllKelurahan();
}
