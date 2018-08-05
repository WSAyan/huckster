package com.wsayan.huckster.core.presenter;

import com.wsayan.huckster.core.config.db.DbConfig;
import com.wsayan.huckster.core.config.db.DbCrud;
import com.wsayan.huckster.core.config.db.DbTables;
import com.wsayan.huckster.core.model.binder.DataRow;
import com.wsayan.huckster.core.model.binder.DataTable;

/**
 * Created by wahid.sadique on 9/11/2017.
 */

class DbInteractor implements IDbInteractor {
    private DbCrud dbCrud;
    private DbConfig dbConfig;

    DbInteractor(DbCrud dbCrud, DbConfig dbConfig) {
        this.dbCrud = dbCrud;
        this.dbConfig = dbConfig;
    }

    @Override
    public DataTable getFavourites() {
        String selectQuery = "SELECT * FROM " + DbTables.FAVOURITES_TABLE;
        return dbCrud.selectData(selectQuery, null, dbConfig.getSqLiteDatabase());
    }

    @Override
    public int markFavourites(String id, String name, String description, String url) {
        DataTable dataTable = new DataTable(DbTables.FAVOURITES_TABLE);
        DataRow dataRow = new DataRow();
        dataRow.add("id", id);
        dataRow.add("name", name);
        dataRow.add("description", description);
        dataRow.add("url", url);
        dataTable.add(0, dataRow);
        return dbCrud.insertData(dataTable, dbConfig.getSqLiteDatabase());
    }

    @Override
    public int removeFavourites(String id) {
        String tableName = DbTables.FAVOURITES_TABLE;
        String[] args = {id};
        String whereClause = " id = ? ";
        return dbCrud.deleteData(tableName, whereClause, args, dbConfig.getSqLiteDatabase());
    }

    @Override
    public boolean checkFavourites(String id) {
        String selectQuery = "SELECT * FROM " + DbTables.FAVOURITES_TABLE + " WHERE id = ?";
        String[] args = {id};
        return (dbCrud.selectData(selectQuery, args, dbConfig.getSqLiteDatabase())).size() > 0;
    }
}
