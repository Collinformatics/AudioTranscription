# Purpose:

Do you have an essay to write, and no time to write it yourself?

This program will allow you to record a message, and convert an audio recording into text that you can copy and paste into your paper, shopping list, dissertation, or whatever kind of writing project you have


# Recomened Software:

- IntelliJ Idea Community Edition 2024.3.4

  - Other versions (or other IDEs) would work, but the installation steps might be slightly different

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
   
      - Name the module as "JavaFX", and click on the "Next" buton

      ![JavaFX](https://github.com/user-attachments/assets/e9ba349c-4117-4534-9c9c-6fc48a646ce8)
  
        - A new popup will offer the option to install additonal libaries, don't worry about adding any of them
        
        - Click on the "Create" button
          
  - After its added, you will be back at the "Project Structure" window

    - Make sure the "JavaFX" module we just created is selected
    
      - Set the "Language Level" to 21

      - Click on the "Apply" button

      - Click on the "OK" button


Set up Python.

- Install Python:

  Reopen the "Project Structure" window

  - Click on "Modules"

    Then click the "+" icon, and click on "New Module" in the drop down window

    Click on "More via plugins" in the bottom left corner of the "New Module" window

    - In the "Install Plugin" popup, click on "Python Community Edition"

      - Click on the "Install" button 

      - Then click on the "Restart IDE" button

- Create Python Interpreter:

  - Go back to the "Project Structure" window, under the left column select "SDKs"

    Click the "+" icon, and click on "Add Python SDK from disk"

    - Create Python envrionment:

      - In the new pop up window select "Conda Environment"

        Select the option "New Environment"

        - Name the virtual environment:
        
          - In the "Location:" input bar the path should contain something similar to:
         
                   /Users/accountname/miniconda3/envs/AudioTranscription

            - Lets change it to:

                      /Users/accountname/miniconda3/envs/envAudio

              This will name the virtual environment as: "envAudio"

                - My interpreter will be named "evnAudio" but you can change this to name it whatever you want, just make sure that no other interpreters in the "envs" folder have the same name

              Click on the "OK" button to create the virtual environment

            ![Python Intrepreter](https://github.com/user-attachments/assets/89a92b21-8db9-4840-be44-7583b9300efb)

              When you get back to the "Project Structure" window, click on the "Apply" button

              
- Add Python Interpreter to IDE

  - Click on "Modules"

    In the column to the right, find the project folder titled "AudioTranscription"

    - Under this folder, you should see "Python"

      - Click on "Python"

        To the right you should see the input for "Python Interpreter"

        - Open the dropdown and select "envAudio"
       
          Click on the "Apply" button to add the interpreter


Install modules:

- Go to the IntelliJ terminal:

  - If the command line starts with: (envAudio)

    - Then the conda envrionment has been activated, and you can proceed to the "pip install" commands

   
- If it starts with: (base)

  - Then we need to activate the conda environment:
  
    - Begin this process by inspecting all of your conda environments with this command:

            conda env list

      - Find your environment and acitvate it:

              conda activate envAudio

- When your environment has been activated:

  - Install necessary python modules:

          pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121
      
          pip install deepmultilingualpunctuation
      
          pip install faster-whisper
      
          pip install PyAudio
      
          pip install soundfile
      
          pip install SpeechRecognition

- If you want to deactivate the conda envrionment then enter the command:

        conda deactivate

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

# Running The Program:

- Follow the path the the UI.java file:

      AudioTranscription/src/main/java/com.example.audiotranscription/UI.java

  - Open the file and run the code to create the UI for the program

- The program will save your transcribed messages in the "logs" folder

  - Open "log.txt" to view and edit the text
