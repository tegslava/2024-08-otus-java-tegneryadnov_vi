create table transfers
(
    id               varchar(36) primary key,
    client_id        varchar(10),
    target_client_id varchar(10),
    source_account   varchar(12),
    target_account   varchar(12),
    amount           int,
    message          varchar(255)
);

create table accounts
(
    id             varchar(36) primary key,
    client_id      varchar(10),
    account_number varchar(12),
    balance        int,
    is_blocked     boolean
);

insert into accounts(id, client_id, account_number, balance, is_blocked)
values ('568b3975-b054-41d9-b8d2-588b326c4a0a', '1000000001', '000000000001', 200, false);
insert into accounts(id, client_id, account_number, balance, is_blocked)
values ('496a8e28-56a0-41ad-a1b1-2985766452bb', '1000000001', '000000000003', 90, true);
insert into accounts(id, client_id, account_number, balance, is_blocked)
values ('682a10f1-a540-45b2-93b1-906f81d9834c', '1000000002', '000000000002', 100, false);
insert into accounts(id, client_id, account_number, balance, is_blocked)
values ('682a10f1-a540-45b2-93b1-906f81d9834s', '1000000002', '000000000004', 70, true);
insert into accounts(id, client_id, account_number, balance, is_blocked)
values ('682a10f1-a540-45b2-93b1-906f81d9899s', '1000000003', '000000000005', 70, false);

insert into transfers (id, client_id, target_client_id, source_account, target_account, amount, message)
values ('bde76ffa-f133-4c23-9bca-03618b2a94b2', '1000000001', '1000000002', '000000000001', '000000000002', 100,
        'Тестовый перевод'),
       ('32ebb2eb-ed35-4baa-b500-b7f6535e4c88', '1000000002', '1000000001', '000000000002', '000000000001', 50,
        'Обратный тестовый перевод'),
       ('32ebb2eb-ed35-4baa-b500-b7f6445e4c88', '1000000002', '1000000003', '000000000005', '000000000001', 700,
        'Тестовый перевод третьему клиенту');