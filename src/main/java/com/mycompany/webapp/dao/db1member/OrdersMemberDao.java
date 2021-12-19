package com.mycompany.webapp.dao.db1member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrdersMemberDao {
	void reflectMileage(@Param("mid") String mid, @Param("mileage") int mileage);
	int getOrdersMember(String mid);
}
