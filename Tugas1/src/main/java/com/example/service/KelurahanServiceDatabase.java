package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	
	@Autowired
	private KelurahanMapper kelurahanMapper;

	@Override
	public KelurahanModel selectKelurahanByID(String id) {
		log.info("select keluarga with nomor_kk {}", id);
		return kelurahanMapper.selectKelurahanByID(id);
	}

	@Override
	public String selectKelurahanByKecamatan(String id) {
		log.info("select keluarga with nomor_kk {}", id);
		return kelurahanMapper.selectKelurahanByKecamatan(id);
	}

	@Override
	public List<KelurahanModel> selectKelurahanByIdKecamatan(String id_kecamatan) {
		return kelurahanMapper.selectKelurahanByIdKecamatan(id_kecamatan);
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		return kelurahanMapper.selectAllKelurahan();
	}
}
