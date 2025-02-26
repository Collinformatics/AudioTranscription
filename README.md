# Setup: Intellij Idea

Install python if you dont already have in on your computer:

      https://www.python.org/downloads/

After installation, find the python executable file with the terminal:

- Open your computers terminal app (dont use the Intellij terminal) and run the command:

      find . -name python.exe 2>/dev/null

  The executable file should be in a path that contains: Programs/Python/

  This will be followed by a folder with a name that describes the version of python

  Ex: Python313

      Python313 = version 3.13

  Your partial path in the terminal to the exexutable should look like:

      ./AppData/Local/Programs/Python/Python313/python.exe

  Use pwd to get your current working directory:

        pwd
        /your/path

  - combine the paths to get the full path:

    /your/path/toFiles/toFiles/AppData/Local/Programs/Python/Python313/python.exe


Create a virtual envrionment (venv) in the java envionment:

- Go back to the Java IDE, and open a new terminal shell.

  Use the following command to create a venv for python scripts with the path we just found:

      /your/path/AppData/Local/Programs/Python/Python313/python.exe -m venv venv

Set up the python interpreter for the project:

- Find the .exe file in you venv directory:

        find . -name python.exe 2>/dev/null
        ./Scripts/python.exe

  Press keys "Alt" & "1" to open the Project folder.

  Follow the folder path to find "python.exe"

  Right click on the executable file and copy the path.

- Add the interpreter path:

  Go to the drop-down menu option, and select "File" -> "Project Structure"

  After clicking on "Project Structure", click on "Modules" then the "+" icon.

  Paste the path to the python interpreter under "Python SDK home path"
