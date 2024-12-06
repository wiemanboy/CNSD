# DYNAMODB TABLE DESIGN

## NoSQL Workbench

[NoSQL Workbench file](Assignment-2-4-3.json) om te importeren in de NoSQL Workbench application.
Het bevat:
- Tabel en attributen specificatie
- Global Secondary Index specificaties
- Facet definitie van de gebruikte items
- Test data voor alle items inclusief onderlinge relaties

Model en data is te deployen in AWS. Daarna zijn de beschreven access patterns uit te voeren.

## Global Secondary Indexes

### GSI1

In `GSI1` zijn de partition key `PK` en sort key `SK` omgedraaid om standaard te voorzien in een reverse lookup van de relatie tussen entities.

### GSI2

`GSI2` voorziet in een additionele lookup op entity typen wanneer dit nodig is.
In dat geval kunnen de `GSI2PK` en `GSI2SK` attributen met de gewenste waarden gevuld worden.

### GSI3

`GSI3` voorziet in een additionele lookup op entity typen wanneer dit nodig is.
In dat geval kunnen de `GSI3PK` en `GSI3SK` attributen met de gewenste waarden gevuld worden.

## Access Patterns

### User by email

Pseudocode:
`Query(LunchTable.GSI1, SK = "USER#awassink@quintor.nl", PK begins_with("USER"))`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI1 WHERE SK = 'USER#awassink@quintor.nl' AND begins_with(PK, 'USER')
```

### User by id

Pseudocode:
`Query(LunchTable, PK = "USER#1234", SK begins_with("USER"))`

PartiQL: 
```sql
SELECT * FROM LunchTable WHERE PK = 'USER#1234' AND begins_with(SK, 'USER')
```

### Lunch by id

Pseudocode:
`Query(LunchTable, PK = "LUNCH#123456", SK begins_with("LUNCH"))`

PartiQL:
```sql
SELECT * FROM LunchTable WHERE PK = 'LUNCH#123456' AND begins_with(SK, 'LUNCH')
```

### Lunch by date

Pseudocode:
`Query(LunchTable.GSI1, SK = "LUNCH#2022-11-11", PK begins_with("LUNCH"))`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI1 WHERE SK = 'LUNCH#2022-11-11' AND begins_with(PK, 'LUNCH')
```

### Transactions for user by email

Pseudocode:
`Query(LunchTable.GSI1, SK = "USER#awassink@quintor.nl", PK begins_with("USER"))`
`Query(LunchTable, PK = "USER#1234", SK begins_with("TRANSACTION"))`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI1 WHERE SK = 'USER#awassink@quintor.nl' AND begins_with(PK, 'USER')
SELECT * FROM LunchTable WHERE PK = 'USER#1234' AND begins_with(SK, 'TRANSACTION')
```

### Grocery by id

Pseudocode:
`Query(LunchTable.GSI1, SK = "TRANSACTION#GROCERY#554433")`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI1 WHERE SK = 'TRANSACTION#GROCERY#554433'
```

### Comments for grocery by id

Pseudocode:
`Query(LunchTable, PK = "TRANSACTION#GROCERY#554433", SK begins_with("COMMENT"))`

PartiQL:
```sql
SELECT * FROM LunchTable WHERE PK = 'TRANSACTION#GROCERY#554433' AND begins_with(SK, 'COMMENT')
```

### Comments under comment by id

Pseudocode:
`Query(LunchTable, PK = "COMMENT#123456", SK begins_with("COMMENT"))`

PartiQL:
```sql
SELECT * FROM LunchTable WHERE PK = 'COMMENT#123456' AND begins_with(SK, 'COMMENT')
```

### LunchEntries for lunch by id

Pseudocode:
`Query(LunchTable.GSI3, GSI3PK = "LUNCH#234567", GSI3SK begins_with("TRANSACTION#LUNCHENTRY"))`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI3 WHERE GSI3PK = 'LUNCH#234567' AND begins_with(GSI3SK, 'TRANSACTION#LUNCHENTRY')
```

### Transactions with state Approved for user with id

Pseudocode:
`Query(LunchTable.GSI2, GSI2PK = "USER#1234", GSI2SK begins_with("APPROVED"))`

PartiQL:
```sql
SELECT * FROM LunchTable.GSI2 WHERE GSI2PK = 'USER#1234' AND begins_with(GSI2SK, 'APPROVED')
```
