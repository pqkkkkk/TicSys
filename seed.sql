insert into Role (name) values ('USER');
insert into Role (name) values ('ADMIN');
insert into Role (name) values ('ORGANIZER');
insert into Category values ('Sport');
insert into Category values ('Music');
insert into Category values ('Other');
insert into Category values ('Comedy');
insert into PromotionType(name) values ('Flash'), ('Combo'), ('Voucher');

insert into Users (userName, email, fullName, passWord, phoneNumber, birthday,gender) values ('admin', 'admin@gmail.com', 'Nguyen Van An', 'admin', '0123456789', '2000-01-01','Male')

select * from Category;
select * from users;
select * from role;
select * from RoleOfUser;
select * from event;
select * from Ticket;
select * from TicketOfOrder;
select * from [Order];
select * from Comment;
select * from PromotionType;
select * from Promotion;
select * from VoucherOfUser;

alter table event alter column description nvarchar(max);

alter table ticket drop constraint [FK__Ticket__type__571DF1D5];
drop table TicketType;
alter table ticket drop column type;
alter table ticket add minQtyInOrder int;
alter table ticket add maxQtyInOrder int;



alter table [order] drop column quantity;


alter table comment add dateCreatedAt date default '2025-02-18';
alter table comment add timeCreatedAt time default '16:00:00';

 INSERT INTO [comment] (content, senderId, eventId, parentId,dateCreatedAt, timeCreatedAt)
values ('Is this event interesting?', 'pqkiet854', 18, null, GETDATE(), GETDATE());


update PromotionType set name = 'Combo Sale' where name = 'Combo';
update PromotionType set name = 'Flash Sale' where name = 'Flash';
update PromotionType set name = 'Voucher Gift' where name = 'Voucher';

alter table voucherOfUser add status nvarchar(20);

update Promotion set status = 'active', endDate = '2025-03-04' where id = 2;