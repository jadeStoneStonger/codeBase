package cn.jadeStones.Service;

import cn.jadeStones.Entity.Test;


public interface TestService {

	Test selectById();
	
	Test doUserLogin(Test test);
	
	Object testEhcache(String key);
}
