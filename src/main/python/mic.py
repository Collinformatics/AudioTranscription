import os
import sys
import speech_recognition as sr


# Define file paths
pathDirLogs = os.getcwd() + '/logs'
pathDirLogs = pathDirLogs.replace('/', '\\')

# Printing outputs
sys.stderr = sys.stdout
sys.stdout = open(os.devnull, 'w')


def logConversation(pathDirectory, text):
    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Log the message
    pathDirLogs = os.path.join(pathDirectory + r'\log.txt')
    with open(pathDirLogs, 'a') as file:
        # Append: 'a'
        # Write: 'w'
        file.write(text + '\n\n')
    print('Mic Off', flush=True)


def loadMessage(pathDirectory):
    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Load the messages
    pathLog = os.path.join(pathDirectory + r'\log.txt')
    if os.path.exists(pathLog):
        with open(pathLog, 'r') as file:
            messages = file.readlines()
    else:
        messages = []

    # Add messages
    if flagInitializeUI:
        if messages != []:
            for index, line in enumerate(messages):
                if index != len(messages)-1:
                    print(f'_text_{line}')
    return messages


def recordAudio():
    messages = loadMessage(pathDirectory=pathDirLogs)
    recognizer = sr.Recognizer()
    microphone = sr.Microphone()

    # Records and transcribe audio
    isRecording = True
    with (microphone as source):
        recognizer.adjust_for_ambient_noise(source)
        # while isRecording:
        try:
            print('Mic On', flush=True)
            audio = recognizer.listen(source, timeout=5)
            print('Processing Audio', flush=True)
            text = f'{recognizer.recognize_faster_whisper(audio).strip()}'
            if text != '':
                if messages != []:
                    print('_text_')
                print(f'_text_{text}')
                logConversation(pathDirLogs, text)
                isRecording = False

        except sr.UnknownValueError:
            messages.append('Could not understand the audio.')
        except sr.RequestError:
            messages.append('Could not request results, '
                            'check your internet connection.')
        except sr.WaitTimeoutError:
            # Handle timeout error and stop recording
            print("Mic Off", flush=True)
            isRecording = False


# Use button inputs
flagRecord = bool(int(sys.argv[1]))  # Convert "1" -> True, "0" -> False
flagInitializeUI = bool(int(sys.argv[2]))
if flagRecord:
    recordAudio()
else:
    isRecording = False
    print('Mic Off')
