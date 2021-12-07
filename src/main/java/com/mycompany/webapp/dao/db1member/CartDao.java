package com.mycompany.webapp.dao.db1member;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Cart;

@Mapper
public interface CartDao {
	int deleteByMemberIdAndProductStockId(Cart cart);
}
