package com.ecommerce.project.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name= "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generates auto Id in the DB ie.,IDENTITY
    private Long categoryId;
    @NotBlank // validates whether it is blank or not passed args
    @Size(min=3, message = "Should consist atleast 3 characters")
    private String categoryName;
}
