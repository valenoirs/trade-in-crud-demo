Create backend services for tukar tambah mobil API:

the v1 API consists of:
- create new trade in information
- update trade in information
- get trade in transaction
- delete trade in (optional)

# the v1 API must be documented using springdoc
the springdoc documentation url: `http://localhost:port/swagger-ui.html`
## new trade in
method: post

customer name must not null
customer phone must not null

response code 201

sample request:
```
{
	"customer_name": "budiyanto",
	"customer_phone": "0123123123",
	"customer_email": "budi@email.com",
	"brand": "toyota",
	"model": "avanza",
	"type": "all new all",
	"year": "2020",
	"mileage": 0,
	"fuel": "bensin",
	"color": "",
	"inspection_location": "Jl dukuh bawah no. 3"
}
```

sample response (optional):
```
{
	"status": 201,
	"message": "created",
}
```
## update trade in
method: put

id must not null
inspector name must not null
inspector phone must not null
inspector email must not null
inspection location must not null
brand, model, type, year, mileage, fuel, color must not null

response code 200

sample request:
```
{
	"id": 1,
	"inspector_name": "alianto",
	"inspector_phone": "0321321321",
	"inspector_email": "inspector@email.com",
	"brand": "toyota",
	"model": "avanza",
	"type": "all new all",
	"year": "2020",
	"mileage": 0,
	"fuel": "bensin",
	"color": "",
	"inspection_location": "Jl dukuh bawah no. 3"
}
```
sample response:
## get trade in
method: get

if {id} is not provided fetch all trade in info, if trade in id exist fetch by id by adding param /{id}

response code 200

sample response all trade in:
```
[
	{
		"id": 1,
		"customer_name": "budiyanto",
		"customer_phone": "0123123123",
		"customer_email": "budi@email.com",
		"inspector_name": "alianto",
		"inspector_phone": "0321321321",
		"inspector_email": "inspector@email.com",
		"brand": "toyota",
		"model": "avanza",
		"type": "all new all",
		"year": "2020",
		"mileage": 0,
		"fuel": "bensin",
		"color": "",
		"inspection_location": "Jl dukuh bawah no. 3"
	},
	{
		"id": 2,
		"customer_name": "alisadikin",
		"customer_phone": "0123123128",
		"customer_email": "ali@email.com",
		"inspector_name": "alianto",
		"inspector_phone": "0321321321",
		"inspector_email": "inspector@email.com",
		"brand": "toyota",
		"model": "avanza",
		"type": "all new all",
		"year": "2020",
		"mileage": 0,
		"fuel": "bensin",
		"color": "",
		"inspection_location": "Jl dukuh bawah no. 3"
	},
	...
]
```
sample response single trade in:
```
{
	"id": 1,
	"customer_name": "budiyanto",
	"customer_phone": "0123123123",
	"customer_email": "budi@email.com",
	"inspector_name": "alianto",
	"inspector_phone": "0321321321",
	"inspector_email": "inspector@email.com",
	"brand": "toyota",
	"model": "avanza",
	"type": "all new all",
	"year": "2020",
	"mileage": 0,
	"fuel": "bensin",
	"color": "",
	"inspection_location": "Jl dukuh bawah no. 3"
},
```
## delete trade in
method: delete

when delete trade in flag the trade in information to deleted
when get the trade in info ignore the deleted trade in

response code 200