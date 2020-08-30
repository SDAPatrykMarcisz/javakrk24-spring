insert into authors (id, first_name, last_Name, description, birth_year)
    values(1001, 'Jan', 'Kochanowski', 'wielkim poeta byl', 1550);

insert into books(id, title, pages, publish_year, isbn)
    values (2001, 'Treny', 150, 1580, '111-222-333');

insert into books_author(book_id, author_id) values(2001, 1001);