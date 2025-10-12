package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private NotificationType type;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String message;

  @Column(name = "related_entity_type", length = 50)
  private String relatedEntityType;

  @Column(name = "related_entity_id")
  private UUID relatedEntityId;

  @Column(name = "is_read", nullable = false)
  private Boolean isRead = false;

  @Column(name = "read_at")
  private LocalDateTime readAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public void markAsRead() {
    this.isRead = true;
    this.readAt = LocalDateTime.now();
  }

  public Notification() {
  }

  private Notification(Builder builder) {
    this.id = builder.id;
    this.user = builder.user;
    this.type = builder.type;
    this.title = builder.title;
    this.message = builder.message;
    this.relatedEntityType = builder.relatedEntityType;
    this.relatedEntityId = builder.relatedEntityId;
    this.isRead = builder.isRead;
    this.createdAt = builder.createdAt;
    this.deletedAt = builder.deletedAt;
  }

  public static class Builder {
    private UUID id;
    private User user;
    private NotificationType type;
    private String title;
    private String message;
    private String relatedEntityType;
    private UUID relatedEntityId;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder type(NotificationType type) {
      this.type = type;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder relatedEntityType(String relatedEntityType) {
      this.relatedEntityType = relatedEntityType;
      return this;
    }

    public Builder relatedEntityId(UUID relatedEntityId) {
      this.relatedEntityId = relatedEntityId;
      return this;
    }

    public Builder isRead(Boolean isRead) {
      this.isRead = isRead;
      return this;
    }

    public Builder readAt(LocalDateTime readAt) {
      this.readAt = readAt;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder deletedAt(LocalDateTime deletedAt) {
      this.deletedAt = deletedAt;
      return this;
    }

    public Notification build() {
      return new Notification(this);
    }
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRelatedEntityType() {
    return relatedEntityType;
  }

  public void setRelatedEntityType(String relatedEntityType) {
    this.relatedEntityType = relatedEntityType;
  }

  public UUID getRelatedEntityId() {
    return relatedEntityId;
  }

  public void setRelatedEntityId(UUID relatedEntityId) {
    this.relatedEntityId = relatedEntityId;
  }

  public Boolean getRead() {
    return isRead;
  }

  public void setRead(Boolean read) {
    isRead = read;
  }

  public LocalDateTime getReadAt() {
    return readAt;
  }

  public void setReadAt(LocalDateTime readAt) {
    this.readAt = readAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }
}
