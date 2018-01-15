package cn.jadeStones.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.jadeStones.Dao.TestMapper;
import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;

public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper mapper;

	public Test selectById() {
		return mapper.selectById(1);
	}
	
	public Test doUserLogin(Test test){
		return mapper.selectBySelective(test);
	}
	

}
