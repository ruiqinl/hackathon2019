package com.financetech.capitalone.hackathon2019;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.NONE)

@Entity
@Table(name = "datasource")
public class BDDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "datasourcename")
    String name;

    @Column(name = "countrycode")
    String countryCode;

    @Column(name = "\"year\"")
    Long year;

    @Column(name = "\"month\"")
    Long month;

    @Column(name = "teamid")
    Long teamId;

    @Column(name = "applicationid")
    Long applicationId;

    @Column(name = "businessday_start")
    Long businessDayStart;
    @Column(name = "businessday_end")
    Long businessDayEnd;

    @Column(name = "isweekendincluded")
    boolean isWeekendIncluded;

    @Column(name = "isholidayincluded")
    boolean isHolidayIncluded;

    @Column(name = "isactive")
    boolean isActive;

    @Column(name = "datecreated")
    Date dateCreated;

    @Column(name = "dateupdated")
    Date dateUpdated;

    @Column(name = "createdby")
    String createdBy;

    @Column(name = "updatedby")
    String updatedBy;


}
