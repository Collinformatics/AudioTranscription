

class Microphone():
    def __init__(self):
        super().__init__()
        import os
        import sys
        import speech_recognition as sr
        import threading


        # User
        self.myName = 'Collin'
        self.initialMessage = f'Hi {self.myName}, what can I write down for you?'

        # Text
        self.messages = ''
        self.messageTemp = ''
        self.initialRun = True
        self.initialSave = True
        self.logPath = ''
        self.counts = 0

        # Microphone
        self.isRecording = False
        self.endRecording = True
        self.recognizer = sr.Recognizer()
        self.microphone = sr.Microphone()
        self.messageMicOn = '\n\nListening'
        self.messageTranscribeAudio = '\n\nProcessing Audio'
        self.audioThread = None


    def recordAudio(self):
        # Records and transcribe audio
        with self.microphone as source:
            self.recognizer.adjust_for_ambient_noise(source)
            while self.isRecording:
                try:
                    if self.messages == '':
                        self.messages = self.initialMessage
                        self.addText(self.messages)
                    else:
                        self.addText(self.messages + self.messageMicOn)
                    audio = self.recognizer.listen(source, timeout=60)
                    self.addText(self.messages + self.messageTranscribeAudio)
                    text = f'{self.recognizer.recognize_faster_whisper(audio).strip()}'
                    if self.isRecording:
                        self.recordingFinish(text) # Stop recording due to pause
                except sr.UnknownValueError:
                    self.messages += f'\nCould not understand the audio.'
                except sr.RequestError:
                    self.messages += (f'\nCould not request results, '
                                      f'check your internet connection.')


    def addText(self, words):
        self.messageTemp = self.messages + words
        print(f'Adding:\n{self.messageTemp}')


    def recordingFinish(self, text):
        # Handle when the audio thread finishes
        print("Recording has finished.")
        self.isRecording = False


    def logConversation(self):
        if self.messages != '':
            # Check if the directory exists
            directory = 'logs'
            if not os.path.exists(directory):
                os.makedirs(directory) # Create the directory if it doesn't exist

            if self.initialSave:
                # Check if log.txt exists in the current working directory
                nameScript = os.path.basename(sys.argv[0]).replace('.py', '')
                for index in range(0, 10**4):
                    self.logPath = os.path.join(directory, f'log_{nameScript}{index}.txt')
                    if not os.path.isfile(self.logPath):
                        print(f'Logging converation: {self.logPath}\n')
                        with open(self.logPath, 'w') as file:
                            # Append: 'a'
                            # Write: 'w'
                            file.write(self.messages)
                            self.initialSave = False
                            break
            else:
                print(f'Logging converation: {self.logPath}\n')
                with open(self.logPath, 'w') as file:
                    file.write(self.messages)
