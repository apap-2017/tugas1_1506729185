package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	
	@Select("select id, nama_kecamatan from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatanByIdKota(String id_kota);
	
	@Select("select * from kecamatan where id = #{id}")
    KecamatanModel selectKecamatanByID (@Param("id") String id);

    @Select("select id_kota from kecamatan where id = #{id}")
    String selectKecamatanByKota (@Param("id") String id);
    
    @Select("select * from kecamatan")
    List<KecamatanModel> selectAllKecamatan();
}
