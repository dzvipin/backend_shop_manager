**Prerequisite**
1. Gradle
2. MySql

**Steps for running the project**

1. If Mysql is installed just change the credentials in application.properties and proceed to step 3.
2. Install mysql in docker using cmd in root directory - $docker-compose up --build
3. Enter cmd inside root directory `-$./gradlew clean build`. Goto /build/libs and run `-$ java -jar backend-0.0.1-SNAPSHOT.jar`
     
**Operations**

To Check Rest Api End, Use Postmen Client and hit the end point.

 1. To Create Shop
 
   just hit  =>POST localhost:8802/v1/api/shop 
   sample Request Body :
   
   `{
       "name": "Villavicencio",
       "category": "MALL",
       "ownerName": "Villavicencio",
       "locationName":"Demo"
       "latitude": 6.55526689,
       "longitude": -73.13373892
   }`
   
   sample Response json:
   
      `{
           "id": 1,
           "name": "Villavicencio",
           "category": "MALL",
           "ownerName": "Villavicencio",
           "address": {
               "id": 1,
               "locationName":"Demo"
               "latitude": 6.55526689,
               "longitude": -73.13373892
           }
       }
     `
     
 2. To Get All Shop
     
       just hit  =>GET localhost:8802/v1/api/shop?pageSize=1&pageNumber=0
       
       sample Response json:
       
          `{
               "content": [
                   {
                       "id": 1,
                       "name": "Villavicencio",
                       "category": "MALL",
                       "ownerName": "Villavicencio",
                       "address": {
                           "id": 1,
                           "locationName":"Demo"
                           "latitude": 6.55526689,
                           "longitude": -73.13373892
                       }
                   }
               ],
               "pageable": {
                   "sort": {
                       "sorted": false,
                       "unsorted": true,
                       "empty": true
                   },
                   "pageNumber": 0,
                   "pageSize": 1,
                   "offset": 0,
                   "paged": true,
                   "unpaged": false
               },
               "totalElements": 1,
               "last": true,
               "totalPages": 1,
               "first": true,
               "sort": {
                   "sorted": false,
                   "unsorted": true,
                   "empty": true
               },
               "numberOfElements": 1,
               "size": 1,
               "number": 0,
               "empty": false
           }
         `
  
 3. To Search Shop
     
       just hit  =>POST localhost:8802/v1/api/shop/search
       sample Request Body using name of shop :
       
       `{
           "name": "Villa",
       }`
       
       sample Request Body using lat and long :
       
        `{
                  "latitude":7.08594109039762,
                  "longitude":286.95225338731285
         }`
       