# Setup: Intellij Idea @ Pycharm Environments

To run the scripts out of IDEs with these instructions, you will need to have Intellij and Pycharm installed on your computer. 

You will need to make a project in each application. 

It is recommended that you give them the same name, such as "AudioTranscription"

- Finding the Python interpreter:
  
  In Pycharm, use the IDE's terminal to find the python executable file with the command:

      find . -name python.exe 2>/dev/null

  The executable file should be in a path that looks like:

        ./.venv/Scripts/python.exe

  Open the folder tab in the top left corner of the IDE, and follow the path the the python.exe file.

  Click on the file and copy its path.

  The path should look similar to this:

      C:\Users\AccountName\PycharmProjects\AudioTranscription\.venv\Scripts\python.exe

- Add the interpreter path to your IDE:

  Go to the drop-down menu option, and select "File" -> "Project Structure"

  After clicking on "Project Structure", click on "Modules" then the "+" icon.

  Paste the path to the Python interpreter under "Python SDK home path"

Install modules:

- Go back to the Pycharm terminal and install necessary python modules with the pip

      pip install deepmultilingualpunctuation

      pip install SpeechRecognition

      pip install PyAudio

