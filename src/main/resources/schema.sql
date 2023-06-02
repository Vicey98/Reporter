-- Stock Pick
create table IF NOT EXISTS Stock_Pick(
    ticker varchar(100) primary key,
    no_of_comments int not null,
    sentiment varchar(100) not null,
    sentiment_score varchar(100) not null
);
