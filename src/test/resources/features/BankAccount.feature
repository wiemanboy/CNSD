Feature: Remove a Customer from a BankAccount

  Scenario: Remove a Customer from a BankAccount with 1 Customer
    Given a BankAccount with 1 Customers
    When a Customer is removed
    Then get error message
    And the BankAccount has 1 Customers

  Scenario Outline: Removing a Customer from a BankAccount with <start> Customers
    Given a BankAccount with <start> Customers
    When a Customer is removed
    Then the BankAccount has <end> Customers

    Examples:
      | start | end |
      | 2     | 1   |
      | 3     | 2   |
      | 4     | 3   |
