package cn.jadeStones.Dao;

import cn.jadeStones.Entity.Test;

public interface TestMapper {

	Test selectById(Integer id);
	
	Test selectBySelective(Test test);
}
