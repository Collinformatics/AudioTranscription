import os
import numpy as np
import pyaudio
import sys
import threading

# Define file paths
nameLog = 'log.txt'
pathDirLogs = os.getcwd() + '/logs'
pathDirLogs = pathDirLogs.replace('/', '\\')

# Printing outputs
sys.stderr = sys.stdout


class Mic():
    def __init__(self, saveDirectory, fileName):
        from faster_whisper import WhisperModel
        import torch

        # Save path
        self.directory = saveDirectory
        self.fileName = fileName
        self.savePath = os.path.join(saveDirectory, fileName)

        # Define parameters for the recording
        self.FORMAT = pyaudio.paInt16  # Audio format
        self.CHANNELS = 1  # Mono audio
        self.RATE = 16000  # Sample rate
        self.CHUNK = 512  # Buffer size

        # Whisper model for transcription
        device = "cuda" if torch.cuda.is_available() else "cpu"
        self.model = WhisperModel("base", device=device)

        # Initialize PyAudio stream
        self.audioStream = pyaudio.PyAudio()
        self.audio = []
        self.micOn = False

        self.stream = self.audioStream.open(format=self.FORMAT,
                                            channels=self.CHANNELS,
                                            rate=self.RATE,
                                            input=True,
                                            frames_per_buffer=self.CHUNK)

    # Function to log conversation into a file
    def logConversation(self, text):
        if not os.path.exists(self.directory):
            os.makedirs(self.directory)

        with open(self.savePath, 'a') as file:
            file.write(text + '\n\n')
        print('Mic Off', flush=True)

    # Function to load messages from the log file
    def loadMessages(self):
        if not os.path.exists(self.directory):
            os.makedirs(self.directory)

        messages = []
        if os.path.exists(self.savePath):
            with open(self.savePath, 'r') as file:
                messages = file.readlines()

        if messages:
            for index, line in enumerate(messages):
                if index != len(messages) - 1:
                    print(f'_text_{line}')

    # Function to record audio until stopped by self.micOn flag
    def recordAudio(self):
        print("Initializing the model and starting the stream...")

        self.stream.start_stream()
        print("Recording started. Press 'q' to stop.")

        while self.micOn:
            try:
                print('Mic On')
                audio = self.stream.read(self.CHUNK)  # Read the audio chunk
                self.audio.append(audio)  # Append the chunk to the frames buffer
            except Exception as e:
                print(f"Error recording audio: {e}")
                break
        self.processAudio()

    # Function to process the recorded audio
    def processAudio(self):
        print("Processing Audio")
        if len(self.audio) == 0:
            print("No audio recorded to process.")
            return

        # Combine frames into a numpy array
        audio = np.frombuffer(b''.join(self.audio), dtype=np.int16)

        # Use Whisper to transcribe the audio
        audio = whisper.Audio.from_array(audio, sr=self.RATE)
        text = self.model.transcribe(audio)
        self.logConversation(text)


# Handling button inputs from Java or command line
flagRecord = bool(int(sys.argv[1])) if len(sys.argv) > 1 else False
flagInitializeUI = bool(int(sys.argv[2])) if len(sys.argv) > 2 else False

if flagInitializeUI:
    # Initialize the microphone
    mic = Mic(saveDirectory=pathDirLogs, fileName=nameLog)
    mic.loadMessages()  # Load messages if requested
else:
    if flagRecord:
        mic.micOn = True
        print('Start')

        # Start recording in a separate thread
        threadRecording = threading.Thread(target=mic.recordAudio)
        threadRecording.start()
    else:
        print('Turn off button')
        mic.micOn = False

