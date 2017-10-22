package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluargaByNomorKK(String nomor_kk) {
		log.info("select keluarga with nomor_kk {}", nomor_kk);
		return keluargaMapper.selectKeluargaByNomorKK(nomor_kk);
	}

	@Override
	public KeluargaModel selectKeluargaByID(String id) {
		log.info("select keluarga with nomor_kk {}", id);
		return keluargaMapper.selectKeluargaByID(id);		
	}
	
	@Override
	public String selectKeluargaByKelurahan(String id) {
		log.info("select keluarga with nomor_kk {}", id);
		return keluargaMapper.selectKeluargaByKelurahan(id);		
	}

	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		keluargaMapper.tambahKeluarga(keluarga);
		
	}

	@Override
	public int hitungNKK(String nomor_kk) {
		return keluargaMapper.hitungNKK(nomor_kk);
	}

	@Override
	public int hitungID() {
		return keluargaMapper.hitungID();
	}

	@Override
	public void ubahKeluarga(KeluargaModel keluarga) {
		keluargaMapper.ubahKeluarga(keluarga);
	}

	@Override
	public void setKeluargaActive(String id) {
		keluargaMapper.setKeluargaActive(id);
		
	}

	@Override
	public void setKeluargaInactive(String id) {
		keluargaMapper.setKeluargaInactive(id);
		
	}
}
