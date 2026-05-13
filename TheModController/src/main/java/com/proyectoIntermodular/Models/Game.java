package com.proyectoIntermodular.Models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  // @Column(nullable = false)
  // private String imagePath;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private int downloads;

  @Column(nullable = false)
  private int packs;

  public Game() {
  }

  public Game(String name) {
    this.name = name;
    // this.imagePath = imagePath;
    this.downloads = 0;
    this.packs = 0;
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

  // public String getImagePath() {
  //   return imagePath;
  // }

  // public void setImagePath(String imagePath) {
  //   this.imagePath = imagePath;
  // }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
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

  public int getPacks() {
    return packs;
  }

  public void setPacks(int packs) {
    this.packs = packs;
  }

  public void incrementPacks() {
    this.packs++;
  }

  public void decrementPacks() {
    this.packs--;
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