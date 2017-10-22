package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	
	@Autowired
	private KecamatanMapper kecamatanMapper;

	@Override
	public KecamatanModel selectKecamatanByID(String id) {
		log.info("select kecamatan by ID {}", id);
		return kecamatanMapper.selectKecamatanByID(id);
	}
	
	@Override
	public String selectKecamatanByKota(String id) {
		log.info("select kecamatan by ID {}", id);
		return kecamatanMapper.selectKecamatanByKota(id);
	}

	@Override
	public List<KecamatanModel> selectKecamatanByIdKota(String id_kota) {
		return kecamatanMapper.selectKecamatanByIdKota(id_kota);
	}

	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		return kecamatanMapper.selectAllKecamatan();
	}
}
