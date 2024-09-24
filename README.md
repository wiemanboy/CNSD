# CNSD Jarno Wieman #1805218

## Docs

This project includes [a swagger ui](http://localhost:8080/docs) to view the API documentation.

## Models

### Domain model

```mermaid
classDiagram
    BankAccount "*" <--> "1...*" Customer

    class BankAccount {
        - id: UUID
        - value: int
        - customers: List<Customer>
        - status: AccountStatus
    }

    class Customer {
        - id: UUID
        - name: String
        - bankAccounts: List<BankAccount>
    }
```

### ERD

```mermaid
erDiagram
    bank_account }o--o{ customer: "has"
    bank_account {
        UUID id
        int value
        AccountStatus status
    }
    customer {
        UUID id
        String name
    }
```

### Layer model

In the slides a three-layer architecture is given; however, I am used to working with a four-layer architecture.
I prefer the four-layer architecture as it gives more separation between your business logic and saving data
(single responsibility).
If this is a problem, please let me know.

```mermaid
classDiagram
    controller --> dto
    controller --> exceptionHandler
    controller --> service
    service --> entity
    service --> repository
    entity --> exception
    service --> exception

    namespace presentation___web {
        class controller
        class dto
        class exceptionHandler
    }

    namespace application___domain {
        class service
    }

    namespace domain {
        class entity
        class exception
    }

    namespace data {
        class repository
    }

```

## Testing

Tests can be run with reports with `mvn test compile org.pitest:pitest-maven:mutationCoverage jacoco:report`, this will
place the reports in the `target` directory.

### Results

![Jacoco Coverage](/assets/jacoco_report.png)

![PIT Mutation Coverage](/assets/pitest_report.png)

## Leerdoelen

- Met AWS werken
- Designen voor de cloud
- NoSQL (Dit is slecht gedaan bij INNO)
- Beter beeld van CD
- Hoe test je een frontend goed
