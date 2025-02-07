insert into Role (name) values ('USER');
insert into Role (name) values ('ADMIN');
insert into Role (name) values ('ORGANIZER');
insert into Category values ('Sport');
insert into Category values ('Music');
insert into Category values ('Other');
insert into Category values ('Comedy');

insert into TicketType values ('A'),
('B'), ('C'), ('D'), ('E');
select * from Category;
select * from users;
select * from role;
select * from RoleOfUser;
select * from event;

alter table event alter column description nvarchar(max);