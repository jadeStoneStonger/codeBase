package cn.jadeStones.Entity;

import java.io.Serializable;

public class CacheRedisSerializable  implements Serializable{

	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isMan(){
		return true;
	}

	@Override
	public String toString() {
		return "CacheRedisSerializable [id=" + id + ", name=" + name + "]";
	}
	
}
