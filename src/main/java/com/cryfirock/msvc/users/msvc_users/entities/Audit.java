package com.cryfirock.msvc.users.msvc_users.entities;

/**
 * Dependencies
 */
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// The class can embed its content in another and autogenerates methods
@AllArgsConstructor
@AttributeOverrides({
        // Maps the table column linked to the entity
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at"))
})
@Data
@Embeddable
@NoArgsConstructor
public class Audit {

    /**
     * Attributes
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Methods
     */
    @PrePersist
    public void prePersist() {
        // Initializes attributes before storing them
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        // Initializes the attribute before updating it
        this.updatedAt = LocalDateTime.now();
    }

}
