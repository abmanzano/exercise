package com.example.exercise.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.exercise.model.GroupResult;
import com.example.exercise.model.User;
import com.example.exercise.util.UserGuavaCacheUtil;
import com.example.exercise.validation.UserValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;

public class UserService {

	public static final int EIGHTEEN = 18;

	// Implement a Guava Loading cache to load the user list from FIle and store
	// it in the cache

	/**
	 * Exercise 1: Return a list of user
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONException
	 * @throws ExecutionException
	 */
	public static List<User> loadUser() throws IOException, ParseException,
			JSONException, ExecutionException {
		// Try to use the Guava Loading cache
		LoadingCache<String, List<User>> usersCache = UserGuavaCacheUtil
				.getLoadingCacheForUsers();

		return usersCache.get("usersCache");
	}

	/**
	 * This method should be called by loading cache to load the user from file
	 * 
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<User> loadUserIfCacheMiss()
			throws JSONException, IOException, ParseException {
		// Step1: Read the user.json file
		Resource resource = new ClassPathResource("/user.json");
		FileReader fileReader = new FileReader(resource.getFile());

		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse((Reader) fileReader);
		String jsonAsString = a.toString();

		// Step2: Convert the user JSON string to java object
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = mapper.readValue(jsonAsString,
				new TypeReference<List<User>>() {
				});

		return users;
	}

	/**
	 * Exercise 2: Group user by last name and count adult and children
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONException
	 * @throws ExecutionException
	 */
	public static List<GroupResult> loadSortedUserGroup() throws IOException,
			ParseException, JSONException, ExecutionException {
		// Step1: Load the user and group them by last name, count adult and
		// children
		// and then return the list sorted by last name.
		List<GroupResult> groupResult = new ArrayList<>();

		// Get the full list of users using Guava cache
		List<User> listOfUsers = loadUser();

		// Iterate over the list of users and check if the surname is already in
		// the resulting list "groupResult"
		Iterator<User> iterator = listOfUsers.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			String lastname = user.getLastname();
			// JodaTime to get the age of the user
			Years age = Years.yearsBetween(new LocalDate(user.getDateOfBirth()),
					new LocalDate());
			int userAge = age.getYears();

			// Loop the resulting list to check if surname family is already
			// there
			boolean alreadyExists = false;
			for (GroupResult r : groupResult) {
				if (lastname.equals(r.getLastname())) {
					alreadyExists = true;
					// If family already exists add 1 to either adult or child
					// values depending on age
					if (userAge > EIGHTEEN) {
						r.setAdult(r.getAdult().intValue() + 1);
					} else {
						r.setChildren(r.getChildren().intValue() + 1);
					}
				}
			}

			if (!alreadyExists) {
				GroupResult newFamily = new GroupResult();

				newFamily.setLastname(lastname);
				if (userAge > EIGHTEEN) {
					newFamily.setAdult(1);
					newFamily.setChildren(0);
				} else {
					newFamily.setChildren(1);
					newFamily.setAdult(0);
				}

				groupResult.add(newFamily);
			}
		}

		// Sort ArrayList groupResult
		Collections.sort(groupResult);

		return groupResult;

	}

	/**
	 * Exercise 3: Load list of user and find the one with email supplied
	 * 
	 * @param email
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ExecutionException
	 */
	public static User getUserByEmail(final String email) throws JSONException,
			IOException, ParseException, ExecutionException {

		// Step1: Load the users
		List<User> listOfUsers = loadUser();

		// Step2. Filter the user based on email address, if more than one
		// return the oldest
		User userToBeReturned = null;

		for (User user : listOfUsers) {
			if (email.trim().equalsIgnoreCase(user.getEmail())) {
				if (userToBeReturned == null) {
					userToBeReturned = user;
				} else {
					if (user.getDateOfBirth()
							.before(userToBeReturned.getDateOfBirth())) {
						userToBeReturned = user;
					}
				}
			}
		}

		return userToBeReturned;
	}

	/**
	 * Returns true if valid email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		return UserValidator.validateEmail(email);
	}
}
