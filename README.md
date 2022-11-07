The main changes I made were to properly implement the main viewmodel which was using a companion object earlier.
Used DataStore to save the current balance and ROOM to save the transactions
Ensured that every commit compiles and there are no breaking changes 
Moved all the hardcoded stuff to the correct files


Things I would have liked to do :
Add DI
Add testcases for testing the business logic of saving and fetching transactions
Move the datastore related code written inside main view model to a repository class
