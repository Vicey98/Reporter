-- Message
create table IF NOT EXISTS message(
    id int primary key,
    symbol varchar(100),
    overall_sentiment_score numeric(20,4),
    overall_sentiment_label varchar(100),
    message_date date
);

CREATE SEQUENCE IF NOT EXISTS my_entity_seq START 1;
