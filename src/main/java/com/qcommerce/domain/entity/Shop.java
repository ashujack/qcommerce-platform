package com.qcommerce.domain.entity;

import com.qcommerce.domain.valueobject.Coordinates;
import com.qcommerce.domain.exception.DomainException;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String phone;
    private String email;
    private Coordinates location;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String businessLicenseNumber;
    private String gstin;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private boolean isOpen;
    private ShopStatus status;
    private boolean isVerified;
    private LocalDateTime verificationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void verifyShop() {
        if (!ShopStatus.APPROVED.equals(this.status)) {
            throw new DomainException("Shop must be APPROVED before verification");
        }
        this.isVerified = true;
        this.verificationDate = LocalDateTime.now();
    }

    public boolean isCurrentlyOpen() {
        if (!isOpen || !isVerified) {
            return false;
        }
        LocalTime now = LocalTime.now();
        return !now.isBefore(openingTime) && !now.isAfter(closingTime);
    }

    public void validateLocation() {
        if (location == null || location.getLatitude() == null || location.getLongitude() == null) {
            throw new DomainException("Shop location (coordinates) is required");
        }
    }

    public enum ShopStatus { PENDING_APPROVAL, APPROVED, SUSPENDED, INACTIVE }
}