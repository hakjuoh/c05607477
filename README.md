## The Online Bank Web Service project for c05607477
This project provides interfaces and web pages for the online bank web service project.
All interfaces have written through RESTful structure and access over HTTP(S). All data is formatted as JSON.
 
## REST APIs
## User
- `GET` /users/[*userId*]

    Parameters
    
        userId - (positive number)
        
    Response
    
        address - (string)
        email - (string)
        name - (string)
        pinCode - (positive number)
        
    Response Example
    ```
    200 OK
    {
        "address": "Dublin",
        "email": "test@gmail.com",
        "name": "Ben",
        "pinCode": 1
    }
    ```
    
    ```
    404 Not Found
    {
        "errorCode": "404",
        "errorMessage": "Requested user doesn't exist"
    }
    ```
         

- `GET` /users/[*userId*]/accounts

    Parameters
    
        userId - (positive number)

    Response
    
        userAccounts - (array)

        
        userAccount
        
            accountId - (positive number)
            accountNo - (string)
            accountType - (string)
            balance - (positive number)
            
    Response Example
    ```
    200 OK
    {
        "userAccounts": [
            {
                "accountId": 1,
                "accountNo": null,
                "accountType": "",
                "balance": null
            }
        ]
    }
    ```
    
    ```
    404 Not Found
    {
        "errorCode": "404",
        "errorMessage": "Requested user have no account"
    }
    ```

- `POST` /users

    Parameters
    
        address - (string)
        email - (string)
        name - (string)
        pinCode - (positive number)

    Response
    
        userId - (positive number)
        
    Response Example
    ```
    201 CREATED
    {
        "userId": 1
    }
    ```
    
## Account

- `POST` /accounts

    Parameters
    
        userId - (positive number)
        accountType - (Account Type)

    Response
    
        accountId - (positive number)
        
    Response Example
    ```
    201 CREATED
    {
        "accountId": 1
    }
    ```

