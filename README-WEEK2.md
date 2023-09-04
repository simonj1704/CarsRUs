


## **What are the benefits of using a RESTful API**
That its using JSON which is basically just a String and therefore anything can use it and it does not matter what language or system is being used.



## **What is JSON, and why does JSON fit so well with REST?**
JSON is a response you get from a RESTful API. it entirely text that is setup to show objects.



## How you have designed simple CRUD endpoints using spring boot and DTOs to separate api from data -> Focus on your use of DTO's
We are using hibernate and Spring JPA to have a repo and some endpoints that can be accesed.
We are using DTO's to map objects from JSON to Java classes.


## What is the advantage of using using DTOs to separate api from data structure when designing rest endpoints
When using DTO's u can make sure that what u are sending out and receiving is just plain JSON and you dont have to manipulate the JSON to a specifik template.



## Explain shortly the concept mocking in relation to software testing
Mocking means that you imitate the class or interface you are using. Instead of having the class do something, you are telling the program what it should do, and therefore it makes testing easier since you dont have to worry of other classes and what they are doing. u can focus on the one class u are testing.


## How did you mock database access in your tests, using an in-memory database and/or mockito → Refer to your code
I have made both. with mocking i am using Mockito and with In-memory i am using H2.


## Explain the concept Build Server and the role Github Actions play here
A build server is a automated server that build your application every time you push to the main branch and tries to build the project. it will give a message if it fails.
this is for example controlled with Github Actions where you can control when and how the project is being build.


## Explain maven, relevant parts in maven, and how maven is used in our CI setup. Explain where maven is used by your GitHub Actions Script(s)

Maven is a framework used to read your POM file and it dictates what should be imported and whatnot. there is lifecycles and plugins in maven that do different stuff. but Github actions uses maven to differantiate, where it first uses the lifecycle down until verify or package. and then it uses the deploy lifecycle to test if it works.



## Understand and chose cloud service models (IaaS, PaaS, SaaS, DBaaS)for your projects -> Just explain what you have used for this handin
I have used Azure, where i have made a SQL database which is a DBaaS.
I have also deployed my application in Azure on a server which is a Paas.

