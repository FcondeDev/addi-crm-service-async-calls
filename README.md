# addi-crm-service
Challenge solution:

To solve the change I created two services, SIMULATION-SERVICE and ADDI-CRM-SERVICE, services are using DTO's to carry over the data beetween them, i am following
the same package structure for both services and using interfaces between controller and services to create a factory pattern.

SIMULATION SERVICE :
It is in charge of simulating the 3 services associated with national registry identification external system, the national archives external system and 
internal prospect qualification system. the service has 3 endpoints and each endpoint represents a service above mentioned.

The tree endpoints has a delay that was set by using Thread.sleep(long); to simulate latency in each request,
simulate-service also has a random response for each endpoint based in a random number, for two first endpoints the response is a boolean and a message
in case of error, for third endpoint the response is a boolean based on if a random number is higher than 60.


ADDI-CRM-SERVICE:
It is in charge of calling simulation service and doing the validation to check if client is able to be approved in our crm,
i am using a model called client and a dummy repository to simulate the interation with our database information.

The exception are beign handled by using @restcontrolleradvice among others(customexceptions, a dto and a enum).

To send the request the service is using @feignClient

the service perform the two first requests(national registry identification external system and the national archives external system) in a asynchronous way by
using @async annotation and CompletableFuture for the return clause, you can check it by seeing the logs as i am logging the time for each of these request,
the third request has the result from previos two requests.

The crm process can be triggered by sending a GET request to localhost:9001/crms/1, the result will be based in the 3 requests to the 3 external services,
the response is a boolean and a message.

Tests are made by using jnuit 5 and mockito.


