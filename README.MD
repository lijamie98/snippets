# Introduction
Spring boot is used to shorten the development time. The RestController
is also used for fast development.

**********************
This is added after the deadline:
I forgot to mention the server runs at port-8080
*********************

# Language
Kotlin is used because it is less verbose for development while maintains the 
performance of Java. 
# Architecture
The server is splitted into 3 layers:
## Controller / API layer. 
This takes care of the HttpRequest as well as the data transfer issues between the model and the HttpResponse.
## Service layer. 
This is the layer that I usually implement the business logic.
## The domain/model/repository layer. 
I am not persisting the domain objects. But the domain object classes are clearly defined in the "models" layer.

# Other Concerns

## Exception Handling
Only "NotFoundException" and "AlreadyExistsException" are defined and caught to return proper HttpStatus code. 

Other exceptions should be better defined for better exception handling.

## Security
I did not put too much thoughts into security concerns such as inject attack, DDOS, etc.

## HTTPS and URL issues
I did not implement HTTPS because I think it should be fronted by Nginx or Apache.
Port #8080 was used instead of #80. 

This is the main difference between the required used case and this implementation.

