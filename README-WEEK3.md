## Where and why you have used a @OneToMany annotation
I have used one to many in both my Car and Member classes to have a list of Reservations in each class.



## Where an why you have used a @ManyToOne annotation
I have used the @ManyToOne annotation in my Reservation class to have one car and one member for each reservation object


## The purpose of the CascadeType, FetchType and mappedBy attributes you can use with one-to-many
The CascadeType is used when using Uni directonal @OnetoMany with a joincolumn.
With the fetchType you have lazy and eager.
Lazy is when the referenced object in the database is not loaded when the parent object is called.
And eager is the opposite.
MappedBy attribute is used to reference the field that should connect two entities.



## How/where you have (if done) added user defined queries to you repositories
I have added them to my repository interfaces.
I create sql queries with method names.


## a few words, explaining what you had to do on your Azure App Service in order to make your Spring Boot App connect to your Azure MySqlDatabase
Through Azure i had to create some enviroment variables that have the URL, Username and password of my SQL server


## a few words about where you have used inheritance in your project, and how it's reflected in your database
I initially used inheritance with my entity classes that extended from AdminDetail.
But after setting up security on my project the entity class Member now extends from userWithRoles and i can see that in my DB that i have a table that shows the role of each user.

## What are the pros & cons of using the Single Table Strategy for inheritance?
It has the best data perfomance and the polymorphic queries very efficient.
But you are unable to use not null constraints.

## how are passwords stored in the database with the changes suggested in part-6 of the exercise
They are encrypted using BCrypt and hashed using hashing.

