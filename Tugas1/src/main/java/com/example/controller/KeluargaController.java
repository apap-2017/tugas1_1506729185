package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class KeluargaController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@RequestMapping("/keluarga")
	public String viewKeluarga (Model model,
			@RequestParam(value = "nomor_kk", required = false) String nomor_kk){
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNomorKK(nomor_kk);
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKotaByID(kecamatan.getId_kota());
		
		List<PendudukModel> anggota = pendudukDAO.selectPendudukByKeluarga(keluarga.getId());
		
		if(keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("anggotaKeluarga", anggota);
			return "view-keluarga";
		} else {
			model.addAttribute("nomor_kk", nomor_kk);
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.GET)
    public String tambahKeluarga (Model model)
    {	
		List<KelurahanModel> listKelurahan = kelurahanDAO.selectAllKelurahan();
		List<KecamatanModel> listKecamatan = kecamatanDAO.selectAllKecamatan();
		List<KotaModel> listKota = kotaDAO.selectAllKota();
		
		model.addAttribute("listKelurahan", listKelurahan);
		model.addAttribute("listKecamatan", listKecamatan);
		model.addAttribute("listKota", listKota);
		
        return "form-tambahKeluarga";
    }


    @RequestMapping(value = "keluarga/tambah", method = RequestMethod.POST)
    public String tambahKeluargaSubmit (Model model,
            @RequestParam(value = "alamat") String alamat,
            @RequestParam(value = "rt") String rt,
            @RequestParam(value = "rw") String rw,
            @RequestParam(value = "id_kelurahan") String id_kelurahan
            )
    {
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(id_kelurahan);
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		
		//ngambil kode_kecamatan
		String kode_kecamatan = kecamatan.getKode_kecamatan();
		kode_kecamatan = kode_kecamatan.substring(0, 6);
		
		//format tanggal
		Date tanggalSekarang = new Date ();
		SimpleDateFormat tanggalKumau = new SimpleDateFormat ("ddMMyy");
		String tanggal = tanggalKumau.format(tanggalSekarang);
		
		String nomor_kk = kode_kecamatan;
		nomor_kk += tanggal;
		
		Integer nkkMirip = keluargaDAO.hitungNKK(nomor_kk + "%");
		nkkMirip += 1;
		String rekapNKK = nkkMirip + "";
		
		int i = 4;
		while (i > rekapNKK.length()) {
			rekapNKK = "0" + rekapNKK;
		}
		nomor_kk += rekapNKK;
		
		Integer id = keluargaDAO.hitungID() + 1;
		String idInput = id + "";
		
        KeluargaModel keluarga = new KeluargaModel (idInput, nomor_kk, alamat, rt, rw, id_kelurahan, "0");
        keluargaDAO.tambahKeluarga(keluarga);
        
        model.addAttribute("keluarga", keluarga);

        return "success-tambahKeluarga";
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nomor_kk}", method = RequestMethod.GET)
    public String ubahKeluarga (Model model, @PathVariable(value = "nomor_kk") String nomor_kk)
    {	
    	KeluargaModel keluarga = keluargaDAO.selectKeluargaByNomorKK(nomor_kk);
    	KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKotaByID(kecamatan.getId_kota());
		/*KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKotaByID(kecamatan.getId_kota());
		List<PendudukModel> anggota = pendudukDAO.selectPendudukByKeluarga(keluarga.getId());*/
		List<KelurahanModel> listKelurahan = kelurahanDAO.selectAllKelurahan();
		List<KecamatanModel> listKecamatan = kecamatanDAO.selectAllKecamatan();
		List<KotaModel> listKota = kotaDAO.selectAllKota();
		
		if(keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("listKelurahan", listKelurahan);
			model.addAttribute("listKecamatan", listKecamatan);
			model.addAttribute("listKota", listKota);
			model.addAttribute("kota", kota);
			/*model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("anggota",anggota);*/
			return "form-ubahKeluarga";
		} else {
			model.addAttribute("nomor_kk", nomor_kk);
			return "not-found";
		}
    }
    
    @RequestMapping(value = "/keluarga/ubah", method = RequestMethod.POST)
    public String ubahKeluargaSubmit (Model model, KeluargaModel keluarga, 
    		@RequestParam(value= "id_kelurahan_lama", required = false) String id_kelurahan_lama,
    		@RequestParam(value= "id", required = false) String id)
    {
    	if(!keluarga.getId_kelurahan().equalsIgnoreCase(id_kelurahan_lama)) {
    		List<PendudukModel> anggota = pendudukDAO.selectPendudukByKeluarga(id);
    		
    		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
    		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
    		
    		//ngambil kode_kecamatan
    		String kode_kecamatan = kecamatan.getKode_kecamatan();
    		kode_kecamatan = kode_kecamatan.substring(0, 6);
    		System.out.println(kode_kecamatan);
    		
    		//format tanggal
    		Date tanggalSekarang = new Date ();
    		SimpleDateFormat tanggalKumau = new SimpleDateFormat ("ddMMyy");
    		String tanggal = tanggalKumau.format(tanggalSekarang);
    		
    		String nomor_kk_baru = kode_kecamatan;
    		nomor_kk_baru += tanggal;
    		System.out.println(nomor_kk_baru);
    		
    		Integer nkkMirip = keluargaDAO.hitungNKK(nomor_kk_baru + "%");
    		nkkMirip += 1;
    		String rekapNKK = nkkMirip + "";
    		System.out.println(rekapNKK);
    		
    		int i = 4;
    		while (i > rekapNKK.length()) {
    			rekapNKK = "0" + rekapNKK;
    		}
    		nomor_kk_baru += rekapNKK;
    		System.out.println(nomor_kk_baru);
    		
    		model.addAttribute("nomor_kk", keluarga.getNomor_kk());
    		keluarga.setNomor_kk(nomor_kk_baru);
    		System.out.println(keluarga);
    		
    		keluargaDAO.ubahKeluarga(keluarga);
           	//model.addAttribute("nomor_kk_baru", keluarga.getNomor_kk());
        	
        	for(int j = 0; j < anggota.size(); j++) {
        		String[] tanggal_split = anggota.get(j).getTanggal_lahir().split("-");
        		
        		//ngecek jenis kelamin
        		if (anggota.get(j).getJenis_kelamin().equals("1")) {
        			tanggal_split[2] = Integer.parseInt(tanggal_split[2]) + 40 + "";
        		}
        		        		
        		String pre_nik = kode_kecamatan;
        		pre_nik += tanggal_split[2] + tanggal_split[1] + tanggal_split[0].substring(2);
        		
        		Integer nikMirip = pendudukDAO.hitungNIK(pre_nik + "%");
        		nikMirip += 1;
        		String rekapNIK = nikMirip + "";
        		
        		int a = 4;
        		while (a > rekapNIK.length()) {
        			rekapNIK = "0" + rekapNIK;
        		}
        		pre_nik += rekapNIK;
        		
        		anggota.get(j).setNik(pre_nik);
        		
        		pendudukDAO.ubahPenduduk(anggota.get(j));
        	}
        	
        	return "success-ubahKeluarga";
    	} else {    		
	    	keluargaDAO.ubahKeluarga(keluarga);
	    	model.addAttribute("nomor_kk", keluarga.getNomor_kk());	        
	    	return "success-ubahKeluarga";
    	}
    }
}
