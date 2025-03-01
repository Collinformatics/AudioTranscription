import os
import sys
import speech_recognition as sr


# Define file paths
pathDirLogs = os.getcwd() + '/logs'
pathDirLogs = pathDirLogs.replace('/', '\\')

# Printing outputs
sys.stderr = sys.stdout


def logConversation(pathDirectory, messages):
    for line in messages:
        print(f'_text_{line}')

    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Log the message
    pathDirLogs = os.path.join(pathDirectory + r'\log.txt')
    with open(pathDirLogs, 'w') as file:
        # Append: 'a'
        # Write: 'w'
        for line in messages:
            file.write(f'{line}\n')
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
            for line in messages:
                print(line)

    # print(f'Loaded Message:\n{messages}\n', flush=True)
    return messages


def recordAudio():
    messages = loadMessage(pathDirectory=pathDirLogs)
    recognizer = sr.Recognizer()
    microphone = sr.Microphone()

    # Records and transcribe audio
    isRecording = True
    with (microphone as source):
        recognizer.adjust_for_ambient_noise(source)
        while isRecording:
            try:
                print('Mic On', flush=True)
                audio = recognizer.listen(source, timeout=5)
                print('Processing Audio', flush=True)
                text = f'{recognizer.recognize_faster_whisper(audio).strip()}'
                if text != '':
                    messages.append(text)
                    logConversation(pathDirLogs, messages)
                    isRecording = False

            except sr.UnknownValueError:
                messages.append('Could not understand the audio.')
            except sr.RequestError:
                messages.append('Could not request results, '
                                'check your internet connection.')
            except sr.WaitTimeoutError:
                # Handle timeout error and stop recording
                print("Mic Off", flush=True)
                isRecording = False  # Stop recording on timeout


# Use button inputs
flagRecord = bool(int(sys.argv[1]))  # Convert "1" -> True, "0" -> False
flagInitializeUI = bool(int(sys.argv[2]))
if flagRecord:
    recordAudio()
else:
    isRecording = False
    print('Mic Off')
