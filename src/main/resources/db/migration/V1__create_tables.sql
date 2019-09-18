create table DataSource(
    ID bigserial primary key,
    DataSourceName text,
    CountryCode text not null,
    TeamID bigint,
    ApplicationID bigint,

    "year" bigint not null,
    "month" bigint,
    BusinessDay_Start bigint not null,
    BusinessDay_End bigint not null,
    IsWeekendIncluded bit(1) not null default B'1',
    IsHolidayIncluded bit(1) not null default B'0',

    IsActive bit(1) not null default B'1',

    DateCreated date not null default current_date,
    CreatedBy text not null,
    DateUpdated date,
    UpdatedBy text
);