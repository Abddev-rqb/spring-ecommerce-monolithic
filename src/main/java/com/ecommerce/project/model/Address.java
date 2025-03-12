package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 3, message = "Enter atleast 3 characters to the street field")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Enter atleast 5 characters to the building field")
    private String buildingName;

    @NotBlank
    @Size(min = 3, message = "Enter atleast 3 characters to the city field")
    private String city;

    @NotBlank
    @Size(min = 3, message = "Enter atleast 3 characters to the state field")
    private String state;

    @NotBlank
    @Size(min = 3, message = "Enter atleast 3 characters to the country field")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Enter atleast 5 characters to the pincode field")
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
