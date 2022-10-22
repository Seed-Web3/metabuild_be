# SEED Career Hub

## Swagger
http://localhost:17641/swagger-ui/index.html


## DB design
https://dbdiagram.io/d/6349cf02f0018a1c5f09b4bc


## Backend-Frontend Authentication

Frontend handles user's wallet (NEAR wallet).
Backend needs to authenticate frontend users.


### Auth endpoints
#### GET /auth/nonce?address={wallet_address}
E.g.
http://localhost:17641/auth/nonce?address=seed.testnet

Response: raw string, the nonce assigned to the {wallet_address}

Backend generates a UUID string to be used in signed message. Stores in db.


#### POST /auth/near
Request (example):
```shell
{
  "publicAddress": "seed.testnet",
  "signature": "0xdce78b657f3c9a61423ca00fd636140e1acba25790b69585d5c26f7daa300cbc2023a9c342c2348718f634549df9a606383ae2bc419df76314c7946c800dce6f1c"
 }
```

E.g.
```shell
curl -X POST 'http://localhost:17641/auth/eth' \
 -H 'Content-Type: application/json' \
 --data-raw '{
   "publicAddress": "seed.testnet",
   "signature": "0xdce78b657f3c9a61423ca00fd636140e1acba25790b69585d5c26f7daa300cbc2023a9c342c2348718f634549df9a606383ae2bc419df76314c7946c800dce6f1c"
 }'
```

Response: JSON with jwt field (Json Web Token) 
```shell
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIweDZDNTQyMTg5YzREYmM0ZDVEN2I0ODQ1Mzg4YjhENTYxZjllMmU5NkIiLCJleHAiOjE2NjI0MTk2MzMsImlhdCI6MTY2MjQxNjAzM30.qgqSMfTr5SgGhpKKryMb98tNxHFENvwyGd8wbTPibwY"
}
```

Frontend needs to construct the message (see bellow) and ask user to sign this message with MetaMask.

Here is an example node code:
```javascript
let msg = 'Welcome to the SEED Career Hub!\n\nPlease sign this message for authentication on Career Hub.\nYour special nonce: ' + nonce
s = ....sign(msg, privKey);
```

Backend does the signed message validation, and generates for the frontend  a JWT with 1 hour expiry time

