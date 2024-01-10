package com.example.file.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Insert("insert into file_oss (from_name, to_name) values (#{FromName},#{ToName})")
    void InsertOss(String FromName,String ToName);

    @Select("select to_name from file_oss where from_name = #{FromName}")
    String SelectToNameOss(String FromName);

    @Delete("delete from file_oss where from_name = #{FromName}")
    void DeleteFileOss(String FromName);

    @Insert("insert into file_minio (from_name, url) values (#{FromName},#{url})")
    void InsertMinio(String FromName,String url);

    @Select("select url from file_minio where from_name = #{FromName}")
    String SelectToNameMinio(String FromName);

    @Delete("delete from file_minio where from_name = #{FromName}")
    void DeleteFileMinio(String FromName);

}
