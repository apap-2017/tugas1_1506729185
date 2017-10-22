package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk") 
	public String tampilPenduduk(Model model, 
			@RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKotaByID(kecamatan.getId_kota());
		
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			return "view-penduduk";
		} else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.GET)
    public String tambahPenduduk ()
    {
        return "form-tambahPenduduk";
    }


    @RequestMapping(value = "penduduk/tambah", method = RequestMethod.POST)
    public String tambahPendudukSubmit (Model model,
            @RequestParam(value = "nama") String nama,
            @RequestParam(value = "tempat_lahir") String tempat_lahir,
            @RequestParam(value = "tanggal_lahir") String tanggal_lahir,
            @RequestParam(value = "jenis_kelamin") String jenis_kelamin,
            @RequestParam(value = "is_wni") String is_wni,
            @RequestParam(value = "id_keluarga") String id_keluarga,
            @RequestParam(value = "agama") String agama,
            @RequestParam(value = "pekerjaan") String pekerjaan,
            @RequestParam(value = "status_perkawinan") String status_perkawinan,
            @RequestParam(value = "status_dalam_keluarga") String status_dalam_keluarga,
            @RequestParam(value = "golongan_darah") String golongan_darah,
            @RequestParam(value = "is_wafat") String is_wafat)
    {
    	KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(id_keluarga);
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
		
		//ngambil kode_kecamatan
		String kode_kecamatan = kecamatan.getKode_kecamatan();
		kode_kecamatan = kode_kecamatan.substring(0, 6);
		
		//ngambil tanggal_lahir
		String tgl_lahir = tanggal_lahir;
		String[] tanggal_split = tgl_lahir.split("-");
		
		//ngecek jenis kelamin
		if (jenis_kelamin.equals("1")) {
			tanggal_split[2] = Integer.parseInt(tanggal_split[2]) + 40 + "";
		}
		
		//masukin kode kecamatan ke nik
		String nik = kode_kecamatan;
		nik += tanggal_split[2] + tanggal_split[1] + tanggal_split[0].substring(2);
		
		//ngitung nik yang mirip
		Integer nikMirip = pendudukDAO.hitungNIK(nik + "%");
		nikMirip += 1;
		String rekapNIK = nikMirip + "";
		
		int i = 4;
		while (i > rekapNIK.length()) {
			rekapNIK = "0" + rekapNIK;
		}
		nik += rekapNIK;
		
		Integer id = pendudukDAO.hitungID() + 1;
		String idInput = id + "";
		
		PendudukModel penduduk = new PendudukModel (idInput, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat);
        pendudukDAO.tambahPenduduk(penduduk);
        
        model.addAttribute("penduduk", penduduk);

        return "success-tambahPenduduk";
    }
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.GET)
    public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik)
    {	
    	PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);
		
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "form-ubahPenduduk";
		} else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
    }
    
    @RequestMapping(value = "/penduduk/ubah", method = RequestMethod.POST)
    public String ubahPendudukSubmit (Model model, @RequestParam(value = "nik", required=false) String nik, @Valid @ModelAttribute PendudukModel penduduk)
    {
    	PendudukModel pendudukLama = pendudukDAO.selectPendudukByNIK(nik);
    	
    	if(penduduk.getTanggal_lahir() != pendudukLama.getTanggal_lahir() || !penduduk.getId_keluarga().equals(pendudukLama.getId_keluarga()) || penduduk.getJenis_kelamin() != pendudukLama.getJenis_kelamin()) {
    		String[] tanggal_split = penduduk.getTanggal_lahir().split("-");
    		
    		//ngecek jenis kelamin
    		if (penduduk.getJenis_kelamin().equals("1")) {
    			tanggal_split[2] = Integer.parseInt(tanggal_split[2]) + 40 + "";
    		}
    		
    		KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
    		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByID(keluarga.getId_kelurahan());
    		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanByID(kelurahan.getId_kecamatan());
    		
    		String kode_kecamatan = kecamatan.getKode_kecamatan();
    		kode_kecamatan = kode_kecamatan.substring(0, 6);
    		
    		String pre_nik = kode_kecamatan;
    		pre_nik += tanggal_split[2] + tanggal_split[1] + tanggal_split[0].substring(2);
    		
    		Integer nikMirip = pendudukDAO.hitungNIK(pre_nik + "%");
    		nikMirip += 1;
    		String rekapNIK = nikMirip + "";
    		
    		int i = 4;
    		while (i > rekapNIK.length()) {
    			rekapNIK = "0" + rekapNIK;
    		}
    		pre_nik += rekapNIK;
    		
    		penduduk.setNik(pre_nik);
    		
    		pendudukDAO.ubahPenduduk(penduduk);
           	model.addAttribute("nik", nik);
        	model.addAttribute("pendudukLama", pendudukLama);
        	return "success-ubahPenduduk";
    	} else {
    		pendudukDAO.ubahPenduduk(penduduk);
           	model.addAttribute("nik", nik);
        	model.addAttribute("penduduk", penduduk);
        	return "success-ubahPenduduk";
    	}
    }
    
    @RequestMapping(value = "penduduk/mati", method=RequestMethod.POST)
    public String ubahStatusKematian(@RequestParam(value="nik", required = false) String nik, Model model) {
    	
    	PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);
    	model.addAttribute("penduduk", penduduk);
    	
    	return "form-pendudukMati";
    }
    
    @RequestMapping(value = "/penduduk", method=RequestMethod.POST)
    public String ubahStatusKematianRedirect(@RequestParam(value="nik", required = false) String nik, Model model) {
    	
    	PendudukModel penduduk = pendudukDAO.selectPendudukByNIK(nik);
    	pendudukDAO.ubahStatusKematian(nik);
    	
    	int hitung = pendudukDAO.hitungTidakMati(penduduk.getId_keluarga());
    	KeluargaModel keluarga = keluargaDAO.selectKeluargaByID(penduduk.getId_keluarga());
    	//keluarga.setIs_tidak_berlaku("1");
    	
    	//List<PendudukModel> anggota = pendudukDAO.selectPendudukByKeluarga(keluarga.getId());
    	
    	if(hitung == 0) {
    		keluarga.setIs_tidak_berlaku("1");
    	} /*else {
    		for(int i = 0; i < anggota.size(); i++) {
    			if(anggota.get(i).getIs_wafat().equals("0")) {
    				keluarga.setIs_tidak_berlaku("0");
    			}
    		}
    	}*/
    	
    	keluargaDAO.setKeluargaInactive(keluarga.getId());;
    	
    	model.addAttribute("nik", nik);
    	model.addAttribute("mati", nik);
    	
    	return "success-ubahStatusKematian";
    }
    
    @RequestMapping("/penduduk/cari")
    public String cariPenduduk(Model model,
    		@RequestParam(value = "kt", required = false) String kota,
    		@RequestParam(value = "kc", required = false) String kecamatan,
    		@RequestParam(value = "kl", required = false) String kelurahan) {
    	if(kota == null) {
    		model.addAttribute("kota", kotaDAO.selectKota());
    		return "cari-pendudukDiKota";
    	} else {
    		KotaModel kotaModel = kotaDAO.selectKotaByID(kota);
    		
    		if(kotaModel == null) {
    			model.addAttribute("error", "Kota tidak ditemukan");
    			return "not-found";
    		}
    		
    		if(kecamatan == null) {
    			model.addAttribute("kota", kotaDAO.selectKotaByID(kota));
    			model.addAttribute("kecamatan", kecamatanDAO.selectKecamatanByIdKota(kota));
    			return "cari-pendudukDiKecamatan";
    		} else {
    			KecamatanModel kecamatanModel = kecamatanDAO.selectKecamatanByID(kecamatan);
    			
    			if(kecamatanModel == null) {
    				model.addAttribute("error", "Kecamatan tidak ditemukan");
    				return "not-found";
    			}
    			
    			if(!kecamatanModel.getId_kota().equals(kota)) {
    				model.addAttribute("error", "Kecamatan tidak ditemukan");
    				return "not-found";
    			}
    			
    			if(kelurahan == null) {
    				model.addAttribute("kota", kotaDAO.selectKotaByID(kota));
    				model.addAttribute("kecamatan", kecamatanDAO.selectKecamatanByID(kecamatan));
    				model.addAttribute("kelurahan", kelurahanDAO.selectKelurahanByIdKecamatan(kecamatan));
    				return "cari-pendudukDiKelurahan";
    			}
    		}
    	}
    	
    	KelurahanModel kelurahanModel = kelurahanDAO.selectKelurahanByID(kelurahan);
    	
    	if(kelurahanModel == null) {
    		model.addAttribute("error", "Kelurahan tidak ditemukan");
    		return "not-found";
    	}
    	
    	KecamatanModel kecamatanModel = kecamatanDAO.selectKecamatanByID(kecamatan);
    	
    	if(!kecamatanModel.getId_kota().equals(kota)) {
    		model.addAttribute("error", "Kecamatan tidak ditemukan");
    		return "not-found";
    	}
    	
    	if(!kelurahanModel.getId_kecamatan().equals(kecamatan)) {
    		model.addAttribute("error", "Kelurahan tidak ditemukan");
    		return "not-found";
    	}
    	
    	List<PendudukModel> listPendudukKelurahan = pendudukDAO.selectPendudukByKelurahan(kelurahan);
    	PendudukModel termudaKelurahan = listPendudukKelurahan.get(0);
    	PendudukModel tertuaKelurahan = listPendudukKelurahan.get(0);
    	
    	for(int i = 0; i < listPendudukKelurahan.size(); i++) {
    		if(listPendudukKelurahan.get(i).getJenis_kelamin().equals("1")) {
    			listPendudukKelurahan.get(i).setJenis_kelamin("Perempuan");
    		} else {
    			listPendudukKelurahan.get(i).setJenis_kelamin("Laki-Laki");
    		}
    		
    		if(termudaKelurahan.getTanggal_lahir().compareTo(listPendudukKelurahan.get(i).getTanggal_lahir()) < 0) {
        		termudaKelurahan = listPendudukKelurahan.get(i);
        	}
    		
    		if(tertuaKelurahan.getTanggal_lahir().compareTo(listPendudukKelurahan.get(i).getTanggal_lahir()) > 0) {
    			tertuaKelurahan = listPendudukKelurahan.get(i);
    		}
    	}
    	
    	List<PendudukModel> listPendudukKecamatan = pendudukDAO.selectPendudukByKecamatan(kecamatan);
    	PendudukModel termudaKecamatan = listPendudukKecamatan.get(0);
    	PendudukModel tertuaKecamatan = listPendudukKecamatan.get(0);
    	
    	for(int i = 0; i < listPendudukKecamatan.size(); i++) {
    		if(listPendudukKecamatan.get(i).getJenis_kelamin().equals("1")) {
    			listPendudukKecamatan.get(i).setJenis_kelamin("Perempuan");
    		} else {
    			listPendudukKecamatan.get(i).setJenis_kelamin("Laki-Laki");
    		}
    		
    		if(termudaKecamatan.getTanggal_lahir().compareTo(listPendudukKecamatan.get(i).getTanggal_lahir()) < 0) {
        		termudaKecamatan = listPendudukKecamatan.get(i);
        	}
    		
    		if(tertuaKecamatan.getTanggal_lahir().compareTo(listPendudukKecamatan.get(i).getTanggal_lahir()) > 0) {
    			tertuaKecamatan = listPendudukKecamatan.get(i);
    		}
    	}
    	
    	model.addAttribute("termudaKecamatan", termudaKecamatan);
    	model.addAttribute("tertuaKecamatan", tertuaKecamatan);
    	model.addAttribute("pendudukKecamatan", listPendudukKecamatan);
    	
    	model.addAttribute("termudaKelurahan", termudaKelurahan);
    	model.addAttribute("tertuaKelurahan", tertuaKelurahan);
    	model.addAttribute("pendudukKelurahan", listPendudukKelurahan);
    	
    	model.addAttribute("kota", kotaDAO.selectKotaByID(kota));
    	model.addAttribute("kecamatan", kecamatanDAO.selectKecamatanByID(kecamatan));
    	model.addAttribute("kelurahan", kelurahanDAO.selectKelurahanByID(kelurahan));
    	
    	return "result-pencarian";
    }
}
