package com.example.exercise;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.exercise.model.GroupResult;
import com.example.exercise.model.User;
import com.example.exercise.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

	@Test
	public void testSorting() throws JSONException, IOException, ParseException,
			ExecutionException {
		List<GroupResult> sortedGroupResult = UserService.loadSortedUserGroup();

		Assert.assertTrue((sortedGroupResult.get(0).getLastname()
				.compareTo(sortedGroupResult.get(1).getLastname())) < 0);
	}

	@Test
	public void testGetUserByEmail() throws JSONException, IOException,
			ParseException, ExecutionException {
		User existingUser = UserService.getUserByEmail("lily.fox@secutix.com");
		User fictitiousUser = UserService
				.getUserByEmail("fictitious@email.com");

		Assert.assertTrue(existingUser.getId()
				.equals("5de06fb7-29bd-4fe3-85b2-8e1b343d1311"));
		Assert.assertNull(fictitiousUser);
	}

	@Test
	public void testValidEmail() {
		String invalidEmail1 = "john";
		String invalidEmail2 = "john.com";
		String invalidEmail3 = "john@.com";
		String invalidEmail4 = "@gmail.com";
		String invalidEmail5 = "john@gmail.m";
		String invalidEmail6 = "";
		String validEmail = "john@gmail.com";

		Assert.assertFalse(UserService.isValidEmail(invalidEmail1));
		Assert.assertFalse(UserService.isValidEmail(invalidEmail2));
		Assert.assertFalse(UserService.isValidEmail(invalidEmail3));
		Assert.assertFalse(UserService.isValidEmail(invalidEmail4));
		Assert.assertFalse(UserService.isValidEmail(invalidEmail5));
		Assert.assertFalse(UserService.isValidEmail(invalidEmail6));
		Assert.assertTrue(UserService.isValidEmail(validEmail));
	}

}
