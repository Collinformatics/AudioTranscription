# Setup: Intellij Idea

For these instructions, make sure that your Java envionment in Intillij has the JavaFX plugin.

After JavaFx is setup we can clone the AudioTranscription repository

- Clone instructions:

  * Make instructions

Next we will need to set up Python.

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


- Add Python Interpreter to IDE

  In the "Project Structure" window, click on "Modules"

  Select the project folder in the column to the right, and then click on "+"

  In the dropdown, click on "Python"

  To the right you should see the input for "Python Interpreter"

  It should contain a path to the Python executable file (python.exe) that is found in the path to the interpreter envAudio, that we just create

  If it matches, then click on the "Apply" button and close the window.

  - Testing Python Interpreter:

    Follow the folders in your IDE to: AudioTranscription\src\main\python\test.py

    Run the script to see if the string prints correctly

    If it works the path to your interpreter should directly above the string

    The path should look something like this, find the text and copy it:

        C:\Users\acountname\miniconda3\envs\envAudio\python.exe

- Add intrepreter to UI.java:

  Find the line that looks like this in the UI.java script:

      public String pythonExe = "C:\path\to\python.exe";

  Update the path with the text just copied:

      public String pythonExe = "C:\Users\acountname\miniconda3\envs\envAudio\python.exe";

Install modules:

- Go to the Intillij terminal:

  Acitvate the conda envionment

      conda activate envAudio

  Install necessary python modules with the pip

      pip install deepmultilingualpunctuation

      pip install faster-whisper

      pip install PyAudio

      pip install SpeechRecognition

