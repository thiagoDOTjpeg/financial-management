package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.TransactionType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends AuditableEntity{@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

  @Column(nullable = false, length = 100)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private TransactionType type;

  @Column(length = 50)
  private String icon;

  @Column(length = 7)
  private String color;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_category_id")
  private Category parentCategory;

  @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
  private Set<Category> subCategories = new HashSet<>();

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @OneToMany(mappedBy = "category")
  private Set<Transaction> transactions = new HashSet<>();

  @OneToMany(mappedBy = "category")
  private Set<Budget> budgets = new HashSet<>();


  public boolean isSubCategory() {
    return parentCategory != null;
  }

  public Category() {
  }

  private Category(Builder builder) {
    this.user = builder.user;
    this.name = builder.name;
    this.type = builder.type;
    this.icon = builder.icon;
    this.color = builder.color;
    this.parentCategory = builder.parentCategory;
    this.subCategories = builder.subCategories;
    this.isActive = builder.isActive;
    this.transactions = builder.transactions;
    this.budgets = builder.budgets;
  }

  public static class Builder {
    private User user;
    private String name;
    private TransactionType type;
    private String icon;
    private String color;
    private Category parentCategory;
    private Set<Category> subCategories = new HashSet<>();
    private Boolean isActive = true;
    private Set<Transaction> transactions = new HashSet<>();
    private Set<Budget> budgets = new HashSet<>();

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder type(TransactionType type) {
      this.type = type;
      return this;
    }

    public Builder icon(String icon) {
      this.icon = icon;
      return this;
    }

    public Builder color(String color) {
      this.color = color;
      return this;
    }

    public Builder parentCategory(Category parentCategory) {
      this.parentCategory = parentCategory;
      return this;
    }

    public Builder subCategories(Set<Category> subCategories) {
      this.subCategories = subCategories;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder transactions(Set<Transaction> transactions) {
      this.transactions = transactions;
      return this;
    }

    public Builder budgets(Set<Budget> budgets) {
      this.budgets = budgets;
      return this;
    }

    public Category build() {
      return new Category(this);
    }
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public Set<Category> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(Set<Category> subCategories) {
    this.subCategories = subCategories;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Set<Budget> getBudgets() {
    return budgets;
  }

  public void setBudgets(Set<Budget> budgets) {
    this.budgets = budgets;
  }
}
