# Setup: Intellij Idea

Install python if you dont already have in on your computer.

Find the python executable file with the terminal:

- Open a new terminal window (dont use the Intellij terminal) and run the command from your starting directory

      find . -name python.exe 2>/dev/null

  The executable file should be in a path that contains: Programs/Python/

  This will be followed by a folder with a name that descrivbes the version of python

  Ex: Python313

      Python313 = version 3.13

  Your partial path in the terminal to the exexutable should look like:

      ./AppData/Local/Programs/Python/Python313/python.exe

  Use pwd to get your current working directory:

        pwd
        /your/path

  combine the paths to get the full path:

  /your/path/toFiles/toFiles/AppData/Local/Programs/Python/Python313/python.exe


Create a virtual envrionment (venv) in the java envionment:

- Go back to the Java IDE, and open a new terminal shell.

  Use the following command to create a venv for python scripts with the path we just found:

      /your/path/AppData/Local/Programs/Python/Python313/python.exe -m venv venv
      

