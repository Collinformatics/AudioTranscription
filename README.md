# Setup: Intellij Idea Environment

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

  - combine the two paths to get the full path to your interpreter:

    /your/path/AppData/Local/Programs/Python/Python313/python.exe


- Add the interpreter path to your IDE:

  Go to the drop-down menu option, and select "File" -> "Project Structure"

  After clicking on "Project Structure", click on "Modules" then the "+" icon.

  Paste the path to the python interpreter under "Python SDK home path"


Install python modules:

  Activate the venv:

        source venv/bin/activate

  Install modules with pip:

        pip install pandas

