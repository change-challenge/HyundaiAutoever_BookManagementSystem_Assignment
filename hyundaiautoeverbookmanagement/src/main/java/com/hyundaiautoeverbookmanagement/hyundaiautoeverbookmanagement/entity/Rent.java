package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_ID", insertable = false, updatable = false)
    private Copy copy;

    @Column(name = "RENT_START_DATE", nullable = false)
    private Date rentStartDate;

    @Column(name = "RENT_END_DATE", nullable = false)
    private Date rentEndDate;

    @Column(name = "RENT_RETURNED_DATE")
    private Date rentReturnedDate;
}
