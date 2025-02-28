# Setup: Intellij Idea

For these instructions, make sure that your Java envionment in Intillij has the JavaFX plugin.

After that is setup we will need to set up Python.

- Install Python plugin at:

  Open the "File" dropdown and select "Project Structure"

  Click on Modules in the left column, select the folder for your project in the column to the right

  Then click the "+" icon, and click on "New Module" in the drop down window

  Click on "More via plugins" in the bottom left corner of the "New Module" window

  In the "Install Plugin" popup, click on "Python" to add the module

  Restart the IDE

- Create Python Interpreter:

  Go back to the "Project Structure" window, under the left column select "SDKs"

  Click the "+" icon, and click on "Add Python SDK from disk"

  In the new pop up window select "Conda Environment"

  Select the option "New Environment"

  In the "Location:" input bar make sure the path contains: 

  - miniconda3\envs\evnAudio
  
  My interpreter will be named "evnAudio" but you can change this to name it whatever you want, just make sure that no other interpreters in the "envs" folder have the same name

  Press the "OK" button to create the interpreter


- Add Python Interpreter:

  In the "Project Structure" window, click on "Modules"

  Select the project folder in the column to the right, and then click on "+"

  In the dropdown, click on "Python"

  To the right you should see the input for "Python Interpreter"

  It should contain a path to the Python executable file (python.exe) that is found in the path to the interpreter envAudio, that we just create

  If it matches, then click on the "Apply" button and close the window.


Install modules:

- Go back to the Pycharm terminal and install necessary python modules with the pip

      pip install deepmultilingualpunctuation

      pip install openai-whisper

      pip install PyAudio

      pip install SpeechRecognition

