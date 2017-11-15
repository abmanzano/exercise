package com.example.exercise.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.exercise.model.User;
import com.example.exercise.service.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class UserGuavaCacheUtil {

	@Autowired
	private static UserService userService;

	public static LoadingCache<Integer, List<User>> getLoadingCache() {
		LoadingCache<Integer, List<User>> userCache = CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.build(new CacheLoader<Integer, List<User>>() {
					@Override
					public List<User> load(Integer id) throws Exception {
						return userService.loadUserIfCacheMiss();
					}
				});
		return userCache;
	}
}
