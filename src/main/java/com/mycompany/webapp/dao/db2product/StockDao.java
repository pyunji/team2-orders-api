package com.mycompany.webapp.dao.db2product;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.ordercomplete.Stock;

@Mapper
public interface StockDao {
	Stock select(Stock stock);
	int update(Stock stock);
}
