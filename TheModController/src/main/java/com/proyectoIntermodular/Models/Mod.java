package com.proyectoIntermodular.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "mods")
public class Mod {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @ManyToMany(mappedBy = "mods")
  private List<Pack> packs = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties({ "mods", "password", "email", "followers" })
  private User author;

  @ManyToMany
  @JoinTable(name = "mod_dependencies", joinColumns = @JoinColumn(name = "mod_id"), inverseJoinColumns = @JoinColumn(name = "dependency_id"))
  @JsonIgnoreProperties({ "dependencies", "packs" })
  private List<Mod> dependencies = new ArrayList<>();

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private int downloads = 0;

  public Mod() {
  }

  public Mod(String name, String title, String description, User author) {
    this.name = name;
    this.title = title;
    this.description = description;
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getAuthor() {
    return author;
  }

  public List<Pack> getPacks() {
    return packs;
  }

  public void setPacks(List<Pack> packs) {
    this.packs = packs;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void addPack(Pack pack) {
    this.packs.add(pack);
  }

  public void removePack(Pack pack) {
    this.packs.remove(pack);
  }

  public void addDependency(Mod mod) {
    this.dependencies.add(mod);
  }

  public void removeDependency(Mod mod) {
    this.dependencies.remove(mod);
  }

  public List<Mod> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<Mod> dependencies) {
    this.dependencies = dependencies;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public int getDownloads() {
    return downloads;
  }

  public void setDownloads(int downloads) {
    this.downloads = downloads;
  }

  public void incrementDownloads() {
    this.downloads++;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

}
