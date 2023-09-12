package com.nexmotion.deletedata;

import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;

@Mapper
public interface DeleteDataMapper {
    
    void deletedata(DeleteData deleteData) throws SQLException;
}
