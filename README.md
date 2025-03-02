# Purpose:

Do you have an essay to write, and no time to write it yourself?

This program will allow you to record a message, and convert an audio recording into text that you can copy and paste into your paper, shopping list, dissertation, or whatever kind of writing project you have


# Recomened Software for Instalation:

- IntelliJ Idea Community Edition 2024.3.4
  - With JavaFX plugin installed
    
    - Download at:
    
          https://gluonhq.com/products/javafx/
    
      - Versions: (If one fails then try the other)
        - 21.0.6 [LTS] (Use if your running Java version 23.X.X in your envioronment)
        - 17.0.14 [LTS]
    
      - Type: SDK

- Miniconda3

      https://repo.anaconda.com/miniconda/

- If you have an NVIDIA GPU install:
  - NVIDIA (R) Cuda compiler driver V12.5.40
  - NVIDIA cuDNN V12.5


# Installing Program:

- These instruction were made for working with an IntelliJ Idea IDE

Download recommended software before continuing.

Clone github repository:

- Click on "Clone Repository" from the IntelliJ start menu
  
    ![Java IDE](https://github.com/user-attachments/assets/981bd7fd-4c48-4e73-8d66-636527f70055)

- Paste the repository path into the URL input, and click on the "Clone" button:

      https://github.com/Collinformatics/AudioTranscription

Setup the Project JDK:

- Open the "File" dropdown, and click on "Project Structure"

  - In the new popup window, click on "SKDs" in the left column, select the folder for your project in the column to the right

  - Find the "+" icon at the top of the column to the right

    - Click on the "+" and select "Download JDK" in the drop down window
    
    - Click the "Download" in the new window

       ![Java SDK](https://github.com/user-attachments/assets/6034a252-b284-40e4-8b28-cbd0a5aadc43)

Install JavaFX Plugin:

- Once you are back at the "Project Structure" window, click on "Modules"

  - Go to the column on the right "AudioTranscription" folder

    - Click on the "+" icon, and select "New Module"
    
      - Find the "JavaFX" option in right column and select it
   
      - Name the module, and click on the "Next" buton

      ![JavaFX](https://github.com/user-attachments/assets/e9ba349c-4117-4534-9c9c-6fc48a646ce8)

  - 


Set up Python.

- Install Python plugin at:

  Once your back at the "Project Structure"

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

Install modules:

- Go to the Intillij terminal:

- Activate conda environment and pip install
  
  Show all conda environments:

      conda env list

  Find your environment and acitvate it:

        conda activate envAudio

  Install necessary python modules:

      pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121

      pip install deepmultilingualpunctuation

      pip install faster-whisper

      pip install PyAudio

      pip install soundfile

      pip install SpeechRecognition

# Testing installation:

Testing Python Interpreter:

- Follow the folders in your IDE to:

      AudioTranscription\src\main\python\test.py

- Run test.py to evaluate:

  - If the Python interpreter was setup correctly
      
  - GPU Usage

    - Only relevant if your computer has an NVIDIA graphics card

  The results are displyed in the console

  - If the test is passed, the path to your interpreter should printed on the first line of your console

    - The path should look something like this, highlight the text and copy it:

          C:\Users\acountname\miniconda3\envs\envAudio\python.exe

Now you need to add intrepreter to UI.java:

- Found at:

      C:\AudioTranscription\src\main\java\com.example.audiotranscription\UI.java

- Find the line that looks like this in the UI.java script:

      public String pythonExe = "C:\path\to\python.exe";

- Update the path with the text just copied:

      public String pythonExe = "C:\Users\acountname\miniconda3\envs\envAudio\python.exe";
