truncate DataSource;

insert into DataSource ( CountryCode, TeamID, ApplicationID, BusinessDay_Start, BusinessDay_End, IsWeekendIncluded, IsHolidayIncluded, IsActive, CreatedBy, DateUpdated, UpdatedBy, "year", "month" , DataSourceName )
values ('us', 1, 1, 1, 6, B'0', B'0', B'1', 'created_by_me', null, null, 2019, 9, 'name1');

insert into DataSource ( CountryCode, TeamID, ApplicationID, BusinessDay_Start, BusinessDay_End, IsWeekendIncluded, IsHolidayIncluded, IsActive, CreatedBy, DateUpdated, UpdatedBy, "year", "month" , DataSourceName )
values ('us', 2, 2, 1, 6, B'0', B'0', B'1', 'created_by_me', null, null, 2019, null, 'name2');

insert into DataSource ( CountryCode, TeamID, ApplicationID, BusinessDay_Start, BusinessDay_End, IsWeekendIncluded, IsHolidayIncluded, IsActive, CreatedBy, DateUpdated, UpdatedBy, "year", "month" , DataSourceName )
values ('us', 3, 3, 1, 100, B'0', B'0', B'1', 'created_by_me', null, null, 2019, null, 'name2');