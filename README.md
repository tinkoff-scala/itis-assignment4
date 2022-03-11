# Movies Database sorting
We are building a movies database service. Our technical department told us that one of the main features of our service should be flexible movies sorting.
To start with, we have a `Movie` data type which has a `name`, `releaseDate` and `rating`. 
Our business department want users to have an ability to specify a sorting order for each of the fields.
## Part 1
Implement an Ordering constructor based on the provided Order type for each of the fields
## Part 2
Business department have told us that users are excited by our sorting algorithm. 
But we receive a lot of feedback that there is not enough information for each movie. 
That's why our business department came to us and told that we should extend our `Movie` with `actors`, `director` and `dvdReleaseDate`. Each person has `name`, `birthDate` and a list of `rewards`. `Reward` itself has `name` and `count` fields.
Also, they said that the model could be extended with more fields in the future. 
Since we are smart and lazy software engineers we want to implement a posibility of model extension in such a way that it will require minimum actions in the future.
Come up with a solution how we can encode our model fields so we can easily add new ones to our model in the future and specify sort orders for them
