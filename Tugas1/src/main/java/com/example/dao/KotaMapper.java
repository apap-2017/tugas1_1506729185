package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	
	@Select("select id, nama_kota from kota")
	List<KotaModel> selectKota();
	
	@Select("select * from kota where id = #{id}")
	KotaModel selectKotaByID (@Param("id") String id);
	
	@Select("select * from kota")
	List<KotaModel> selectAllKota();
}
