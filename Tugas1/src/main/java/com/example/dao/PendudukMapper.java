package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {

	@Select("select * from penduduk where nik = #{nik}")
	PendudukModel selectPendudukByNIK (@Param("nik") String nik);
	
	@Select("select * from penduduk where id_keluarga = #{id_keluarga}")
	List<PendudukModel> selectPendudukByKeluarga (@Param("id_keluarga") String id_keluarga);
	
	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void tambahPenduduk (PendudukModel penduduk);
	
	@Select("select count(*) from penduduk where nik like #{nik}")
	int hitungNIK (String nik);
	
	@Select("select max(id) from penduduk")
	int hitungID();
	
	@Update("UPDATE penduduk SET nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin}, is_wni=#{is_wni}, id_keluarga=#{id_keluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_dalam_keluarga}, golongan_darah=#{golongan_darah} WHERE id=#{id}")
	void ubahPenduduk (PendudukModel penduduk);
	 
	@Update("update penduduk set is_wafat = 1 where nik=#{nik}")
	void ubahStatusKematian(String nik);
	
	@Select("select count(*) from penduduk where id_keluarga=#{id_keluarga} and is_wafat = 0")
	int hitungTidakMati(@Param("id_keluarga") String id_keluarga);
	
	@Select("select * from penduduk p, keluarga k where p.id_keluarga = k.id and id_kelurahan = #{id_kelurahan}")
    List<PendudukModel> selectPendudukByKelurahan(String id_kelurahan);
	
	@Select("select * from penduduk p, keluarga k, kelurahan kel where p.id_keluarga = k.id and k.id_kelurahan = kel.id and id_kecamatan = #{id_kecamatan}")
    List<PendudukModel> selectPendudukByKecamatan(String id_kecamatan);
	
	/*@Select("select penduduk.nik, penduduk.nama, penduduk.tempat_lahir, penduduk.tanggal_lahir, keluarga.alamat, keluarga.rt, keluarga.rw, kelurahan.nama_kelurahan, kecamatan.nama_kecamatan, kota.nama_kota, penduduk.golongan_darah, penduduk.agama, penduduk.status_perkawinan, penduduk.pekerjaan, penduduk.is_wni, penduduk.is_wafat "
			+ "from penduduk, keluarga, kota, kecamatan, kelurahan " 
			+ "where penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = kelurahan.id and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id")
    @Results(value = {
	    @Result(property="nik", column="penduduk.nik"),
	    @Result(property="nama", column="penduduk.nama"),
	    @Result(property="ttl", column="penduduk.tempat_lahir"),
	    @Result(property="courses", column="npm",
	    		javaType = List.class,
	    		many=@Many(select="selectCourses"))
	})PendudukModel selectPendudukByNIK (@Param("nik") String nik);*/
}
