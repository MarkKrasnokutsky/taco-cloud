create table if not exists Taco_Order (
                            id BIGINT,
                            delivery_name varchar(50) not null,
                            delivery_street varchar(50) not null,
                            delivery_city varchar(50) not null,
                            delivery_state varchar(2) not null,
                            delivery_zip varchar(10) not null,
                            cc_number varchar(16) not null,
                            cc_expiration varchar(5) not null,
                            cc_cvv varchar(3) not null,
                            taco_ids integer[]
);
create table if not exists Taco (
                                    id BIGINT,
                                    name varchar(50) not null,
                                    ingredients_ids integer[]
);
create table if not exists Ingredient_Ref (
                                              ingredient varchar(4) not null,
                                              taco bigint not null,
                                              taco_key bigint not null
);
create table if not exists Ingredient (
                                          id bigint not null,
                                          slug varchar(4) not null,
                                          name varchar(25) not null,
                                          type varchar(10) not null
);
alter table Taco
    add foreign key (taco_order) references Taco_Order(id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);