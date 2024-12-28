package com.example.javawebcourse.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder(toBuilder = true)
public class Category {
    UUID id;
    String name;
}