Feature: Remove a Customer from a BankAccount

  Scenario: Remove a Customer from a BankAccount with 1 Customer
    Given a BankAccount with 1 Customers
    When a Customer is removed
    Then get error message
    And the BankAccount has 1 Customers

  Scenario: Remove a Customer from a BankAccount with 2 Customers
    Given a BankAccount with 2 Customers
    When a Customer is removed
    Then the BankAccount has 1 Customers

  Scenario: Remove a Customer from a BankAccount with 3 Customers
    Given a BankAccount with 3 Customers
    When a Customer is removed
    Then the BankAccount has 2 Customers