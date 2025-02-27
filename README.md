# Setup: Intellij Idea @ Pycharm Environments

To run the scripts out of IDEs with these instructions, you will need to have Intellij and Pycharm installed on your computer. 

You will need to make a project in each application.

It is recommended that you give them the same name, such as "AudioTranscription"

Make sure that your Java envionment in Intillij has the JavaFX plugin

After creating a project in both IDEs, add a Python interperter to the Intillij project: 

- In Pycharm, use the IDE's terminal to find the python executable file with the command:

      find . -name python.exe 2>/dev/null

  The executable file should be in a path that looks like:

        ./.venv/Scripts/python.exe

  Open the folder tab in the top left corner of the IDE, and follow the path the the python.exe file.

  Click on the file and copy its path.

  The path should look similar to this:

      C:\Users\AccountName\PycharmProjects\AudioTranscription\.venv\Scripts\python.exe

- Add the interpreter path to the Intellij IDE:

  Go back to Intellij and open the drop-down menu option, and select "File" -> "Project Structure"

  After clicking on "Project Structure", click on "Modules" then the "+" icon.

  Select: Python
  - If Python is not in the dropdown then close the dropdown and click on the folder with the project name to highlight it and reclick on the "+" icon

  Click on the "+" icon in the Configure SDK pop up window, and select "Add Python SDK from Disk"

  Select the "Existing envrionment" and paste the path to the python interpreter from Pycharm
  
  Find the row that is titled "Base Interpreter" and click on the icon on the right side of this row 

  A window will pop up, paste the path to the Python interpreter in the input bar and press "OK"

  Close the remaining pop up windows untill you are back at the "Project Structure" window.

  Use the "Python Interpreter" drop down to select the new Python .venv that we just added, and click on the "Apply" button in the bottom right corner of the window

Install modules:

- Go back to the Pycharm terminal and install necessary python modules with the pip

      pip install deepmultilingualpunctuation

      pip install openai-whisper

      pip install PyAudio

      pip install SpeechRecognition

