
# (A)partment (T)racking (S)ystem

This project aims to improve quality of tracking most of economic income and expenses of an apartment. It has varius panels to track apartment's incomes and expenses. One of the panels track down recent processes; likewise, another one shows incomes and expenses in a detailed table.
# In Detail

The project should be initiated through the file "**src/Main.java.**"

**SQLite3** has been utilized, and the project makes use of the **jdbc library**.

The user interface has been prepared using **Java Swing**.

Most of the operations attempted under the administrator account are logged.

### _The project usage is divided into two account types:_

#### Administrator Account:

* _(Example login credentials: TCKN: adminTC Password: admin)_

>**1.1) LOG**: This section presents the log to the administrator.
**1.2) GENERA**L: This section displays the Income Table (Left), Expense Table (Right), and calculates the total income, expense, and net (Bottom).
>**1.3) ADMIN**: This section allows manual data entry by the administrator.
    1.3.1) I: Addition/subtraction of Income.

        1.3.1.1) Description and Amount are entered for addition.

        1.3.1.2) Description, Amount, and Date are required for subtraction.

    1.3.2) E: Addition/subtraction of Expense.

        1.3.2.1) Description and Amount are entered for addition.
        
        1.3.2.2) Description, Amount, and Date are required for subtraction.
>**1.3.3) Resident**: Addition/removal of Residents.
    1.3.3.1) TCKN, First Name, Last Name, Flat, Ownership, Password, Super User are required for addition.

    1.3.3.2) Only TCKN is required for removal.
    
#### Resident Account:
* _(Example login credentials: TCKN: 89472295479 Password: kt)_
**Resident**: This section provides a panel for dues input.

_The logging function keeps a record of processes' success or failure history in a .txt file named '**log.txt**'. Rest of the information, such as account details, is stored in the **SQLite3 database**; likewise, the contexts of income and expense tables._

## Example Screenshots
GENERAL named panel:
![App Screenshot](https://lh3.googleusercontent.com/drive-viewer/AKGpihZ1VRN8bz0X8D3IsFdOiSn47MP5IfHYhU-QT4xeFzKuPaZGcl-E294IpeZiBuU76-Np_be6sBLN5r0PcSYwyK-15WY3uA=s1600-v0)

ADMIN named panel:
![App Screenshot](https://lh3.googleusercontent.com/drive-viewer/AKGpihb2cp1IWV-UAxFcYwcb-Tk5mtk2RDR7mxzNBGwLcv2tQkt0yk2W_1uQMqLqKc7bDCDEQzLuQdJ9Af891_X--XQIG-ByrA=s1600-v0)

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

