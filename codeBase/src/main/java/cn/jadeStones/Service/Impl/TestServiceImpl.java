package cn.jadeStones.Service.Impl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.jadeStones.Dao.TestMapper;
import cn.jadeStones.Entity.CacheRedisSerializable;
import cn.jadeStones.Entity.Test;
import cn.jadeStones.Service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper mapper;
	@Autowired
	private CacheManager cacheManager;

	public Test selectById() {
		return mapper.selectById(1);
	}
	
	public Test doUserLogin(Test test){
		return mapper.selectBySelective(test);
	}

	@Override
	@Cacheable(value="test",key="'myTest'+#key")
	public Object testEhcache(String key) {
		System.out.println(cacheManager.getCache("test").get(key));
		int rand = RandomUtils.nextInt(1, 10);
		System.out.println(String.format("rand = %s", rand));
		CacheRedisSerializable aa = new CacheRedisSerializable();
		aa.setId(1);
		aa.setName("123");
		
		return JSONObject.toJSONString(aa);
	}
}
