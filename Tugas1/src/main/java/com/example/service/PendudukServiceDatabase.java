package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel selectPendudukByNIK(String nik) {
		log.info("select penduduk with nik {}", nik);
		return pendudukMapper.selectPendudukByNIK(nik);
	}
	
	@Override
	public List<PendudukModel> selectPendudukByKeluarga(String id_keluarga) {
		log.info("select penduduk with id_keluarga {}", id_keluarga);
		return pendudukMapper.selectPendudukByKeluarga(id_keluarga);
	}

	@Override
	public void tambahPenduduk(PendudukModel penduduk) {
		log.info("select penduduk with id_keluarga {}", penduduk);
		pendudukMapper.tambahPenduduk(penduduk);
	}

	@Override
	public int hitungNIK(String nik) {
		return pendudukMapper.hitungNIK(nik);
	}

	@Override
	public int hitungID() {
		return pendudukMapper.hitungID();
	}

	@Override
	public void ubahPenduduk(PendudukModel penduduk) {
		pendudukMapper.ubahPenduduk(penduduk);		
	}

	@Override
	public void ubahStatusKematian(String nik) {
		pendudukMapper.ubahStatusKematian(nik);
		
	}

	@Override
	public int hitungTidakMati(String id_keluarga) {
		return pendudukMapper.hitungTidakMati(id_keluarga);
	}

	@Override
	public List<PendudukModel> selectPendudukByKelurahan(String id_kelurahan) { 
		return pendudukMapper.selectPendudukByKelurahan(id_kelurahan);
	}

	@Override
	public List<PendudukModel> selectPendudukByKecamatan(String id_kecamatan) {
		return pendudukMapper.selectPendudukByKecamatan(id_kecamatan);
	}
}
