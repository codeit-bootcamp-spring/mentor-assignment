package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.model.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.jcf.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class DiscodeitJavaApplication {
	static void userTest(UserService userService) {
		// Create Test
		User user = userService.createUser("woody", "woody@codeit.com", "woody1234", "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-10n7ovy_9b42e613.jpeg");
		System.out.println("Created User: " + user);
		// Find Test
		Optional<User> foundUser = userService.findUser(user.getId());
		if (foundUser.isPresent()) {
			System.out.println("Found User: " + foundUser.get());
		} else {
			System.out.println("User Not Found with Id: " + user.getId());
		}
		// Update Test
		User userToUpdate = foundUser.get();
		userToUpdate.updateEmail("woody123@codeit.com");
		User updatedUser = userService.updateUser(userToUpdate);
		System.out.println("Updated User: " + updatedUser);
		// Delete Test
		userService.deleteUser(user.getId());
		Optional<User> notFoundUser = userService.findUser(user.getId());
		System.out.println("User Not Found: " + notFoundUser.isEmpty());
	}
	static void categoryTest(CategoryService categoryService) {
		// Create Test
		Category category = categoryService.createCategory("favorite");
		System.out.println("Created Category: " + category);
		// Find Test
		Optional<Category> foundCategory = categoryService.findCategory(category.getId());
		if (foundCategory.isPresent()) {
			System.out.println("Found Category: " + foundCategory.get());
		} else {
			System.out.println("Category Not Found with Id: " + category.getId());
		}
		// Update Test
		Category categoryToUpdate = foundCategory.get();
		categoryToUpdate.updateName("my favorite");
		Category updatedCategory = categoryService.updateCategory(categoryToUpdate);
		System.out.println("Updated Category: " + updatedCategory);
		// Delete Test
		categoryService.deleteCategory(category.getId());
		Optional<Category> notFoundCategory = categoryService.findCategory(category.getId());
		System.out.println("Category Not Found: " + notFoundCategory.isEmpty());
	}
	static void channelTest(ChannelService channelService) {
		// Create Test
		Channel channel = channelService.createChannel("sports", "Channel for sports.", UUID.randomUUID(), UUID.randomUUID());
		System.out.println("Created Channel: " + channel);
		// Find Test
		Optional<Channel> foundChannel = channelService.findChannel(channel.getId());
		if (foundChannel.isPresent()) {
			System.out.println("Found Channel: " + foundChannel.get());
		} else {
			System.out.println("Channel Not Found with Id: " + channel.getId());
		}
		// Update Test
		Channel channelToUpdate = foundChannel.get();
		channelToUpdate.updateDescription("Welcome to sports channel.");
		Channel updatedChannel = channelService.updateChannel(channelToUpdate);
		System.out.println("Updated Channel: " + updatedChannel);
		// Delete Test
		channelService.deleteChannel(channel.getId());
		Optional<Channel> notFoundChannel = channelService.findChannel(channel.getId());
		System.out.println("Channel Not Found: " + notFoundChannel.isEmpty());
	}
	static void messageTest(MessageService messageService) {
		// Create Test
		Message message = messageService.createMessage("EPL is my favorite!!", UUID.randomUUID(), UUID.randomUUID());
		System.out.println("Created Message: " + message);
		// Find Test
		Optional<Message> foundMessage = messageService.findMessage(message.getId());
		if (foundMessage.isPresent()) {
			System.out.println("Found Message: " + foundMessage.get());
		} else {
			System.out.println("Message Not Found with Id: " + message.getId());
		}
		// Update Test
		Message messageToUpdate = foundMessage.get();
		messageToUpdate.updateContent("Any Premier League fans here?", LocalDateTime.now());
		Message updatedMessage = messageService.updateMessage(messageToUpdate);
		System.out.println("Updated Message: " + updatedMessage);
		// Delete Test
		messageService.deleteMessage(message.getId());
		Optional<Message> notFoundMessage = messageService.findMessage(message.getId());
		System.out.println("Message Not Found: " + notFoundMessage.isEmpty());
	}
	static void directMessageTest(DirectMessageService directMessageService) {
		// Create Test
		DirectMessage directMessage = directMessageService.createDirectMessage("EPL is my favorite!!", UUID.randomUUID(), UUID.randomUUID());
		System.out.println("Created DirectMessage: " + directMessage);
		// Find Test
		Optional<DirectMessage> foundDirectMessage = directMessageService.findDirectMessage(directMessage.getId());
		if (foundDirectMessage.isPresent()) {
			System.out.println("Found DirectMessage: " + foundDirectMessage.get());
		} else {
			System.out.println("DirectMessage Not Found with Id: " + directMessage.getId());
		}
		// Update Test
		DirectMessage directMessageToUpdate = foundDirectMessage.get();
		directMessageToUpdate.updateContent("You a fan of EPL too?", LocalDateTime.now());
		DirectMessage updatedDirectMessage = directMessageService.updateDirectMessage(directMessageToUpdate);
		System.out.println("Updated DirectMessage: " + updatedDirectMessage);
		// Delete Test
		directMessageService.deleteDirectMessage(directMessage.getId());
		Optional<DirectMessage> notFoundDirectMessage = directMessageService.findDirectMessage(directMessage.getId());
		System.out.println("DirectMessage Not Found: " + notFoundDirectMessage.isEmpty());
	}
	
	public static void main(String[] args) {
		// Service Initialization
		UserService userService = new JCFUserService();
		CategoryService categoryService = new JCFCategoryService();
		ChannelService channelService = new JCFChannelService(categoryService, userService);
		MessageService messageService = new JCFMessageService(userService, channelService);
		DirectMessageService directMessageService = new JCFDirectMessageService(userService);
		// Test
		userTest(userService);
		categoryTest(categoryService);
		channelTest(channelService);
		messageTest(messageService);
		directMessageTest(directMessageService);
	}
}