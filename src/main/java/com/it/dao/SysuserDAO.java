package com.it.dao;

import com.it.entity.Sysuser;

import java.util.HashMap;
import java.util.List;

public interface SysuserDAO {
	List<Sysuser> selectAll(HashMap map);
	Sysuser findById(Integer id);
	void update(Sysuser sysuser);
	void add(Sysuser sysuser);


}
