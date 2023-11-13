# Lim Choong Kai Joshua - Project Portfolio Page

## Overview
Welcome to 🪙NUScents🪙, the tailor-made financial tracker for SOC students at NUS. It is optimized for use via a
Command Line Interface (CLI) to offer a clutter-free solution for our users to manage and monitor their financial
activities.   
It is written in Java and has about 3kLoC.

## Summary of Contributions

### Code Contributed
View my code contributions here: [Reposense](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&chartGroupIndex=10&chartIndex=2)

### Enhancements implemented
- Feature: Added the ability to add expenses
  - Creation of Transaction, Expense classes
  - Created methods in Parser to validate user input for adding expenses
  - Created methods in Storage to load expenses from data file
  - Created TransactionList to store and manipulate transactions
- Feature: Added the ability to add allowances
    - Creation of Transaction, Allowance classes
    - Created methods in Parser to validate user input for adding allowances
    - Created methods in Storage to load allowances from data file
    - Created TransactionList to store and manipulate transactions
- Feature: Added the ability to categorise allowances
  - Created AllowanceCategory 
  - Modified Parser to parse valid allowance categories
  - Modified Ui to print allowance category in relevant outputs
- Bugfix: Log to file instead of console: [#44](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/44)
- Bugfix: Allow modification of storage file and handle improper file input. 
Github issues:
[#63](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/63),[#92](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/92),
[#99](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/99),[#104](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/104)
- Explored using HMAC to verify integrity of storage file [#64](https://github.com/AY2324S1-CS2113-T18-4/tp/pull/64)
  - Eventually removed to align with tP constraints of human-editable file

### Contributions to the UG
- Created the format for commands in the Features section
- Ideated the example outputs given
- FAQ

### Contributions to the DG
- Acknowledgements
- Interaction Flow simplified class diagram
- UI Component
- Command Component
- Parser Component
- Storage Component
- Implementation: `list` Transactions Feature

### Contributions to team-based tasks
- Set up the team repository
- Heavily refactored code from original [Spaceman Task Manager](https://github.com/spaceman03/ip/)
in the initial phase of the project (when developing the basic adding allowance/expense feature)
- Reviewed non-trivial PRs [#24](https://github.com/AY2324S1-CS2113-T18-4/tp/pull/24), [#74](https://github.com/AY2324S1-CS2113-T18-4/tp/pull/74) 
- Assisted team members with help if needed (e.g. edit command feature syntax and implementation)
- Developed JUnit tests for the methods used in Parser when adding transactions

### Contributions beyond the project team
Reported 7 bugs in the PE-D on [GitHub](https://github.com/lckjosh/ped/issues)
