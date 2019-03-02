# osf-task
Create a REST based application that will give access to a business contact information stored in a database. The contact info consists of people's names (first name, last name), address (street, city), their phone number (type: home, mobile) and email. 

The endpoint will need to provide authenticated access for CRUD  operations (only for authenticated users) and anonymous access (public) for search operation.

Authentication endpoint (public) will expect a request with an user and password and will return a token with a valability of 30 minutes. The token will be set in the request header of future requests to the endpoints with restricted access.  

An Authentication Token refresh endpoint can extend the valability of the token if an extend request is received in the 30 min interval.

The search query can contain any of the parameters: firstname, lastname, city. The result list will be ordered by lastname (default) or by the value passed in the orderBy query param if it’s present. The result list should support pagination:  index=0 (default), size=10(default)|200(max number of results).

Validation of input data would be nice to have. Do not allow null values for fields. Phone numbers must be 10 digits long, email must be a valid address (https://en.wikipedia.org/wiki/Email_address).

Notes: you can use in memory databases, or generated on application launch.
Ex of requests:

POST /contacts/login 
{„username”:”adminUser”„password”:”xxx”}

Results: {„message”: „success”, „token”:”xxx”, „expiresAt”:”2018-01-01 00:00:00”} 
or  {„message”: „unauthorized” }

Refresh the authentication token 
POST /contacts/login/refresh     
request must have Authentication-Token present  
Authentication-Token: xxx 

Results: {„message”: „success”, „token”:”xxx”, „expiresAt”:”2018-01-01 00:30:00 ”}  => 

Add a new record into the phonebook. Requires authentication.
POST /contact/add
Body
{„firstname”:”John”, „lastname”:”Doe”, „address”:{„street”:”Fourth St”, „city”:”NY”}, „phone”:{„type”:”mobile”, „number”:„ +1 (888) 555-4654”}} 
Results: {„message”: „success”, „uid”:”xxx”} or  {„message”: „unauthorized” }

Updating a contact
POST /contact/{ uid }
{„firstname”:”John2”, „lastname”:”Doe”, „address”:{„street”:”Fourth St”, „city”:”NY”}, „phone”:{„type”:”mobile”, „number”:„ +1 (888) 555-4654”}} 
Results: {„message”: „success”, „uid”:”xxx”} or  {„message”: „unauthorized” }

Delete Contacts
DELETE /contact/{ uid }
Results: {„message”: „success”} or  {„message”: „unauthorized” }

Get a single record
GET /contact/{uid}

Get a list of records
GET / contact?size=10&offset=0 (pagination fields are optional, defaults must be assumed for size = 10, offset = 0)

Query API
GET /contact/search?size=10&offset=0&query={firstname=xx&lastname=yyy&city=NY}
 (pagination fields are optional, defaults must be assumed for size = 10, offset = 0)
