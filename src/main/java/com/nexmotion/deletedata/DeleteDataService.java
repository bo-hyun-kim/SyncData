package com.nexmotion.deletedata;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDataService implements DeleteDataMapper {

    @Autowired
    private DeleteDataMapper deletedataMapper;

    @Override
    public void deletedata(DeleteData deleteData) throws SQLException {
        deletedataMapper.deletedata(deleteData);
    }
}
