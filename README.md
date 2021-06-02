# conference-backend
Backend for it conference written with Spring Boot  
Java version: 11  
Created with: Maven  
IDE used: VScode  
Used database: In-memory H2  

Example requests:
- show all registreted users in the system
  - GET localhost:8080/api/user/
- add new user
  - POST localhost:8080/api/user/
  - body as raw JSON
    {
      "login" : "exampleLogin",
      "email" : "example@email"
    }
- update user email
  - PUT localhost:8080/api/user/{userId}
  - body as raw JSON
    {
      "email" : "example@email"
    }
- get all lectures in conference
  - GET localhost:8080/api/lecture
- register user for lecture
  - POST localhost:8080/api/user/lecture/{lectureId}
  - body as raw JSON
    {
      "id" : userId
    }
- show lectures that the user has registered for 
  - GET localhost:8080/api/user/lecture/{userId}
- delete user's registration for lecture
  - DELETE localhost:8080/api/user/lecture/{lectureId}
  - body as raw JSON
    {
      "id" : userId
    }
 
