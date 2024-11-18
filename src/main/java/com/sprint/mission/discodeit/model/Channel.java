package com.sprint.mission.discodeit.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Channel {
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
    private UUID ownerId;
    private LocalDateTime createdAt;

    public Channel(UUID id, String name, String description, UUID categoryId, UUID ownerId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", ownerId=" + ownerId +
                ", createdAt=" + createdAt +
                '}';
    }
}
