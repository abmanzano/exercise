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

	public static LoadingCache<String, List<User>> getLoadingCacheForUsers() {
		LoadingCache<String, List<User>> userCache = CacheBuilder.newBuilder()
				.maximumSize(1).expireAfterAccess(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, List<User>>() {
					@Override
					public List<User> load(String id) throws Exception {
						return userService.loadUserIfCacheMiss();
					}
				});
		return userCache;
	}
}
