package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {
	
	@Autowired
	private KotaMapper kotaMapper;

	@Override
	public KotaModel selectKotaByID(String id) {
		log.info("select keluarga with nomor_kk {}", id);
		return kotaMapper.selectKotaByID(id);
	}

	@Override
	public List<KotaModel> selectKota() {
		return kotaMapper.selectKota();
	}

	@Override
	public List<KotaModel> selectAllKota() {
		return kotaMapper.selectAllKota();
	}
}
