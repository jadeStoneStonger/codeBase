package cn.jadeStones.Service.Impl;

import cn.jadeStones.Entity.Base;
import cn.jadeStones.Service.BaseBusinessService;
import cn.jadeStones.base.exception.BusinessException;

public class BaseBusinessServiceImpl implements BaseBusinessService {
	@Override
	public void save(Base base) throws BusinessException{};
	@Override
	public void update(Base base) throws BusinessException{};
	@Override
	public void delete(Base base) throws BusinessException{};
	@Override
	public Base select(Integer mktId) throws BusinessException{return null;}
	@Override
	public Base selectBySelective(Base base) throws BusinessException {return null;};
}
