import os
import sys
import threading
import speech_recognition as sr


# Define file paths
pathDirLogs = os.getcwd() + '/logs'
pathDirLogs = pathDirLogs.replace('/', '\\')
isRecording = False
sys.stderr = sys.stdout


def logConversation(pathDirectory, messages, text):
    # Add messages
    print(text)

    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Log the message
    messages.append(text)
    pathDirLogs = os.path.join(pathDirectory + r'\log.txt')
    with open(pathDirLogs, 'w') as file:
        # Append: 'a'
        # Write: 'w'
        for line in messages:
            file.write(line)


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
        messages = ['']

    # Add messages
    if flagInitializeUI:
        if messages != ['']:
            for line in messages:
                print(line)

    # print(f'Loaded Message:\n{messages}\n', flush=True)
    return messages


def recordAudio():
    messages = loadMessage(pathDirectory=pathDirLogs)

    recognizer = sr.Recognizer()
    microphone = sr.Microphone()

    isRecording = True

    # Records and transcribe audio
    with microphone as source:
        recognizer.adjust_for_ambient_noise(source)
        while isRecording:
            try:
                print('Mic On', flush=True)
                audio = recognizer.listen(source, timeout=60)
                print('Processing Audio', flush=True)
                text = f'{recognizer.recognize_faster_whisper(audio).strip()}'
                print('Mic Off', flush=True)
                if text != '':
                    logConversation(pathDirLogs, messages, text)
            except sr.UnknownValueError:
                messages += 'Could not understand the audio.'
            except sr.RequestError:
                messages += 'Could not request results, check your internet connection.'


# Use button inputs
flagRecord = bool(int(sys.argv[1]))  # Convert "1" -> True, "0" -> False
flagInitializeUI = bool(int(sys.argv[2]))
if flagRecord:
    recordAudio()
else:
    isRecording = False
