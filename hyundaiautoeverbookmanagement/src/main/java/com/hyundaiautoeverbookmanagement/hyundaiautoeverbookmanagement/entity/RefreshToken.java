package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "REFRESH_TOKEN")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "RT_KEY")
    private String key;

    @Column(name = "RT_VALUE")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
