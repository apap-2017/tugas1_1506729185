package com.example.service;

import java.util.List;

import com.example.model.KotaModel;

public interface KotaService {

	KotaModel selectKotaByID(String string);
	
	List<KotaModel> selectKota();
	
	List<KotaModel> selectAllKota();
}
