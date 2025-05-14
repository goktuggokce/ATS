
# (A)partment (T)racking (S)ystem

This project aims to improve quality of tracking most of economic income and expenses of an apartment. It has varius panels to track apartment's incomes and expenses. One of the panels track down recent processes; likewise, another one shows incomes and expenses in a detailed table.
# In Detail

The project should be initiated through the file "**src/Main.java.**"

**SQLite3** has been utilized, and the project makes use of the **jdbc library**.

The user interface has been prepared using **Java Swing**.

Most of the operations attempted under the administrator account are logged.

### _The project usage is divided into two account types:_

**1.1) LOG**: This section presents the log to the administrator.

**1.2) GENERA**L: This section displays the Income Table (Left), Expense Table (Right), and calculates the total income, expense, and net (Bottom).

**1.3) ADMIN**: This section allows manual data entry by the administrator.
    
    1.3.1) I: Addition/subtraction of Income.

        1.3.1.1) Description and Amount are entered for addition.

        1.3.1.2) Description, Amount, and Date are required for subtraction.

    1.3.2) E: Addition/subtraction of Expense.

        1.3.2.1) Description and Amount are entered for addition.
        
        1.3.2.2) Description, Amount, and Date are required for subtraction.
**1.3.3) Resident**: Addition/removal of Residents.

    1.3.3.1) TCKN, First Name, Last Name, Flat, Ownership, Password, Super User are required for addition.

    1.3.3.2) Only TCKN is required for removal.
    
#### Resident Account:
* _(Example login credentials: TCKN: 89472295479 Password: kt)_

**Resident**: This section provides a panel for dues input.
  
#### Administrator Account:
* _(Example login credentials: TCKN: adminTC Password: admin)_

_The logging function keeps a record of processes' success or failure history in a .txt file named '**log.txt**'. Rest of the information, such as account details, is stored in the **SQLite3 database**; likewise, the contexts of income and expense tables._

## Example Screenshots
GENERAL named panel:


![Medya1](https://github.com/user-attachments/assets/307b4799-7d31-4285-b6ec-d5ec90ce8c15)


ADMIN named panel:


![Medya2](https://github.com/user-attachments/assets/b2069b87-8b5a-4e02-a90b-ed5ea1677218)

Others:


![Medya3](https://github.com/user-attachments/assets/5d6432cf-17cc-4e10-9b51-42becf3af7de)
![Medya4](https://github.com/user-attachments/assets/946f244c-52c8-40f4-8454-f7904fc39f97)
![Medya5](https://github.com/user-attachments/assets/022ac96b-2a00-4095-a35f-ff8566e9112a)
![Medya6](https://github.com/user-attachments/assets/9a407bb0-5f03-4dd3-8826-1adffb95a134)
![Medya7](https://github.com/user-attachments/assets/25605dd8-6682-4724-84b7-dcf932147486)
![Medya8](https://github.com/user-attachments/assets/3ddad6e2-3a00-4099-8369-ee22abb8548d)



## Lessons Learned

* jdbc, database connection
* java.util.logging
* java.util.Logger
* java.util.Level
* StringBuilder
* Java Swing
* java.io.File
* Hash Map
* ArrayList



## Author

- M. Göktuğ Gökçe | [github/goktuggokce](https://www.github.com/goktuggokce) | [linkedin/goktuggokce](https://www.linkedin.com/in/goktuggokce)



## License

[MIT](https://choosealicense.com/licenses/mit/)

