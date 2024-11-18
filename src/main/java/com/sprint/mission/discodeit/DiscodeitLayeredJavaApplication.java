package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.model.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.*;

import java.util.List;

public class DiscodeitLayeredJavaApplication {
    static List<User> setUpUsers(UserService userService) {
        // User
        User user1 = userService.createUser("woody", "woody@codeit.com", "woody1234", "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-10n7ovy_9b42e613.jpeg");
        System.out.println("Created User1: " + user1);
        User user2 = userService.createUser("buzz", "buzz@codeit.com", "buzz1234", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSr8RwnYAsvEYAvc199pUF6AIYE6CIRBNVGg&s");
        System.out.println("Created User2: " + user2);

        return List.of(user1, user2);
    }

    static Category setUpCategory(CategoryService categoryService) {
        // Category
        Category category = categoryService.createCategory("favorite");
        System.out.println("Created Category: " + category);
        return category;
    }

    static Channel channelTest(ChannelService channelService, Category category, User owner) {
        Channel channel = channelService.createChannel("sports", "Channel for sports.", category.getId(), owner.getId());
        System.out.println("Created Channel: " + channel);
        return channel;
    }

    static void messageTest(MessageService messageService, User author, Channel channel) {
        Message message = messageService.createMessage("EPL is my favorite!!", author.getId(), channel.getId());
        System.out.println("Created Message: " + message);
    }

    static void directMessageTest(DirectMessageService directMessageService, User sender, User receiver) {
        DirectMessage directMessage = directMessageService.createDirectMessage("EPL is my favorite!!", sender.getId(), receiver.getId());
        System.out.println("Created DirectMessage: " + directMessage);
    }

    public static void main(String[] args) {
        // Service Initialization
        UserService userService = new BasicUserService();
        CategoryService categoryService = new BasicCategoryService();
        ChannelService channelService = new BasicChannelService();
        MessageService messageService = new BasicMessageService();
        DirectMessageService directMessageService = new BasicDirectMessageService();

        // Set up
        List<User> users = setUpUsers(userService);
        Category category = setUpCategory(categoryService);

        // Test
        Channel channel = channelTest(channelService, category, users.get(0));
        messageTest(messageService, users.get(0), channel);
        directMessageTest(directMessageService, users.get(0), users.get(1));
    }
}