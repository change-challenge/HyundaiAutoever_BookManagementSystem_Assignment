package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "RENT")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Member user;

    @ManyToOne
    @JoinColumn(name = "COPY_ID", nullable = false)
    private Copy copy;

    @Column(name = "RENT_START_DATE", nullable = false)
    private Date rentStartDate;

    @Column(name = "RENT_END_DATE", nullable = false)
    private Date rentEndDate;

    @Column(name = "RENT_RETURNED_DATE")
    private Date rentReturnedDate;
}
