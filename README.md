## The idea with, and reasons for why to use, an ORM-mapper:

With an ORM-mapper it's easier to work with databases and different types of databases.   
it reduces boilerplate code and therefore increasing productivity. It's a way to have the benefits of   
Objective oriented programming, with relational databases.


## The meaning of the terms JPA, Hibernate and Spring Data JPA and how they are connected:


JPA is a specification, where Hibernate is an implementation of this specification.  
And Spring Data JPA builds upon Hibernate with reducing boilerplate code and making it smarter



## How to create simple Java entities and map them to a database via the Spring Data API:

Give any given Class the following annotations: *@Entity @NoArgsConstructor*.
Also give a field the *@Id* annotation
Afterward you can create an Interface that implements *JpaRepository* 
and define which Class it should work with.
Then you can use the interface to save or delete from a given database.


## **How to control the mapping between individual fields in an Entity class and their matching columns in the database:**

You can use the *@Column* annotation where you can define a name, length etc.
In my code I used it with the Car Class where I limit the length of fields and make it so that they are not nullable

## **How to auto generate IDs, and how to ensure we are using a specific database's preferred way of doing it (Auto Increment in our case for MySQL):**

In my Class Car, the field *id*, I use the annotation *@GeneratedValue(strategy = GenerationType.IDENTITY)* to use MySQL to autoincrement the id automatically

## **How to use and define repositories and relevant query methods using Spring Data JPAs repository pattern:**

## How to write simple "integration" tests, using H2 as a mock-database instead of MySQL:

## How to add (dev) connection details for you local MySQL database