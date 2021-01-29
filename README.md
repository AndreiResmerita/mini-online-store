## Mini online store application

- __IMPORTANT__: **This application runs only with a SQLite database file.**
- Please make a db file and put the url into the method located in DBManager class.
- SQL tables are created automatically with some data for test when the application runs.
- This project is an example of a basic online store application working with SQL Database with 2 types of users (admin
  and customer) all the accounts having hashed passwords for security. Customer can buy products (add/remove from cart),
  send orders, see orders history and update his details. It has validation methods for login as user and as admin.
  Admin can add products, edit, delete. Another things to tell:
    - Customer cannot buy products out of stock (also message on product appears that is out of stock)
    - Customer cannot select a quantity that is bigger than the actual quantity
    - Admin can insert photos
    - Cart is saved in DB only after the order in sent
    - User is saved on session
    - On the main page is shown bestseller product based on the product bought by most of users,and a random one.
    - Convert data to JSON and add JSON data to javascript table

**User details for loggin as admin or customer are shown in the main page. Please run it to see all his features.**
  
