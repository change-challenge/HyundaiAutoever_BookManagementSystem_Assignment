package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "RENT")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_ID", insertable = true, updatable = true)
    private Copy copy;

    @Column(name = "RENT_START_DATE", nullable = false)
    private LocalDate rentStartDate;

    @Column(name = "RENT_END_DATE", nullable = false)
    private LocalDate rentEndDate;

    @Column(name = "RENT_RETURNED_DATE")
    private LocalDate rentReturnedDate;

    @Column(name = "EXTENDABLE")
    private Boolean extendable;
}
