import os
import pandas as pd
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
    messages = loadMessage(pathDirectory=pathLog)

    recognizer = sr.Recognizer()
    microphone = sr.Microphone()
    isRecording = True

    for index, name in enumerate(sr.Microphone.list_microphone_names()):
        print(f"Microphone {index}: {name}")
    
    # Records and transcribe audio
    with microphone as source:
        print('Mic On')
        recognizer.adjust_for_ambient_noise(source)
        while isRecording:
            try:
                audio = recognizer.listen(source, timeout=60)
                text = f'{recognizer.recognize_faster_whisper(audio).strip()}'
                print('Processing Audio')
                if isRecording:
                    if text != '':
                        print("Mic Off")
                        messages.append(text)
                        logConversation(pathDirectory=pathLog)
            except sr.UnknownValueError:
                messages += f'\nCould not understand the audio.'
            except sr.RequestError:
                messages += (f'\nCould not request results, '
                                  f'check your internet connection.')


def testAudio():
    print('Processing Audio')


flagRecord = bool(int(sys.argv[1]))  # Convert "1" -> True, "0" -> False
flag2 = bool(int(sys.argv[2]))
if flagRecord:
    print("Mic On")
elif flag2:
    testAudio()
print('')
