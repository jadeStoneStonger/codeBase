package cn.jadeStones.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.jadeStones.Dao.TestMapper;
import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private Environment ev;
	
	@Autowired
	private TestMapper mapper;

	public Test selectById() {
		ev.getProperty("aaa");
		return mapper.selectById(1);
	}
	

}
