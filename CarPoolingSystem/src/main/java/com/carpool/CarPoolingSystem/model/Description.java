package com.carpool.CarPoolingSystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "description")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "gender_preference")
    private String genderPreference;

    @Column(name ="pet_free")
    private boolean petFree;
    @Column(name ="smoke_free")
    private boolean smokeFree;
    @Column(name ="handicap_friendly")
    private boolean handicapFriendly;

    public Description() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGenderPreference() { return genderPreference; }
    public void setGenderPreference(String genderPreference) { this.genderPreference = genderPreference; }

    public boolean isPetFree() { return petFree; }
    public void setPetFree(boolean petFree) { this.petFree = petFree; }

    public boolean isSmokeFree() { return smokeFree; }
    public void setSmokeFree(boolean smokeFree) { this.smokeFree = smokeFree; }

    public boolean isHandicapFriendly() { return handicapFriendly; }
    public void setHandicapFriendly(boolean handicapFriendly) { this.handicapFriendly = handicapFriendly; }

    @Override
    public String toString() {
        return "Description{" +
                "email='" + email + '\'' +
                ", genderPreference='" + genderPreference + '\'' +
                ", petFree=" + petFree +
                ", smokeFree=" + smokeFree +
                ", handicapFriendly=" + handicapFriendly +
                '}';
    }
}
