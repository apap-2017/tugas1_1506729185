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

import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	
	@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
    KeluargaModel selectKeluargaByNomorKK (@Param("nomor_kk") String nomor_kk);
	
	@Select("select * from keluarga where id = #{id}")
    KeluargaModel selectKeluargaByID (@Param("id") String id);

    @Select("select id_kelurahan from keluarga where id = #{id}")
    String selectKeluargaByKelurahan (@Param("id") String id);

    @Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void tambahKeluarga (KeluargaModel keluarga);
    
    @Select("select count(*) from keluarga where nomor_kk like #{nomor_kk}")
	int hitungNKK (String nomor_kk);
    
	@Select("select max(id) from keluarga")
	int hitungID();
	
	@Update("UPDATE keluarga SET nomor_kk= #{nomor_kk}, alamat= #{alamat}, rt= #{rt}, rw= #{rw}, id_kelurahan=#{id_kelurahan} WHERE id=#{id}")
	void ubahKeluarga (KeluargaModel keluarga);

	@Update("UPDATE keluarga SET is_tidak_berlaku = 0 WHERE id = #{id}")
    void setKeluargaActive (@Param("id") String id);

    @Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
    void setKeluargaInactive (@Param("id") String id);
    
    //property yang di model, kolom yang di tabel
    /*@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
    @Results(value = {
    	    @Result(property="nama", column="nama"),
    	    @Result(property="nik", column="nik"),
    	    @Result(property="jenis_kelamin", column="jenis_kelamin"),
    	    @Result(property="tempat_lahir", column="tempat_lahir"),
    	    @Result(property="agama", column="agama"),
    	    @Result(property="pekerjaan", column="pekerjaan"),
    	    @Result(property="status_perkawinan", column="status_perkawinan"),
    	    @Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
    	    @Result(property="is_wni", column="is_wni",
    	    		javaType = List.class,
    	    		many=@Many(select="selectKeluargaWithPenduduk"))
    	})
    List<PendudukModel> selectKeluargaWithPenduduk (@Param("nomor_kk") String nomor_kk);*/
}
