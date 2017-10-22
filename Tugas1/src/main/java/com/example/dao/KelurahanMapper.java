package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	
	@Select("select id, nama_kelurahan from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanByIdKecamatan (String id_kecamatan);
	
	@Select("select * from kelurahan where id = #{id}")
    KelurahanModel selectKelurahanByID (@Param("id") String id);

    @Select("select id_kecamatan from kelurahan where id = #{id}")
    String selectKelurahanByKecamatan (@Param("id") String id);
    
    @Select("select * from kelurahan")
    List<KelurahanModel> selectAllKelurahan();
}
