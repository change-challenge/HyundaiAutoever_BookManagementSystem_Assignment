package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "RENT")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", insertable = true, updatable = true)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_ID", insertable = true, updatable = true)
    private Copy copy;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "RETURNED_DATE")
    private LocalDate returnedDate;

    @Column(name = "IS_EXTENDABLE", nullable = false)
    private Boolean isExtendable;
}
