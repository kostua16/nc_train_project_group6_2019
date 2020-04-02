CREATE TABLE IF NOT EXISTS test_account
(
    login       VARCHAR PRIMARY KEY ,
    password    VARCHAR(254)  ,
    name        VARCHAR(254) ,
    balance     integer (254)
);
