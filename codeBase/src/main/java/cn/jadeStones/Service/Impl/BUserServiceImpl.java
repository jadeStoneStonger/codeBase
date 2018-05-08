package cn.jadeStones.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jadeStones.Dao.BUserMapper;
import cn.jadeStones.Entity.BUser;
import cn.jadeStones.Entity.Base;

@Service
public class BUserServiceImpl extends BaseBusinessServiceImpl {
	
	@Autowired
	private BUserMapper bUserMapper;
	
	@Override
	public Base selectBySelective(Base base){
		return bUserMapper.selectBySelective((BUser)base);
	}
}
