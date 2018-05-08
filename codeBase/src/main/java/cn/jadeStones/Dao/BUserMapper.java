package cn.jadeStones.Dao;

import cn.jadeStones.Entity.BUser;

public interface BUserMapper {

	BUser selectById(Integer id);
	
	BUser selectBySelective(BUser user);
}
