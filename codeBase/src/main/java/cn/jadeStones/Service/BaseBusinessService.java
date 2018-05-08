package cn.jadeStones.Service;

import cn.jadeStones.Entity.Base;
import cn.jadeStones.base.exception.BusinessException;

public interface BaseBusinessService {

	void save(Base base) throws BusinessException;
	
	void update(Base base) throws BusinessException;
	
	void delete(Base base) throws BusinessException;

	Base select(Integer mktId) throws BusinessException;
	
	Base selectBySelective(Base base) throws BusinessException;
}
