import os
import sys
import threading
import time
import speech_recognition as sr


# Define file paths
pathLog = os.getcwd() + '/logs'
pathLog = pathLog.replace('/', '\\')


def logConversation(pathDirectory, messages):
    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Log the message
    pathLog = os.path.join(pathDirectory + r'\log.txt')
    with open(pathLog, 'w') as file:
        # Append: 'a'
        # Write: 'w'
        file.write(messages)


def loadMessage(pathDirectory):
    # Make the directory
    if not os.path.exists(pathDirectory):
        os.makedirs(pathDirectory)

    # Load the messages
    pathLog = os.path.join(pathDirectory + r'\log.txt')
    with open(pathLog, 'r') as file:
        messages = file.readlines()
    return messages


def recordAudio():
    print('Mic On', flush=True)
    messages = loadMessage(pathDirectory=pathLog)

    recognizer = sr.Recognizer()
    microphone = sr.Microphone()
    isRecording = True

    for index, name in enumerate(sr.Microphone.list_microphone_names()):
        print(f'Microphone {index}: {name}', flush=True)
    
    # Records and transcribe audio
    with microphone as source:

        recognizer.adjust_for_ambient_noise(source)
        while isRecording:
            try:
                audio = recognizer.listen(source, timeout=60)
                text = f'{recognizer.recognize_faster_whisper(audio).strip()}'
                print('Processing Audio', flush=True)
                if isRecording:
                    if text != '':
                        print('Mic Off', flush=True)
                        messages.append(text)
                        logConversation(pathLog, messages)
            except sr.UnknownValueError:
                messages += 'Could not understand the audio.'
            except sr.RequestError:
                messages += 'Could not request results, check your internet connection.'


flagRecord = bool(int(sys.argv[1]))  # Convert "1" -> True, "0" -> False
flag2 = bool(int(sys.argv[2]))
if flagRecord:
    recordAudio()
elif flag2:
    print('Mic Off', flush=True)

