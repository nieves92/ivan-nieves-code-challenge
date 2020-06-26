drop table TRANSACTIONS_TABLE if exists;

create table TRANSACTIONS_TABLE (ID bigint identity primary key, TRANSACTION_ID varchar(50) not null,
                        DESCRIPTION varchar(50), TRANSACTION_DATE varchar(10) not null, AMOUNT double not null, USER_ID bigint not null);