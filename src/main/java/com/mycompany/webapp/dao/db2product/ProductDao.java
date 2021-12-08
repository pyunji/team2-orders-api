package com.mycompany.webapp.dao.db2product;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.OrderProduct;

@Mapper
public interface ProductDao {
	OrderProduct selectProductByPcolorid(String pcolorid);
}
