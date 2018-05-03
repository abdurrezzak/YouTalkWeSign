package com.youtalkwesign.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.youtalkwesign.model.Heart;

public interface HeartRepository extends CrudRepository<Heart, Integer> {
	public Heart findOneByUsernameAndVideoId(String username, String videoId);
	public List<Heart> findByUsername(String username);
	public void deleteByUsernameAndVideoId(String username, String videoId);
}
