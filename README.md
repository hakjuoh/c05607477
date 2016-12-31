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
                "accountNo": "72538ef0-04e4-4f90-900c-ad0de1a26c09",
                "accountType": "Checking",
                "balance": 0
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
- `GET` /accounts/[*accountId*]

    Parameters
    
        accountId - (positive number)

    Response
    
        accountNo - (string)
        accountType - (string)
        balance - (positive number)
        
    Response Example
    ```
    200 OK
    {
        "accountNo": "72538ef0-04e4-4f90-900c-ad0de1a26c09",
        "accountType": "Checking",
        "balance": 0
    }
    ```
       

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

## Transaction
- `POST` /transactions/transferTo

    Parameters
    
        senderAccountId - (positive number)
        recipientAccountId - (positive number)
        amount - (positive number)
        
    Response
    
        txNo - (string)
        txStatus - (TxStatus)
        
    Response Example
    ```
    200 OK
    {
        "txNo": "0075865a-0eda-46ed-bf79-a49d8c5b350c",
        "txStatus": "Success"
    }
    ```