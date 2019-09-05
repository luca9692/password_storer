# Password Storer 
***Password Storer*** is an open source tool which allows to store your password into a small XML file. 

All your passwords will be encrypted safely with a **MASTER** password at your choice thanks to Jasypt encryption module. 

:warning: **Be careful not to lose the MASTER password :)**

## How to use it
In this moment it works only from command line. Download the jar at this [link](out/jasypt_encoding.jar) and run -h to get instructions.

### Basic esample
If you want to save the password for your account this is the basic command. 
```
java -jar jasypt_encryptor.jar "MASTER_PASSWORD" -e "ACCOUNT_NAME" "USERNAME" "PASSWORD"
```

## How to contribute to the project
You can mail me @ work@lucamortillaro.it if you want to help me with this tiny project.